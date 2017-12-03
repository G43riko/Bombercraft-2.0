package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.bots.BotManager;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletBasic;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletLaser;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.entity.towers.TowerMachineGun;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.level.Map;
import Bombercraft2.Bombercraft2.game.lights.Light;
import Bombercraft2.Bombercraft2.game.lights.LightsManager;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.gui.GameGui;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.engine.Input;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends GameState implements GameAble {
    private MouseSelector mouseSelector;//	= new MouseSelector(this);
    private       MyPlayer      myPlayer      = null;
    private       Level         level         = null;
    private       float         zoom          = Config.DEFAULT_ZOOM;
    private       GameGui       gui           = null;
    private       ToolManager   toolManager   = null;
    private       CoreGame      parent        = null;
    private final SceneManager  sceneManager  = new SceneManager(this);
    private       LightsManager lightsManager = null;
    private       long          startTime     = System.currentTimeMillis();


    //pre pripadny currentModificationException ak sa chce upravit nieco co sa pouziva//todo treba to osetrit inac
    private final boolean render = true;
    private final boolean update = true;
    private final boolean input  = true;

    public Game(Level level, CoreGame parent, JSONObject gameData) {
        super(GameState.Type.Game);
        this.level = level;
        this.parent = parent;
        level.setGame(this);
        toolManager = new ToolManager(this);
        try {
            myPlayer = new MyPlayer(this,
                                    level.getRandomRespawnZone(),
                                    getProfile().getName(),
                                    level.getDefaultPlayerInfo().getInt(Texts.SPEED),
                                    level.getDefaultPlayerInfo().getInt(Texts.HEALTH),
                                    getProfile().getAvatar(),
                                    level.getDefaultPlayerInfo().getInt(Texts.RANGE));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if (gameData != null) {
            try {
                for (int i = 0; i < gameData.getInt(Texts.PLAYERS_NUMBER); i++) {
                    sceneManager.addPlayer(new Player(this, new JSONObject(gameData.getString("player_" + i))));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        lightsManager = new LightsManager(this);
        gui = new GameGui(this);
        sceneManager.addPlayer(myPlayer);
//		parent.getCanvas().addMouseWheelListener(this);


        lightsManager.addLight(new Light(this, new GVector2f(300, 300), new GVector2f(300, 300), myPlayer));
    }

    @Override
    public void render(Graphics2D g2) {
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
        if (Input.isKeyDown(Input.KEY_ENTER)) {
            sceneManager.addEnemy(myPlayer.getSelectorPos(), BotManager.Types.A);
        }
        myPlayer.input();
        gui.input();
    }

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
        final long usedMem = (runtime.totalMemory() - runtime.freeMemory()) / 1000000;
        result.add("memory: " + String.format("%03d ", usedMem) + " / " + String.format("%03d ",
                                                                                        runtime.totalMemory() / 1000000) + "MB");

        result.addAll(sceneManager.getLogInfo());
        result.add("blocks: " + level.getMap().getRenderedBlocks() + "/" + (int) level.getMap()
                                                                                      .getNumberOfBlocks()
                                                                                      .mul());
        result.add("Zoom: " + getZoom());
        return result;
    }

    @Override
    public boolean isVisible(Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom());
    }

    public String getLabelOf(String key) {
        return parent.getGuiManager().getLabelOf(key);
    }

    @Override
    public boolean hasWall(float i, float j) {
        GLogger.notImplemented();
        return false;
    }

    public boolean getVisibleOption(String key) {
        return parent.getVisibleOption(key);
    }

    public void switchVisibleOption(String key) {
        parent.switchVisibleOption(key);
    }

    @Override
    public void addPlayer(String name, String image) {
        try {
            sceneManager.addPlayer(new Player(this,
                                              level.getRandomRespawnZone(),
                                              name,
                                              level.getDefaultPlayerInfo().getInt("speed"),
                                              level.getDefaultPlayerInfo().getInt("health"),
                                              "player1.png",
                                              level.getDefaultPlayerInfo().getInt("range")));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExplosion(GVector2f position,
                             GVector2f size,
                             Color color,
                             int number,
                             boolean explosion,
                             boolean shockWave
                            ) {
        sceneManager.addExplosion(position, size, color, number, explosion, shockWave);
    }

    @Override
    public void addEmitter(GVector2f position, Emitter.Types type) {
        sceneManager.addEmitter(position, type);
    }

    @Override
    public void addEnemy(GVector2f position, String type) {
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
//		render = update = input = false;
////		enemies.clear();
////		others.clear();
////		helpers.clear();
////		emitters.clear();
//		level.getMap().resetMap();
//		render = update = input = true;
//		parent.removeLoading();
    }

    @Override
    public void onResize() {
        calcPosition();
    }

    @Override
    public JSONObject toJSON() {
        return sceneManager.toJSON();
    }

    @Override
    public void changeZoom(float value) {
        if (!Config.ZOOM_ALLOWED || !level.isReady()) { return; }

        zoom += value;

        if (level.getMap().getNumberOfBlocks().getX() * Block.SIZE.getX() * zoom < parent.getCanvas().getWidth()) {
            zoom -= value;
            return;
        }

        if (zoom > Config.ZOOM_MAXIMUM) {
            zoom -= value;
        }

    }

    @Override
    public void doAct(GVector2f click) {
        gui.doAct(click);
    }

    @Override
    public void addHelper(GVector2f pos,
                          Helper.Type type,
                          long createTime
                         ) {//TODO playerov bonus k poskodeniu tu ma byt
        GVector2f localPos = Map.globalPosToLocalPos(pos);
        pos = pos.div(Block.SIZE).toInt().mul(Block.SIZE);

        String key = localPos.getXi() + "_" + localPos.getYi();
        if (sceneManager.existHelperOn(key)) {
            GLogger.printLine("Vytvara sa helper na helpere");
            return;
        }
        if (level.getMap().getBlock(key).getType() == Block.Type.WATER) {
            GLogger.printLine("Vytvara sa helper na vode");
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
    public void explodeBombAt(GVector2f pos) {
        String key = pos.getXi() + "_" + pos.getYi();
        if (!sceneManager.existHelperOn(key)) {
            GLogger.printLine("Explodovala neexistujuca bomba na: " + pos);
            return;
        }
        sceneManager.removeHelper(key);
    }

//	@Override
//	public HashMap<String, Player> getPlayers() {
//		return players;
//	}

    @Override
    public GVector2f getPosition() {return new GVector2f();}

    public GVector2f getSize() {return new GVector2f(getCanvas().getWidth(), getCanvas().getHeight());}

    public Level getLevel() {return level;}

    public Profile getProfile() {return parent.getProfile();}

    public float getZoom() {return 1;}

    public GVector2f getOffset() {return myPlayer.getOffset();}

    public Canvas getCanvas() {return parent.getCanvas();}

    public ToolManager getToolsManager() {return toolManager;}

    public Connector getConnector() {return parent.getConnector();}

    public MyPlayer getMyPlayer() {return myPlayer;}

    public String getGameInfo() {
        try {
            JSONObject o = new JSONObject();
            o.put(Texts.LEVEL_DATA, level.toJSON());
            o.put(Texts.GAME_DATA, toJSON());
            return o.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBasicInfo() {
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.LEVEL, "randomLevel");
            result.put(Texts.NAME, getProfile().getName());
            result.put(Texts.MAX_PLAYERS, "5");
            result.put(Texts.PLAYERS_NUMBER, sceneManager.getPlayersCount());
            return result.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return "{}";

    }

    @Override
    public void removePlayer(String name) {
        sceneManager.removePlayer(name);
    }

    @Override
    public Player getPlayerByName(String name) {
        return sceneManager.getPlayerByName(name);
    }

    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public JSONObject getWeapon(String type) {
        return parent.getWeapon(type);
    }


    @Override
    public GVector2f getPlayerDirection() {
        return myPlayer.getTagetDirection();
    }

    @Override
    public GVector2f getPlayerTarget() {
        return myPlayer.getTargetLocation();
    }

    @Override
    public void addBullet(Types bulletType, GVector2f angle, GVector2f position) {
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

    @Override
    public HashMap<String, String> getStats() {
        HashMap<String, String> stats = new HashMap<>();
        int duration = (int) (System.currentTimeMillis() - startTime) / 1000;
        int minutes = duration / 60;
        int seconds = duration - minutes * 60;
        stats.putAll(sceneManager.getStats());
        stats.put("Cas hry", minutes + ":" + seconds);
        return stats;
    }

}
