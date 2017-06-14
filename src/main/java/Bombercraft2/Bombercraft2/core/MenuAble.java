package Bombercraft2.Bombercraft2.core;

import java.awt.Canvas;

import Bombercraft2.Bombercraft2.game.Level;


public interface MenuAble extends Visible{
	public void 	startNewGame();
	public void 	joinGame();
	public void 	continueGame();
	public void 	pausedGame();
	public void 	stopGame();
	public void 	exitGame();
	
	public void 	createGame(Level level, String string);
	
	public boolean 	isGameLauched();
	public Canvas 	getCanvas();
	public int 		getGameIs();
	public void 	setProfile(String profilName);
	
	public void showProfileMenu();
	public void showMainMenu();
}
