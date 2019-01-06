package org.bombercraft2.game;

import org.bombercraft2.Bombercraft;
import org.bombercraft2.Profile;
import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.CoreGame;
import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.bots.BotFactory;
import org.bombercraft2.game.entity.Bomb;
import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.bullets.Bullet;
import org.bombercraft2.game.entity.bullets.BulletBasic;
import org.bombercraft2.game.entity.bullets.BulletLaser;
import org.bombercraft2.game.entity.bullets.BulletManager;
import org.bombercraft2.game.entity.bullets.BulletManager.Types;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.entity.towers.TowerCreator;
import org.bombercraft2.game.entity.towers.TowerMachineGun;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.level.Level;
import org.bombercraft2.game.level.Map;
import org.bombercraft2.game.lights.Light;
import org.bombercraft2.game.lights.LightsManager;
import org.bombercraft2.game.player.MyPlayer;
import org.bombercraft2.game.player.Player;
import org.bombercraft2.gui.GameGui;
import org.bombercraft2.multiplayer.Connector;
import org.engine.Input;
import org.glib2.interfaces.Visible;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.utils.enums.Keys;
import org.utils.logger.GError;
import org.utils.logger.GLog;
import org.utils.logger.GLogger;
import org.utils.resources.ResourceUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends GameState implements GameAble {
    private static JSONObject gameConfig;

    static {
        try {
            JSONObject jsonResult = ResourceUtils.getJSON(StaticConfig.FILE_GAME_CONFIG);
            if (jsonResult == null) {
                GLogger.throwError(GError.CANNOT_READ_JSON);
            }
            gameConfig = jsonResult.getJSONObject("data");
            BotFactory.init(gameConfig.getJSONObject("enemies"));
            BulletManager.init(gameConfig.getJSONObject("bullets"));
            JSONObject helpers = gameConfig.getJSONObject("helpers");
            TowerCreator.init(helpers.getJSONObject("towers"));
        } catch (JSONException e) {
            GLogger.error(GError.CREATE_CORE_GAME_FAILED, e);
        }
    }

    private final SceneManager  sceneManager = new SceneManager(this);
    //pre pripadny currentModificationException ak sa chce upravit nieco co sa pouziva//todo treba to osetrit inac
    private final boolean       render       = true;
    private final boolean       update       = true;
    private final boolean       input        = true;
    private       MouseSelector mouseSelector;//	= new MouseSelector(this);
    private       MyPlayer      myPlayer     = null;
    private       float         zoom         = StaticConfig.DEFAULT_ZOOM;
    private       long          startTime    = System.currentTimeMillis();
    private       Level         level;
    private       GameGui       gui;
    private       ToolManager   toolManager;
    private       CoreGame      parent;
    private       LightsManager lightsManager;

    public Game(Level level, CoreGame parent, JSONObject gameData) {
        super(GameStateType.Game);
        try {
            this.level = level;
            this.parent = parent;
            level.setGame(this);
            toolManager = new ToolManager(this);
            myPlayer = new MyPlayer(this,
                                    level.getRandomRespawnZone(),
                                    getProfile().getName(),
                                    level.getDefaultPlayerInfo().getInt(Texts.SPEED),
                                    level.getDefaultPlayerInfo().getInt(Texts.HEALTH),
                                    getProfile().getAvatar(),
                                    level.getDefaultPlayerInfo().getInt(Texts.RANGE));


            if (gameData != null) {
                try {
                    for (int i = 0; i < gameData.getInt(Texts.PLAYERS_NUMBER); i++) {
                        sceneManager.addPlayer(new Player(this, new JSONObject(gameData.getString("player_" + i))));
                    }
                } catch (JSONException e) {
                    GLogger.error(GError.CANNOT_ADD_PLAYERS, e);
                }
            }

            lightsManager = new LightsManager(this);
            gui = new GameGui(this);
            sceneManager.addPlayer(myPlayer);
//		    parent.getCanvas().addMouseWheelListener(this);
            lightsManager.addLight(new Light(this,
                                             new GVector2f(300, 300),
                                             new GVector2f(300, 300), myPlayer));
            GLogger.log(GLog.GAME_CREATED);
        } catch (JSONException e) {
            GLogger.error(GError.CREATE_GAME_FAILED, e);
        }
    }

    public static JSONObject getConfig() {
        return gameConfig;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!render) {
            return;
        }

        g2.clearRect(0, 0, parent.getSize().getXi(), parent.getSize().getYi());
        level.render(g2);

        toolManager.render(g2);

        sceneManager.render(g2);

        level.renderUpperFlora(g2);

        if (myPlayer.showSelector()) {
            myPlayer.renderSelector(g2);
        }

        if (mouseSelector != null) {
            mouseSelector.render(g2);
        }

        lightsManager.render(g2);

//		g2.setE
//		g2.setG
//		GraphicsContext

        gui.render(g2);
    }


    public void update(float delta) {
        if (!update) {
            return;
        }

        myPlayer.update(delta);

        sceneManager.update(delta);

        gui.update(delta);
    }

    public void input() {
        if (!input) {
            return;
        }
        if (Input.isKeyDown(Keys.ENTER)) {
            sceneManager.addEnemy(myPlayer.getSelectorPos(), BotFactory.Types.A);
        }
        myPlayer.input();
        gui.input();
    }

    @NotNull
    @Override
    public ArrayList<String> getLogInfo() {
        ArrayList<String> result = new ArrayList<>();
        result.add("FPS: " + parent.getFPS());
        result.add("UPS: " + parent.getUPS());
        result.add("loops: " + parent.getLoops());
        result.add("player: " + myPlayer.getPosition().toDecimal(4));
        result.add("offset: " + getOffset().toDecimal(4));
        result.add("mouse: " + Input.getMousePosition().toDecimal(4));
        result.add("send messages: " + Bombercraft.totalMessages.getXi());
        result.add("receive messages: " + Bombercraft.totalMessages.getYi());

        Runtime runtime = Runtime.getRuntime();
        final long usedMem = (runtime.totalMemory() - runtime.freeMemory()) / 1_000_000;
        result.add("memory: " + String.format("%03d ", usedMem) + " / " + String.format("%03d ",
                                                                                        runtime.totalMemory() / 1_000_000) + "MB");

        result.addAll(sceneManager.getLogInfo());
        result.add("blocks: " + level.getMap().getRenderedBlocks() + "/" + (int) level.getMap()
                .getNumberOfBlocks()
                .mul());
        result.add("Zoom: " + getZoom());
        return result;
    }

    @Override
    public boolean isVisible(@NotNull Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom());
    }

    @NotNull
    public String getLabelOf(String key) {
        return parent.getGuiManager().getLabelOf(key);
    }

    @Override
    public boolean hasWall(float i, float j) {
        GLogger.notImplemented();
        return false;
    }

    public boolean getVisibleOption(@NotNull String key) {
        return parent.getVisibleOption(key);
    }

    public void switchVisibleOption(@NotNull String key) {
        parent.switchVisibleOption(key);
    }

    @Override
    public void addPlayer(@NotNull String name, @NotNull String image) {
        try {
            sceneManager.addPlayer(new Player(this,
                                              level.getRandomRespawnZone(),
                                              name,
                                              level.getDefaultPlayerInfo().getInt("speed"),
                                              level.getDefaultPlayerInfo().getInt("health"),
                                              "player1.png",
                                              level.getDefaultPlayerInfo().getInt("range")));
        } catch (JSONException e) {
            GLogger.error(GError.CANNOT_ADD_PLAYER, e);
        }
    }

    @Override
    public void addExplosion(@NotNull GVector2f position,
                             @NotNull GVector2f size,
                             @NotNull Color color,
                             int number,
                             boolean explosion,
                             boolean shockWave
    ) {
        sceneManager.addExplosion(position, size, color, number, explosion, shockWave);
    }

    @Override
    public void addEmitter(@NotNull GVector2f position, @NotNull EmitterTypes type) {
        sceneManager.addEmitter(position, type);
    }

    @Override
    public void addEnemy(@NotNull GVector2f position, @NotNull String type) {
        GLogger.notImplemented();
    }

    @Override
    public void calcPosition() {
        gui.calcPosition();
    }

    @Override
    public void newGame() {
        startTime = System.currentTimeMillis();
        parent.showLoading();
        resetGame();
        getLevel().getMap().createRandomMap();
        myPlayer.respawn();
        sceneManager.reset();
        parent.removeLoading();
    }

    @Override
    public void resetGame() {
//		parent.showLoading();
//		draw = update = input = false;
////		enemies.clear();
////		others.clear();
////		helpers.clear();
////		emitters.clear();
//		level.getHashMap().resetMap();
//		draw = update = input = true;
//		parent.removeLoading();
    }

    @Override
    public void onResize() {
        calcPosition();
    }

    @NotNull
    @Override
    public JSONObject toJSON() {
        return sceneManager.toJSON();
    }

    @Override
    public void changeZoom(float value) {
        if (!StaticConfig.ZOOM_ALLOWED || !level.isReady()) {
            return;
        }

        zoom += value;

        if (level.getMap().getNumberOfBlocks().getX() * StaticConfig.BLOCK_SIZE.getX() * zoom < parent.getCanvas()
                .getWidth()) {
            zoom -= value;
            return;
        }

        if (zoom > StaticConfig.ZOOM_MAXIMUM) {
            zoom -= value;
        }

    }

    @Override
    public void doAct(GVector2f click) {
        gui.doAct(click);
    }

    @Override
    public void addHelper(@NotNull GVector2f pos,
                          @NotNull Helper.Type type,
                          long createTime
    ) {//TODO playerov bonus k poskodeniu tu ma byt
        GVector2f localPos = Map.globalPosToLocalPos(pos);
        pos = pos.getDiv(StaticConfig.BLOCK_SIZE).toInt().getMul(StaticConfig.BLOCK_SIZE);

        String key = localPos.getXi() + "_" + localPos.getYi();
        if (sceneManager.existHelperOn(key)) {
            GLogger.print("Vytvara sa helper na helpere");
            return;
        }
        if (level.getMap().getBlock(key).getType() == BlockType.WATER) {
            GLogger.print("Vytvara sa helper na vode");
            return;
        }
        switch (type) {
            case BOMB_NORMAL:
                sceneManager.addHelper(key, new Bomb(pos, this, type, createTime));
                break;
            case TOWER_MACHINE_GUN:
                sceneManager.addHelper(key, new TowerMachineGun(pos, this));
                break;
        }
    }

    @Override
    public void explodeBombAt(@NotNull GVector2f pos) {
        String key = pos.getXi() + "_" + pos.getYi();
        if (!sceneManager.existHelperOn(key)) {
            GLogger.print("Explodovala neexistujuca bomba na: " + pos);
            return;
        }
        sceneManager.removeHelper(key);
    }

