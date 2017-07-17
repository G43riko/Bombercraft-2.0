package Bombercraft2.Bombercraft2.game.entity.towers;

import java.awt.Color;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletModel;
import utils.resouces.JSONAble;

public class TowerModel implements JSONAble{

//	private GVector2f 	size; 
	private int 		canonSpeed;
	private int 		maxHealt;
	private int 		demage;
	private Helper.Type type;
	private BulletModel	bulletType;
	
	public TowerModel (JSONObject data){
		fromJSON(data);
	}
	@Override
	public void fromJSON(JSONObject data) {
		try {
			this.demage 		= data.getInt(Texts.DAMAGE);
			this.canonSpeed 	= data.getInt("canonSpeed");
			this.maxHealt 		= data.getInt(Texts.HEALTH);
			this.bulletType		= BulletManager.getBulletModel(data.getString(Texts.BULLET_TYPE));
//			this.size 			= new GVector2f(data.getString(Texts.SIZE));
			this.type 			= Helper.Type.valueOf(data.getString(Texts.TYPE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public BulletManager.Types	getBulletType() {return bulletType.getType();}
	public int getMaxHealt() {return maxHealt;}
//	public GVector2f getSize() {return size;}
	public int getDemage() {return demage;}
	public Helper.Type getType() {return type;}

	@Override
	public JSONObject toJSON() {
		return null;
	}

}
