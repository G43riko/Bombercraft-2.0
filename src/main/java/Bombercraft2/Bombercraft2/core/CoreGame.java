package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.core.GameState.Type;
import Bombercraft2.Bombercraft2.game.Game;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotManager;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.towers.TowerCreator;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.gui.AlertManager;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.LoadingScreen;
import Bombercraft2.Bombercraft2.gui.menus.*;
import Bombercraft2.Bombercraft2.gui.menus.Menu;
import Bombercraft2.Bombercraft2.gui2.GuiTester;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.Bombercraft2.multiplayer.GameClient;
import Bombercraft2.Bombercraft2.multiplayer.GameServer;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.engine.Input;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.util.Stack;

public class CoreGame extends CoreEngine implements MenuAble {
    private       Profile          profile      = null;
    private       Game             game         = null;
    private       boolean          ignoreBlur   = true;
    //	private Level 				level;
    private       Connector        connector    = null;
    private final GuiManager       guiManager   = new GuiManager();
    private final AlertManager     alertManager = new AlertManager(this);
    private       boolean          gameLaunched = false;
    private final Stack<GameState> states       = new Stack<>();
    private       JSONObject       gameConfig   = null;

    public CoreGame() {
        super(Config.WINDOW_DEFAULT_FPS,
              Config.WINDOW_DEFAULT_UPS,
              Config.WINDOW_DEFAULT_RENDER_TEXT);

        Utils.sleep(100);
        states.push(new ProfileMenu(this));
        Input.setTarget(states.peek());

        try {
            JSONObject jsonResult = ResourceLoader.getJSON(Config.FILE_GAME_CONFIG);
            if (jsonResult == null) {
                Error.makeError(Error.CANNOT_READ_JSON);
            }
            gameConfig = jsonResult.getJSONObject("data");
            BotManager.init(gameConfig.getJSONObject("enemies"));
            BulletManager.init(gameConfig.getJSONObject("bullets"));
            JSONObject helpers = gameConfig.getJSONObject("helpers");
            TowerCreator.init(helpers.getJSONObject("towers"));

        }
        catch (JSONException e) {
            GLogger.printLine(e);
        }
    }

    public void switchVisibleOption(String key) {
        profile.getOptions().switchVisibleOption(key);
    }

    @Override
    protected void render(Graphics2D g2) {
        if (!ignoreBlur && !getWindow().isFocused()) {
            return;
        }


        states.peek().render(g2);
        alertManager.render(g2);
        GuiTester.renderTest(g2);
    }

    @Override
    protected void update(float delta) {
        if (!ignoreBlur && !getWindow().isFocused()) {
            return;
        }
        states.peek().update(delta);
        alertManager.update(delta);
    }

