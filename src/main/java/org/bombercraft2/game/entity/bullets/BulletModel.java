package org.bombercraft2.game.entity.bullets;

import org.bombercraft2.core.Texts;
import org.bombercraft2.game.entity.bullets.BulletManager.Types;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.BVector2f;

import java.awt.*;

public class BulletModel {
//	public final static String BULLET_BASIC 	= "bulletBasic";
//	public final static String BULLET_LASER 	= "bulletLaser";
//	public final static String BULLET_ARROW 	= "bulletArrow";
//	public final static String BULLET_MAGIC 	= "bulletMagic";
//	public final static String BULLET_BOOMERANG = "bulletBoomerang";

    private EmitterTypes emitterOnHit;// = Emitter.PARTICLE_EXPLOSION_TEST;

    private BVector2f size;
    private int       speed;
    private int       maxHealth;
    private int       damage;
    private Color     color;
    private Types     type;

    public BulletModel(JSONObject data) {
        fromJSON(data);
    }

    private void fromJSON(JSONObject data) {
        try {
            if (data.has(Texts.EMITTER_ON_HIT) && !data.isNull(Texts.EMITTER_ON_HIT)) {
                this.emitterOnHit = EmitterTypes.valueOf(data.getString(Texts.EMITTER_ON_HIT));
            }
            this.damage = data.getInt(Texts.DAMAGE);
            this.speed = data.getInt(Texts.SPEED);
            this.maxHealth = data.getInt(Texts.HEALTH);
            this.color = new Color(data.getInt(Texts.COLOR));
            this.size = new BVector2f(data.getString(Texts.SIZE));
            this.type = Types.valueOf(data.getString(Texts.TYPE));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public EmitterTypes getEmitterOnHit() {return emitterOnHit;}

    public void setEmitterOnHit(EmitterTypes emitterOnHit) {this.emitterOnHit = emitterOnHit;}

    public int getMaxHealth() {return maxHealth;}

    public BVector2f getSize() {return size;}

    public int getDamage() {return damage;}

    public Color getColor() {return color;}

    public Types getType() {return type;}

    public void setType(Types type) {this.type = type;}

    public int getSpeed() {return speed;}


}
