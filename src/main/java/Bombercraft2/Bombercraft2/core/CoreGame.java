package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.game.Game;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.gui.AlertManager;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.LoadingScreen;
import Bombercraft2.Bombercraft2.gui.menus.*;
import Bombercraft2.Bombercraft2.gui.menus.Menu;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.Bombercraft2.multiplayer.GameClient;
import Bombercraft2.Bombercraft2.multiplayer.GameServer;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.util.Stack;

public class CoreGame extends CoreEngine implements MenuAble {
    private       Profile          profile      = null;
    private       Game             game         = null;
    private final boolean          ignoreBlur   = true;
    //	private Level 				level;
    private       Connector        connector    = null;
    private final GuiManager       guiManager   = new GuiManager();
    private final AlertManager     alertManager = new AlertManager(this);
    private       boolean          gameLaunched = false;
    private final Stack<GameState> states       = new Stack<>();

    protected CoreGame() {
        super(StaticConfig.WINDOW_DEFAULT_FPS,
              StaticConfig.WINDOW_DEFAULT_UPS,
              StaticConfig.WINDOW_DEFAULT_RENDER_TEXT);

        Utils.sleep(100);
        states.push(new ProfileMenu(this));
        Input.setTarget(states.peek());
    }

    public void switchVisibleOption(String key) {
        profile.getOptions().switchVisibleOption(key);
    }

    @Override
    protected void render(@NotNull Graphics2D g2) {
        if (!ignoreBlur && !getWindow().isFocused()) {
            return;
        }


        states.peek().render(g2);
        alertManager.render(g2);
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
            if (states.peek().getType() == GameStateType.Game) {
                pausedGame();
            }
            else if (states.peek().getType() == GameStateType.MainMenu && game != null) {
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

    public void showLoading() {
        states.push(new LoadingScreen(getCanvas(), guiManager.getLabelOf(Texts.LOADING)));
        Input.removeTarget();
    }

    public void removeLoading() {
        // pop loading state
        if (states.peek().getType() == GameStateType.LoadingScreen) {
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
    public void connectToGame(@NotNull String ip) {
        //pop join menu
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
        if (states.peek().getType() == GameStateType.EndGameMenu) {
            states.pop();
        }

        //pop game
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

        if (!Utils.isIn(states.peek().getType(), GameStateType.MainMenu, GameStateType.ProfileMenu)) {
            states.pop();
            Input.setTarget(states.peek());
        }

        if (states.peek().getType() == GameStateType.MainMenu) {
            Menu menu = (Menu) states.peek();
            menu.setDisabled(Texts.CONTINUE_GAME, game == null);
            menu.setDisabled(Texts.STOP_GAME, menu != null);
            menu.setDisabled(Texts.JOIN_GAME, game != null);
        }
    }

    public void createGame(@Nullable JSONObject gameData) {
        if (gameLaunched) { stopGame(); }

        if (gameData == null) {
            game = new Game(new Level(), this, null);
        }
        else {
            try {
                Level level = new Level(gameData.getJSONObject(Texts.LEVEL_DATA));
                game = new Game(level, this, gameData.getJSONObject(Texts.GAME_DATA));
                GLogger.log(GLogger.GLog.LEVEL_SUCCESSFULLY_PARSED);
            }
            catch (JSONException e) {
                GLogger.error(GLogger.GError.CANNOT_PARSE_LEVEL, e);
            }
        }

        gameLaunched = true;

        removeLoading();

        states.push(game);
        Input.setTarget(game);


    }

    public void setProfile(@NotNull String profileName) {
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

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {
        return new GVector2f();
    }

    @Contract(pure = true)
    @NotNull
    public GVector2f getSize() {
        return new GVector2f(getWindow().getWidth(), getWindow().getHeight());
    }

    @NotNull
    public GuiManager getGuiManager() {
        return guiManager;
    }

    public boolean isGameLaunched() {
        return false;
    }

    public Profile getProfile() {
        return profile;
    }

    @NotNull
    public GameAble getGame() {
        return game;
    }

    @NotNull
    public Connector getConnector() {
        return connector;
    }

    public boolean getVisibleOption(String key) {
        return profile.getOptions().getVisibleOption(key);
    }

    //	public int getGameIs() {return 0;}
    @NotNull
    @Override
    public JSONObject getPlayerInfo() {
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.NAME, profile.getName());
            result.put(Texts.IMAGE, profile.getAvatar());
            return result;
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_SERIALIZE_PLAYER_INFO, e);
        }
        return null;
    }

    public void showMessage(@NotNull String key, String... args) {
        alertManager.addAlert(guiManager.getLabelOf(key, args));

    }

    public JSONObject getWeapon(String type) {
        try {
            return Game.getConfig().getJSONObject("helpers").getJSONObject("weapons").getJSONObject("laser");
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_GET_WEAPON_BY_TYPE, e);
            return null;
        }
    }
}
