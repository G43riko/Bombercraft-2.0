package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import org.json.JSONObject;

import java.awt.*;


public interface MenuAble extends Visible, StateAble {
    void connectToGame(String ip);

    GuiManager getGuiManager();

    JSONObject getPlayerInfo();

    void createGame(JSONObject string);

    void showMessage(String message, String... args);

    boolean isGameLaunched();

    Canvas getCanvas();

    //	int 			getGameIs();
    void setProfile(String profileName);

    void showProfileMenu();

    void showMainMenu();

    GameAble getGame();

    Connector getConnector();

    void showOptions();
}
