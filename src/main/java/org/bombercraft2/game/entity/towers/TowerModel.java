package org.bombercraft2.game.entity.towers;

import org.bombercraft2.core.Texts;
import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.bullets.BulletManager;
import org.bombercraft2.game.entity.bullets.BulletModel;
import org.glib2.interfaces.JSONAble;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class TowerModel implements JSONAble {

    //	private BVector2f 	size;
    private int         canonSpeed;
    private int         maxHealth;
    private int         damage;
    private Helper.Type type;
    private BulletModel bulletType;

    public TowerModel(JSONObject data) {
        fromJSON(data);
    }

    @Override
    public void fromJSON(@NotNull JSONObject data) {
        try {
            this.damage = data.getInt(Texts.DAMAGE);
            this.canonSpeed = data.getInt("canonSpeed");
            this.maxHealth = data.getInt(Texts.HEALTH);
            this.bulletType = BulletManager.getBulletModel(data.getString(Texts.BULLET_TYPE));
//			this.size 			= new BVector2f(data.getString(Texts.SIZE));
            this.type = Helper.Type.valueOf(data.getString(Texts.TYPE));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public BulletManager.Types getBulletType() {return bulletType.getType();}

    public int getMaxHealth() {return maxHealth;}

    //	public BVector2f getSize() {return size;}
    public int getDamage() {return damage;}

    public Helper.Type getType() {return type;}

    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

}
