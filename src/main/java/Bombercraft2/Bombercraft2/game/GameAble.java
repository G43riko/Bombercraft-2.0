package Bombercraft2.Bombercraft2.game;
import java.awt.Canvas;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Profil;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import utils.math.GVector2f;

public interface GameAble extends Interactable, Visible{
	public Connector 				getConnector();
//	public Item 					getItem(String string);
	public Level 					getLevel();
	public float 					getZoom();
	public GVector2f 				getOffset();
	public Canvas 					getCanvas();
//	public NavBar 					getNavBar();
	public MyPlayer 				getMyPlayer();
	public Player 					getPlayerByName(String name);
//	public ArrayList<Enemy> 		getEnemiesAround(GVector2f position, int range);
	public ToolManager				getToolsManager();
	public Profil 					getProfil();
	public ArrayList<String> 		getLogInfos();
	public SceneManager 			getSceneManager();
	public GVector2f				getPlayerTarget();
	public boolean 					isVisible(Visible b);
	public String 					getLabelOf(String key);
//	public void addHelper(GVector2f selectorSur, 
//						  int cadenceBonus,
//						  GVector2f pos, 
//						  int demage,  
//						  String type, 
//						  GVector2f target);
	public void addBullet(GVector2f position, 
						  GVector2f direction, 
						  int bulletSpeed, 
						  int attack, 
						  String bulletType, 
						  int bulletDefaultHealt);

	public boolean hasWall(float i, float j);
	public boolean getVisibleOption(String key);
	public void switchVisibleOption(String key);
	/**
	 * @deprecated since 19.6.2017 - use {@link GameAble#putHelper}
	 * @param position
	 * @param range
	 * @param time
	 * @param demage
	 */
	public void addBomb(GVector2f position, int range, int time, int demage);
	public void addPlayer(String name, String image);
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number);
	public void addEmmiter(GVector2f position, Emitter.Types type);
	public void addEnemy(GVector2f position, String type);
	
//	public boolean 		bulletHitEnemy(Bullet bullet);
	public void 		calcPosition();
	public void 		newGame();
	public void 		resetGame();
	public JSONObject 	toJSON();
	public void 		changeZoom(float f);
	
	/**
	 * 
	 * @param pos
	 * @param type
	 * @param createdTime - cas vytvorenie objektu u klienta pre pripadny delay siete
	 */
	public void 	putHelper(GVector2f pos, Type type, long createdTime);
	public void		explodeBombAt(GVector2f globalPosToLocalPos);
	public String 	getBasicInfo();
	public String 	getGameInfo();
	public void removePlayer(String name);
	public JSONObject getWeapon(String weaponLaser);
//	public void addBullet(Bullet bullet);
	public GVector2f gePlyerDirection();
	public void addBullet(BulletManager.Types bulletType, GVector2f angle,GVector2f position);
	
}