    @Override
    protected void input() {
        if (!ignoreBlur && !getWindow().isFocused()) {
            return;
        }
        states.peek().input();

        if (Input.getKeyDown(Input.KEY_E)) {
            endGame();
            return;
        }

        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            if (states.peek().getType() == GameState.Type.Game) {
                pausedGame();
            }
            else if (states.peek().getType() == GameState.Type.MainMenu && game != null) {
                continueGame();
            }
        }
    }

    @Override
    public void onResize() {
        getPosition().set(getWindow().getWidth(), getWindow().getHeight());
        if (states != null) {
            states.forEach(GameState::onResize);
        }
    }

    @Override
    public void onExit() {
        if (connector != null) {
            connector.setCloseConnection();
        }
        if (profile != null) {
            Profile.saveProfile(profile);
        }
    }

    @Override
    public void exitGame() {
        stopGame();
        stop();
        cleanUp();
        onExit();
        System.exit(1);
    }

    private JSONObject toJSON() {
        return new JSONObject();
    }

    public void showLoading() {
        states.push(new LoadingScreen(getCanvas(), guiManager.getLabelOf(Texts.LOADING)));
        Input.removeTarget();
    }

    public void removeLoading() {
        //vyberieme loading state
        if (states.peek().getType() == GameState.Type.LoadingScreen) {
            states.pop();
            Input.setTarget(states.peek());
        }
    }

    public void startNewGame() {
        if (gameLaunched) {
            stopGame();
        }
        showLoading();

        connector = new GameServer(this);
    }

    public void showJoinMenu() {
        states.push(new JoinMenu(this));
        Input.setTarget(states.peek());
    }

    @Override
    public void connectToGame(String ip) {
        //odstranime joinMenu
        states.pop();
        Input.setTarget(null);

        if (gameLaunched) {
            stopGame();
        }
        showLoading();

        connector = new GameClient(this, ip);
    }

    public void endGame() {
        states.pop();
        states.push(new EndGameMenu(this));
        Input.setTarget(states.peek());
    }

    public void continueGame() {
        if (game != null) {
            states.push(game);
        }
        Input.setTarget(game);
    }

    public void pausedGame() {
        if (states.peek().getType() == Type.EndGameMenu) {
            states.pop();
        }

        //vyberie game
        states.pop();
        Input.setTarget(states.peek());

        Menu menu = (Menu) states.peek();
        menu.setDisabled(Texts.CONTINUE_GAME, game == null);
        menu.setDisabled(Texts.STOP_GAME, menu == null);
//		menu.setDisabled(Texts.NEW_GAME, game != null);
        menu.setDisabled(Texts.JOIN_GAME, game != null);

    }

    public void stopGame() {
        if (game != null) {
            game.cleanUp();
        }

        if (connector != null) {
            connector.cleanUp();
        }
        gameLaunched = false;

        game = null;

        if (!Utils.isIn(states.peek().getType(), GameState.Type.MainMenu, GameState.Type.ProfileMenu)) {
            states.pop();
            Input.setTarget(states.peek());
        }

        if (states.peek().getType() == GameState.Type.MainMenu) {
            Menu menu = (Menu) states.peek();
            menu.setDisabled(Texts.CONTINUE_GAME, game == null);
            menu.setDisabled(Texts.STOP_GAME, menu != null);
            menu.setDisabled(Texts.JOIN_GAME, game != null);
        }
    }

    public void createGame(JSONObject gameData) {
        if (gameLaunched) { stopGame(); }

        if (gameData == null) {
            game = new Game(new Level(), this, null);
        }
        else {
            try {
                Level level = new Level(gameData.getJSONObject(Texts.LEVEL_DATA));
                game = new Game(level, this, gameData.getJSONObject(Texts.GAME_DATA));
            }
            catch (JSONException e) {
//					System.out.println("gameData: " + gameData);
                e.printStackTrace();
            }
        }

        gameLaunched = true;

        removeLoading();

        states.push(game);
        Input.setTarget(game);


    }

    public void setProfile(String profileName) {
        if (profile != null) {
            Profile.saveProfile(profile);
        }

        profile = new Profile(profileName);
    }

    public void initDefaultProfile() {
        setProfile("template");
    }

    @Override
    public void showOptions() {
        states.push(new OptionsMenu(this));
        Input.setTarget(states.peek());
    }

    public void showMainMenu() {
        states.push(new MainMenu(this));
        Menu menu = (Menu) states.peek();
        menu.setDisabled(Texts.CONTINUE_GAME, game == null);
        menu.setDisabled(Texts.STOP_GAME, menu != null);
        menu.setDisabled(Texts.JOIN_GAME, game != null);
        Input.setTarget(states.peek());
    }

    public void showProfileMenu() {
        states.pop().cleanUp();
        Input.setTarget(states.peek());
    }

    @Override
    public GVector2f getPosition() {
        return new GVector2f();
    }

    public GVector2f getSize() {
        return new GVector2f(getWindow().getWidth(), getWindow().getHeight());
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public boolean isGameLaunched() {
        return false;
    }

    public Profile getProfile() {
        return profile;
    }

    public GameAble getGame() {
        return game;
    }

    public Connector getConnector() {
        return connector;
    }

    public boolean getVisibleOption(String key) {
        return profile.getOptions().getVisibleOption(key);
    }

    //	public int getGameIs() {return 0;}
    @Override
    public JSONObject getPlayerInfo() {
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.NAME, profile.getName());
            result.put(Texts.IMAGE, profile.getAvatar());
            return result;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showMessage(String key, String... args) {
        alertManager.addAlert(guiManager.getLabelOf(key, args));

    }

    public JSONObject getWeapon(String type) {
        try {
            return gameConfig.getJSONObject("helpers").getJSONObject("weapons").getJSONObject("laser");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
