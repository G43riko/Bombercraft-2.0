package Bombercraft2.Bombercraft2.core;

import java.awt.Graphics2D;
import java.util.Stack;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.game.Game;
import Bombercraft2.Bombercraft2.game.Level;
import Bombercraft2.Bombercraft2.game.LoadingScreen;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.menus.MainMenu;
import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.engine.Input;
import utils.Utils;
import utils.math.GVector2f;

public class CoreGame extends CoreEngine implements MenuAble{
	private Profil 				profil;
	private Game 				game;
	private Level 				level;
	private boolean				gameLauched	= false;
	private GuiManager 			guiManager 	= new GuiManager();
	private boolean 			focused		= true;
	private Stack<GameState> 	states 		= new Stack<GameState>();
	
	public CoreGame(){
		super(Config.WINDOW_DEFAULT_FPS, Config.WINDOW_DEFAULT_UPS, Config.WINDOW_DEFAULT_RENDER_TEXT);

		Utils.sleep(100);
		states.push(new ProfileMenu(this, guiManager));
		Input.setTarget(states.peek());
		
	}
	
	public Profil getProfil(){return profil;}
	
	public boolean getVisibleOption(String key){
		return profil.getOptions().getVisibleOption(key);
	}
	public void switchVisibleOption(String key){
		profil.getOptions().switchVisibleOption(key);
	}
	
	@Override
	protected void render(Graphics2D g2) {
		if(!focused){
			return;
		}
		
		states.peek().render(g2);
	}
	
	@Override
	protected void update(float delta) {
		if(!focused){
			return;
		}
		states.peek().update(delta);
	}
	
	@Override
	protected void input() {
		if(!focused){
			return;
		}
		states.peek().input();
		
		if(Input.isKeyDown(Input.KEY_ESCAPE)){
			if(states.peek().getType() == GameState.Type.Game){
				pausedGame();
			}
			else if(states.peek().getType() == GameState.Type.MainMenu && game != null){
				continueGame();
			} 
		}
	}

	
	@Override
	public void onResize() {
		getPosition().set(getWindow().getWidth(), getWindow().getHeight());
		if(states != null){
			states.forEach(a -> a.onResize());
		}
	}

	@Override
	public void onExit() {
		Profil.saveProfil(profil);
	}
	
	@Override
	public void onFocus() {
		focused = true;
	}
	@Override
	public void onUnfocus() {
		if(states != null && states.peek().getType() == GameState.Type.Game){
			pausedGame();
		}
		focused = false;
	}
	@Override
	public void exitGame() {
		stopGame();
		stop();
		cleanUp();
		System.exit(1);
	}
	
	private JSONObject toJSON(){
		JSONObject result = new JSONObject();
		return result;
	}

	@Override
	public GVector2f getPosition() {return new GVector2f();}
	public GVector2f getSize() {return new GVector2f(getWindow().getWidth(), getWindow().getHeight());}
	public void showLoading(){
		states.push(new LoadingScreen(getCanvas()));
		Input.removeTarget();
	}
	public void removeLoading(){
		//vyberieme loading state
		if(states.peek().getType() == GameState.Type.LoadingScreen){
			states.pop();
			Input.setTarget(states.peek());
		}
	}
	
	public void startNewGame() {
		if(gameLauched){
			stopGame();
		}
		showLoading();
		
		level = new Level();
		createGame(level, null);
	}
	public void joinGame() {}
	public void continueGame() {
		if(game != null){
			states.push(game);
		}
		Input.setTarget(game);
	}
	public void pausedGame() {
		//vyberie game
		states.pop();
		Input.setTarget(states.peek());
		
	}
	public void stopGame() {
		
	}
	public void createGame(Level level, String gameData) {
		if(gameLauched)
			stopGame();
		
		game = new Game(level, this, gameData);
		gameLauched = true;
		
		
		removeLoading();
		
		states.push(game);
		Input.setTarget(game);
	}
	public boolean isGameLauched() {return false;}
	public int getGameIs() {return 0;}
	public void setProfile(String profilName) {
		if(profil != null)
			Profil.saveProfil(profil);
		
		profil = new Profil(profilName);
	}

	@Override
	public void showMainMenu() {
		states.push(new MainMenu(this, guiManager));
		Input.setTarget(states.peek());
	}
	@Override
	public void showProfileMenu() {
		states.pop().cleanUp();
		Input.setTarget(states.peek());
	}

}
