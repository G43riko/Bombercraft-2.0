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
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import utils.math.GVector2f;

public interface GameAble extends Interactable, Visible{
	public JSONObject 				getWeapon(String weaponLaser);
	public boolean 					getVisibleOption(String key);
	public Player 					getPlayerByName(String name);
	public String 					getLabelOf(String key);
	public GVector2f 				gePlyerDirection();
	public SceneManager 			getSceneManager();
	public ToolManager				getToolsManager();
	public GVector2f				getPlayerTarget();
	public Connector 				getConnector();
	public String 					getBasicInfo();
	public ArrayList<String>		getLogInfos();
	public MyPlayer 				getMyPlayer();
	public String 					getGameInfo();
	public GVector2f 				getOffset();
	public Canvas 					getCanvas();
	public Profil 					getProfil();
	public Level 					getLevel();
	public float 					getZoom();
	
	public void 					addBullet(BulletManager.Types bulletType, GVector2f angle,GVector2f position);
	public void 					addExplosion(GVector2f position, 
												 GVector2f size, 
												 Color color, 
												 int number,
												 boolean explosion,
												 boolean shockwave);
	/**
	 * 
	 * @param pos
	 * @param type
	 * @param createdTime - cas vytvorenie objektu u klienta pre pripadny delay siete
	 */
	public void 					addHelper(GVector2f pos, Type type, long createdTime);
	public void 					addEmmiter(GVector2f position, Emitter.Types type);
	public void 					addEnemy(GVector2f position, String type);
	public void 					addPlayer(String name, String image);

	public void						explodeBombAt(GVector2f globalPosToLocalPos);
	public void 					switchVisibleOption(String key);
	public boolean 					hasWall(float i, float j);
	public void 					removePlayer(String name);
	public boolean 					isVisible(Visible b);
	public void 					changeZoom(float f);
	public void 					calcPosition();
	public void 					resetGame();
	public void 					newGame();
	public JSONObject 				toJSON();
	public void 					endGame();
	public HashMap<String, String> getStats();
	
	
}
