package Bombercraft2.Bombercraft2.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletBasic;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletLaser;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
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
import utils.GLogger;
import utils.math.GVector2f;

public class Game extends GameState implements GameAble{
	private MyPlayer				myPlayer;
	private Level					level;
	private float					zoom			= Config.DEFAULT_ZOOM;
	private GameGui					gui;
	private ToolManager				toolManager;
	private CoreGame				parent;
	private MouseSelector			mouseSelector;//	= new MouseSelector(this);
	private SceneManager 			sceneManager 	= new SceneManager(this);
	private LightsManager			lightsManager;

	
	//pre pripadny currentModificationException ak sa chce upravit nieco co sa pouziva
	private boolean					render		= true;
	private boolean					update		= true;
	private boolean					input		= true;
	
	public Game(Level level, CoreGame parent, JSONObject gameData) {
		super(GameState.Type.Game);
		this.level = level;
		this.parent = parent;
		level.setGame(this);
		toolManager = new ToolManager(this);
		try {
			myPlayer = new MyPlayer(this,
									level.getRandomRespawnZone() , 
									getProfil().getName(), 
									level.getDefaultPlayerInfo().getInt(Texts.PLAYER_SPEED), 
									level.getDefaultPlayerInfo().getInt(Texts.PLAYER_HEALT), 
									getProfil().getAvatar(), 
									level.getDefaultPlayerInfo().getInt(Texts.PLAYER_RANGE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(gameData != null){
			try {
				for(int i=0 ; i < gameData.getInt(Texts.PLAYERS_NUMBER) ;  i++){
					sceneManager.addPlayer(new Player(this, new JSONObject(gameData.getString("player_" + i))));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			};
		}
		
		lightsManager = new LightsManager(this);
		gui = new GameGui(this);
		sceneManager.addPlayer(myPlayer);
//		parent.getCanvas().addMouseWheelListener(this);
		
		

		lightsManager.addLight(new Light(this, new GVector2f(300,300), new GVector2f(300, 300), myPlayer));
		
		
	}

	@Override
	public void render(Graphics2D g2) {
		if(!render){
			return;
		}
		
		g2.clearRect(0, 0, parent.getSize().getXi(), parent.getSize().getYi());
		level.render(g2);
		
		toolManager.render(g2);
		
		sceneManager.render(g2);

		level.renderUpperFlora(g2);

		if(myPlayer.showSelector()){
			myPlayer.renderSelector(g2);
		}
		
		if(mouseSelector != null){
			mouseSelector.render(g2);
		}

		lightsManager.render(g2);

//		g2.setE
//		g2.setG
//		GraphicsContext
		
		gui.render(g2);
	}
	
	
	
	public void update(float delta) {
		if(!update){
			return;
		}

		myPlayer.update(delta);
		
		sceneManager.update(delta);
		
		gui.update(delta);
	}

	public void input() {
		if(!input){
			return;
		}
		myPlayer.input();
		gui.input();
	}
	@Override
	public GVector2f getPosition() {
		GLogger.notImplemented();
		return null;
	}
	@Override
	public GVector2f getSize() {
		GLogger.notImplemented();
		return null;
	}
	@Override
	public Level getLevel() {
		return level;
	}
	@Override
	public float getZoom() {
		return 1;
	}
	public GVector2f 				getOffset() {return myPlayer.getOffset();}
	public Canvas 					getCanvas(){return parent.getCanvas();}
	@Override
	public Profil getProfil() {
		return parent.getProfil();
	}
	@Override
	public ArrayList<String> getLogInfos() {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(sceneManager.getLogInfos());
		result.add("FPS: " 				+ parent.getFPS());
		result.add("UPS: " 				+ parent.getUPS());
		result.add("loops: " 			+ parent.getLoops());
		result.add("player: " 			+ myPlayer.getPosition().toDecimal(4));
		result.add("offset: " 			+ getOffset().toDecimal(4));
		result.add("mouse: " 			+ Input.getMousePosition().toDecimal(4));
		result.add("send messages: " 	+ Bombercraft.totalMessages.getXi());
		result.add("recieve messages: " + Bombercraft.totalMessages.getYi());
		
		Runtime runtime = Runtime.getRuntime();
		final long usedMem = (runtime.totalMemory() - runtime.freeMemory()) / 1000000;
		result.add("memory: " + String.format("%03d ", usedMem)  + " / " + String.format("%03d ", runtime.totalMemory() / 1000000) + "MB");
//		result.add("helpers: " + renderedHelpers + "/" + helpers.size());
//		result.add("enemies: " + renderedEnemies + "/" + enemies.size());
//		result.add("particles: " + renderedParticles);
		result.add("blocks: " + level.getMap().getRenderedBlocks() + "/" + (int)level.getMap().getNumberOfBlocks().mul());
		result.add("Zoom: " + getZoom());
		return result;
	}
	@Override
	public boolean isVisible(Visible b) {
		return !(b.getPosition().getX() * getZoom()  + b.getSize().getX() * getZoom()  < getOffset().getX() || 
				 b.getPosition().getY() * getZoom()  + b.getSize().getY() * getZoom()  < getOffset().getY() || 
				   getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom()    ||
				   getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom() );
	}

	public String getLabelOf(String key){
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
	public void switchVisibleOption(String key){
		parent.switchVisibleOption(key);
	}
	public void addHelper(GVector2f selectorSur, 
						  int cadenceBonus, 
						  GVector2f pos, 
						  int demage, 
						  String type,
						  GVector2f target) {
		GLogger.notImplemented();
	}
	public void addBomb(GVector2f position, int range, int time, int demage) {
		GLogger.notImplemented();
	}
	public void addBullet(GVector2f position, 
						  GVector2f direction, 
						  int bulletSpeed, 
						  int attack, 
						  String bulletType,
						  int bulletDefaultHealt) {
		GLogger.notImplemented();
	}
	@Override
	public void addPlayer(String name, String image) {
		try {
			sceneManager.addPlayer(new Player(this, 
											  level.getRandomRespawnZone(), 
											  name, 
											  level.getDefaultPlayerInfo().getInt("speed"), 
											  level.getDefaultPlayerInfo().getInt("healt"), 
											  "player1.png", 
											  level.getDefaultPlayerInfo().getInt("range")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number) {
		sceneManager.addExplosion(position, size, color, number);
	}
	@Override
	public void addEmmiter(GVector2f position, Emitter.Types type) {
		sceneManager.addEmmiter(position, type);
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
		parent.showLoading();
		resetGame();
		getLevel().getMap().createRandomMap();
		myPlayer.respawn();
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
	public void changeZoom(float value){
		if(!Config.ZOOM_ALLOWED || !level.isReady())
			return;
		
		zoom += value;
		
		if(level.getMap().getNumberOfBlocks().getX() * Block.SIZE.getX() * zoom < parent.getCanvas().getWidth()){
			zoom -= value; 
			return;
		}
		
		if(zoom > Config.ZOOM_MAXIMUM){
			zoom -= value;
		}
		
	}
	@Override
	public void doAct(GVector2f click) {
		gui.doAct(click);
	}

	@Override
	public ToolManager getToolsManager() {
		return toolManager;
	}

	@Override
	public Connector getConnector() {
		return parent.getConnector();
	}

	@Override
	public void putHelper(GVector2f pos, Helper.Type type, long createTime) {//TODO playerov bonus k poskodeniu tu ma byt
		GVector2f localPos = Map.globalPosToLocalPos(pos);
		pos = pos.div(Block.SIZE).toInt().mul(Block.SIZE);
		
		String key = localPos.getXi() + "_" + localPos.getYi();
		if(sceneManager.existHelperOn(key)){
			GLogger.printLine("Vytvara sa helper na helpere");
			return;
		}
		switch(type){
			case BOMB_NORMAL:
				sceneManager.addHelper(key,  new Bomb(pos, this, type, createTime));
				break;
		}
	}

	@Override
	public void explodeBombAt(GVector2f pos) {
		String key = pos.getXi() + "_" + pos.getYi();
		if(!sceneManager.existHelperOn(key)){
			GLogger.printLine("Explodovala neexistujuca bomba na: " + pos);
			return;
		}
		sceneManager.removeHelper(key);
	}

	@Override
	public MyPlayer getMyPlayer() {
		return myPlayer;
	}

//	@Override
//	public HashMap<String, Player> getPlayers() {
//		return players;
//	}
	
	@Override
	public String getGameInfo() {
		try {
			JSONObject o = new JSONObject();
			o.put(Texts.LEVEL_DATA, level.toJSON());
			o.put(Texts.GAME_DATA, toJSON());
			return o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getBasicInfo() {
		try {
			JSONObject result = new JSONObject();
			result.put(Texts.LEVEL_NAME, "randomLevel");
			result.put(Texts.PLAYER_NAME, getProfil().getName());
			result.put(Texts.MAX_PLAYERS, "5");
			result.put(Texts.PLAYERS_NUMBER, sceneManager.getPlayersCount());
			return result.toString();
		} catch (JSONException e) {
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
	public GVector2f gePlyerDirection() {
		//return Input.getMousePosition().add(myPlayer.getOffset()).sub(myPlayer.getCenter());
		return myPlayer.getTagetDirection();
	}
	@Override
	public GVector2f getPlayerTarget() {
		return myPlayer.getTargetLocation();
	}

	@Override
	public void addBullet(Types bulletType, GVector2f angle, GVector2f position) {
		Bullet bullet = null;
		switch(bulletType) {		
			case LASER:
				bullet = new BulletLaser(position, parent.getGame(), angle);
				break;
			case BASIC:
				bullet = new BulletBasic(position, parent.getGame(), angle);
				break;
		}
		sceneManager.addBullet(bullet);
	}

}
