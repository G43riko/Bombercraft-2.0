package Bombercraft2.Bombercraft2.core;

import java.awt.Canvas;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.multiplayer.Connector;


public interface MenuAble extends Visible{
	public void 		startNewGame();
	public void 		showJoinMenu();
	public void 		continueGame();
	public void 		connectToGame(String ip);
	public void 		pausedGame();
	public void 		endGame();
	public void 		stopGame();
	public void 		exitGame();
	public GuiManager	getGuiManager();
	public JSONObject	getPlayerInfo();
	public void 		createGame(JSONObject string);
	public void 		showMessage(String message, String ...args);
	public boolean 		isGameLauched();
	public Canvas 		getCanvas();
//	public int 			getGameIs();
	public void 		setProfile(String profilName);
	
	public void 		showProfileMenu();
	public void 		showMainMenu();
	public GameAble 	getGame();
	public Connector 	getConnector();
	public void 		showOptions();
}
