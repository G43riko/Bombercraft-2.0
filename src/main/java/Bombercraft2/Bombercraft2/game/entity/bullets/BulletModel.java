package Bombercraft2.Bombercraft2.game.entity.bullets;

import java.awt.Color;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public class BulletModel{
//	public final static String BULLET_BASIC 	= "bulletBasic";
//	public final static String BULLET_LASER 	= "bulletLaser";
//	public final static String BULLET_ARROW 	= "bulletArrow";
//	public final static String BULLET_MAGIC 	= "bulletMagic";
//	public final static String BULLET_BOOMERANG = "bulletBoomerang";
	
	protected Emitter.Types emitterOnHit;// = Emitter.PARTICLE_EXPLOSION_TEST;
	
	private GVector2f 	size; 
	private int 		speed;
	private int 		maxHealt;
	private int 		demage;
	private Color 		color;
	private Types 		type;
	//CONTRUCTORS
	
	public BulletModel (JSONObject data){
		fromJSON(data);
	}
	
	private void fromJSON(JSONObject data){
		try {
			if(data.has(Texts.EMITTER_ON_HIT) && !data.isNull(Texts.EMITTER_ON_HIT)){
				this.emitterOnHit 	= Emitter.Types.valueOf(data.getString(Texts.EMITTER_ON_HIT));
			}
			this.demage 		= data.getInt(Texts.DAMAGE);
			this.speed 			= data.getInt(Texts.SPEED);
			this.maxHealt 		= data.getInt(Texts.HEALTH);
			this.color 			= new Color(data.getInt(Texts.COLOR));
			this.size 			= new GVector2f(data.getString(Texts.SIZE));
			this.type 			= Types.valueOf(data.getString(Texts.TYPE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Emitter.Types getEmitterOnHit() {return emitterOnHit;}
	public int getMaxHealt() {return maxHealt;}
	public GVector2f getSize() {return size;}
	public int getDemage() {return demage;}
	public Color getColor() {return color;}
	public Types getType() {return type;}
	public int getSpeed() {return speed;}
	

	public void setEmitterOnHit(Emitter.Types emitterOnHit) {this.emitterOnHit = emitterOnHit;}
	public void setType(Types type) {this.type = type;}

	//OVERRIDES
	


}
