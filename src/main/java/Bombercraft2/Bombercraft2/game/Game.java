package Bombercraft2.Bombercraft2.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.gui.GameGui;
import Bombercraft2.engine.Input;
import utils.ImageUtils;
import utils.math.GVector2f;
import utils.resouces.ResourceSaver;

public class Game extends GameState implements GameAble{
	private MyPlayer				myPlayer;
	private Level					level;
	private float					zoom			= Config.DEFAULT_ZOOM;
	private GameGui					gui;
	private CoreGame				parent;
	private MouseSelector			mouseSelector;//	= new MouseSelector(this);
	private HashMap<String, Player>	players			= new HashMap<String, Player>();
	
	//pre pripadny currentModificationException ak sa chce upravit nieco co sa pouziva
	private boolean					render		= true;
	private boolean					update		= true;
	private boolean					input		= true;
	public Game(Level level, CoreGame parent, String game) {
		super(GameState.Type.Game);
		this.level = level;
		this.parent = parent;
		level.setGame(this);
		
		try {
			myPlayer = new MyPlayer(this,
									level.getPlayerRespawnZone() , 
									getProfil().getName(), 
									level.getDefaultPlayerInfo().getInt("speed"), 
									level.getDefaultPlayerInfo().getInt("healt"), 
									getProfil().getAvatar(), 
									level.getDefaultPlayerInfo().getInt("range"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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

		new HashMap<String, Player>(players).entrySet()
											.stream()
											.map(a -> a.getValue())
											.filter(this::isVisible)
											.forEach(a -> a.render(g2));
		

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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GVector2f getSize() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		return !(b.getPosition().getX() * getZoom()  + Config.DEFAULT_BLOCK_WIDTH * getZoom()  < getOffset().getX() || 
				 b.getPosition().getY() * getZoom()  + Config.DEFAULT_BLOCK_HEIGHT * getZoom()  < getOffset().getY() || 
				   getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom()    ||
				   getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom() );
	}
	@Override
	public boolean hasWall(float i, float j) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean getVisibleOption(String key) {
		return parent.getVisibleOption(key);
	}
	public void switchVisibleOption(String key){
		parent.switchVisibleOption(key);
	}
	@Override
	public void addHelper(GVector2f selectorSur, int cadenceBonus, GVector2f pos, int demage, String type,
			GVector2f target) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addBomb(GVector2f position, int range, int time, int demage) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addBullet(GVector2f position, GVector2f direction, int bulletSpeed, int attack, String bulletType,
			int bulletDefaultHealt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addPlayer(String name, String image) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEmmiter(GVector2f position, String type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEnemy(GVector2f position, String type) {
		// TODO Auto-generated method stub
		
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
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
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
}
