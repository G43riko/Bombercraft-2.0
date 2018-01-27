package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.awt.*;


public interface MenuAble extends Visible, StateAble {
    void connectToGame(@NotNull String ip);

    @NotNull
    GuiManager getGuiManager();

    @NotNull
    JSONObject getPlayerInfo();

    void createGame(@Nullable JSONObject string);

    void showMessage(@NotNull String message, String... args);

    boolean isGameLaunched();

    @NotNull
    Canvas getCanvas();

    //	int 			getGameIs();
    void setProfile(@NotNull String profileName);

    void showProfileMenu();

    void showMainMenu();

    @NotNull
    GameAble getGame();

    @NotNull
    Connector getConnector();

    void showOptions();
}
