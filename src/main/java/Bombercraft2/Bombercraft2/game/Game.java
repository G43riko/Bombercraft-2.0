package Bombercraft2.Bombercraft2.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.level.Map;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.gui.GameGui;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.Bombercraft2.multiplayer.GameServer;
import Bombercraft2.engine.Input;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;

public class Game extends GameState implements GameAble{
	private MyPlayer				myPlayer;
	private Level					level;
	private float					zoom			= Config.DEFAULT_ZOOM;
	private GameGui					gui;
	private ToolManager				toolManager		= new ToolManager(this);
	private HashMap<String, Helper>	helpers			= new HashMap<String, Helper>();
	private CoreGame				parent;
	private MouseSelector			mouseSelector;//	= new MouseSelector(this);
	private HashMap<String, Player>	players			= new HashMap<String, Player>();
	private HashSet<Helper>			helpersRemoved	= new HashSet<Helper>();
	
	//pre pripadny currentModificationException ak sa chce upravit nieco co sa pouziva
	private boolean					render		= true;
	private boolean					update		= true;
	private boolean					input		= true;
	
	public Game(Level level, CoreGame parent, JSONObject gameData) {
		super(GameState.Type.Game);
		this.level = level;
		this.parent = parent;
		level.setGame(this);
		
		try {
			myPlayer = new MyPlayer(this,
									level.getRandomRespawnZone() , 
									getProfil().getName(), 
									level.getDefaultPlayerInfo().getInt("speed"), 
									level.getDefaultPlayerInfo().getInt("healt"), 
									getProfil().getAvatar(), 
									level.getDefaultPlayerInfo().getInt("range"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		gui = new GameGui(this);
		
		players.put(myPlayer.getName(), myPlayer);
		//parent.getCanvas().addMouseWheelListener(this);
	}

	@Override
	public void render(Graphics2D g2) {
		if(!render){
			return;
		}
		g2.clearRect(0, 0, parent.getSize().getXi(), parent.getSize().getYi());
		level.render(g2);
		
		toolManager.render(g2);
		
		players.values().stream().filter(this::isVisible).forEach(a -> a.render(g2));
		helpers.values().stream().filter(this::isVisible).forEach(a -> a.render(g2));

		level.renderUpperFlora(g2);

		if(myPlayer.showSelector()){
			myPlayer.renderSelector(g2);
		}
		
		if(mouseSelector != null){
			mouseSelector.render(g2);
		}
		gui.render(g2);
		
		
	}
	
	public void update(float delta) {
		if(!update){
			return;
		}
		myPlayer.update(delta);
		
		helpers.values().removeAll(helpersRemoved);
		helpersRemoved.clear();
		helpers.values().stream().filter(this::isVisible).forEach(a -> a.update(delta));
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
//		return !(b.getPosition().getX() * getZoom()  + Config.DEFAULT_BLOCK_WIDTH * getZoom()  < getOffset().getX() || 
//				 b.getPosition().getY() * getZoom()  + Config.DEFAULT_BLOCK_HEIGHT * getZoom()  < getOffset().getY() || 
//				   getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom()    ||
//				   getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom() );

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
	@Override
	public void addHelper(GVector2f selectorSur, 
						  int cadenceBonus, 
						  GVector2f pos, 
						  int demage, 
						  String type,
						  GVector2f target) {
		GLogger.notImplemented();
	}
	@Override
	public void addBomb(GVector2f position, int range, int time, int demage) {
		GLogger.notImplemented();
	}
	@Override
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
		GLogger.notImplemented();
	}
	@Override
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number) {
		GLogger.notImplemented();
	}
	@Override
	public void addEmmiter(GVector2f position, String type) {
		GLogger.notImplemented();
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
		GLogger.notImplemented();
		return new JSONObject();
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
	public GVector2f getPlayerTarget() {
		return myPlayer.getTargetLocation();
	}

	@Override
	public Connector getConnector() {
		return parent.getConnector();
	}

	@Override
	public void putHelper(GVector2f pos, Helper.Type type, long createTime) {
		GVector2f localPos = Map.globalPosToLocalPos(pos);
		pos = pos.div(Block.SIZE).toInt().mul(Block.SIZE);
		
		String key = localPos.getXi() + "_" + localPos.getYi();
		if(helpers.containsKey(key)){
			GLogger.printLine("Vytvara sa helper na helpere");
			return;
		}
		switch(type){
			case BOMB_NORMAL:
				helpers.put(key, new Bomb(pos, this, type, createTime));
				break;
		}
	}

	@Override
	public void explodeBombAt(GVector2f pos) {
		String key = pos.getXi() + "_" + pos.getYi();
		
		if(!helpers.containsKey(key)){
			GLogger.printLine("Explodovala neexistujuca bomba na: " + pos);
			return;
		}
		helpersRemoved.add(helpers.get(key));
	}

	@Override
	public MyPlayer getMyPlayer() {
		return myPlayer;
	}

	@Override
	public HashMap<String, Player> getPlayers() {
		return players;
	}

	@Override
	public String getBasicInfo() {
		try {
			JSONObject o = new JSONObject();
			o.put("level", level.toJSON());
			o.put("game", toJSON());
			return o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
