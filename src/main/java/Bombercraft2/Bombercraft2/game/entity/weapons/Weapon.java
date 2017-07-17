package Bombercraft2.Bombercraft2.game.entity.weapons;

import java.awt.Color;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletModel;
import Bombercraft2.Bombercraft2.game.player.Toolable;
import utils.math.GVector2f;

public abstract class Weapon implements Shootable, Toolable{
	public enum Types{
		LASER("laser");
		
		private String name;
		Types(String name){this.name = name;}
		public String getName(){return name;}
	}
//	private static HashMap<Helper.Type, Weapon> weapons = new HashMap<Helper.Type, Weapon>(); 
	private int 				demage;
	private int 				cadence;
	private Types				type;
	private BulletModel			bulletType;
	private long 				lastShot;
	private GameAble 			parent;
	//CONSTRUCTORS
	public Weapon(GameAble parent, Types type, JSONObject data){
		this.parent = parent;
		this.type = type;
		fromJSON(data);
	}
	private void fromJSON(JSONObject data){
		try {
			this.demage 		= data.getInt(Texts.DAMAGE);
			this.cadence 		= data.getInt(Texts.CADENCE);
			this.bulletType		= BulletManager.getBulletModel(data.getString(Texts.TYPE));
			lastShot = System.currentTimeMillis() - cadence;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected GameAble getParent(){
		return parent;
	}
	//OTHERS
	
	public boolean canShot() {
		return canShot(parent.getMyPlayer().getCadenceBonus());
	}
	
	public boolean canShot(int bonus) {
		return System.currentTimeMillis() - lastShot > cadence - bonus;
	}

	//GETTERS

	public Types				getType(){return type;}
	public int 					getDemage() {return demage;}
	public GVector2f 			getPosition() {return new GVector2f();}
	public GVector2f 			getDirection() {return new GVector2f();}
	
	public BulletManager.Types	getBulletType() {return bulletType.getType();}
	public int 					getBulletSpeed() {return bulletType.getSpeed() + parent.getMyPlayer().getSpeedBonus();}
	public int 					getBulletMaxHealt() {return bulletType.getMaxHealt();}
	//SETTERS
	
	
	public void setLastShot() {lastShot = System.currentTimeMillis();}
}