//	@Override
//	public HashMap<String, Player> getPlayers() {
//		return players;
//	}

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {return new GVector2f();}

    @Contract(pure = true)
    @NotNull
    public GVector2f getSize() {return new GVector2f(getCanvas().getWidth(), getCanvas().getHeight());}

    @NotNull
    public Level getLevel() {return level;}

    @NotNull
    public Profile getProfile() {return parent.getProfile();}

    @Contract(pure = true)
    public float getZoom() {return 1;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getOffset() {return myPlayer.getOffset();}

    public Canvas getCanvas() {return parent.getCanvas();}

    @NotNull
    public ToolManager getToolsManager() {return toolManager;}

    @NotNull
    public Connector getConnector() {return parent.getConnector();}

    @NotNull
    public MyPlayer getMyPlayer() {return myPlayer;}

    @NotNull
    public String getGameInfo() {
        try {
            JSONObject o = new JSONObject();
            o.put(Texts.LEVEL_DATA, level.toJSON());
            o.put(Texts.GAME_DATA, toJSON());
            return o.toString();
        } catch (JSONException e) {
            GLogger.error(GError.CANNOT_SERIALIZE_GAME_INFO, e);
        }
        return null;
    }

    @NotNull
    @Override
    public String getBasicInfo() {
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.LEVEL, "randomLevel");
            result.put(Texts.NAME, getProfile().getName());
            result.put(Texts.MAX_PLAYERS, "5");
            result.put(Texts.PLAYERS_NUMBER, sceneManager.getPlayersCount());
            return result.toString();
        } catch (JSONException e) {
            GLogger.error(GError.CANNOT_SERIALIZE_BASIC_INFO, e);
        }
        return "{}";
    }

    @Override
    public void removePlayer(@NotNull String name) {
        sceneManager.removePlayer(name);
    }

    @NotNull
    @Override
    public Player getPlayerByName(String name) {
        return sceneManager.getPlayerByName(name);
    }

    @NotNull
    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @NotNull
    @Override
    public JSONObject getWeapon(String type) {
        return parent.getWeapon(type);
    }


    @NotNull
    @Override
    public GVector2f getPlayerDirection() {
        return myPlayer.getTargetDirection();
    }

    @NotNull
    @Override
    public GVector2f getPlayerTarget() {
        return myPlayer.getTargetLocation();
    }

    @Override
    public void addBullet(@NotNull Types bulletType, @NotNull GVector2f angle, @NotNull GVector2f position) {
        Bullet bullet = null;
        switch (bulletType) {
            case LASER:
                bullet = new BulletLaser(position, parent.getGame(), angle);
                break;
            case BASIC:
                bullet = new BulletBasic(position, parent.getGame(), angle);
                break;
        }
        sceneManager.addBullet(bullet);
    }

    @Override
    public void endGame() {
        parent.endGame();
    }

    @NotNull
    @Override
    public HashMap<String, String> getStats() {
        HashMap<String, String> stats = new HashMap<>(sceneManager.getStats());
        int duration = (int) (System.currentTimeMillis() - startTime) / 1_000;
        int minutes = duration / 60;
        int seconds = duration - minutes * 60;
        stats.put("Cas hry", minutes + ":" + seconds);
        return stats;
    }
}
