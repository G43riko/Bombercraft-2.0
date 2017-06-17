package Bombercraft2.Bombercraft2.core;

import java.awt.Graphics2D;
import java.util.Stack;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.game.Game;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.LoadingScreen;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.menus.MainMenu;
import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.Bombercraft2.multiplayer.GameClient;
import Bombercraft2.Bombercraft2.multiplayer.GameServer;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.engine.Input;
import utils.Utils;
import utils.math.GVector2f;

public class CoreGame extends CoreEngine implements MenuAble{
	private Profil 				profil;
	private Game 				game;
	private Level 				level;
	private Connector			connector;
	private GuiManager 			guiManager	= new GuiManager();
	private boolean				gameLauched	= false;
	private boolean 			focused		= true;
	private Stack<GameState> 	states 		= new Stack<GameState>();
	
	public CoreGame(){
		super(Config.WINDOW_DEFAULT_FPS, Config.WINDOW_DEFAULT_UPS, Config.WINDOW_DEFAULT_RENDER_TEXT);

		Utils.sleep(100);
		states.push(new ProfileMenu(this));
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
		
		if(Input.getKeyDown(Input.KEY_ESCAPE)){
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
		if(profil != null){
			Profil.saveProfil(profil);
		}
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
		
		connector = new GameServer(this);
	}
	public void joinGame() {
		if(gameLauched){
			stopGame();
		}
		showLoading();
		
		connector = new GameClient(this);
	}
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
	public void createGame(JSONObject gameData) {
		if(gameLauched)
			stopGame();
		
			if(gameData == null){
				game = new Game(new Level(), this, null);
			}
			else{
				try {
					Level level = new Level(gameData.getJSONObject("level"));
					game = new Game(level, this, gameData.getJSONObject("game"));
				} catch (JSONException e) {
					System.out.println("gameData: " + gameData);
					e.printStackTrace();
				}
			}
			
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
	
	public void initDefaultProfil(){
		setProfile("template");
	}

	public void showMainMenu() {
		states.push(new MainMenu(this));
		Input.setTarget(states.peek());
	}
	public void showProfileMenu() {
		states.pop().cleanUp();
		Input.setTarget(states.peek());
	}
	public GuiManager getGuiManager() {
		return guiManager;
	}
	public GameAble getGame() {
		return game;
	}
	public Connector getConnector() {
		return connector;
	}

}
