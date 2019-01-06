package org.bombercraft2.game.entity.weapons;

import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.ShootAble;
import org.bombercraft2.game.entity.bullets.BulletManager;
import org.bombercraft2.game.entity.bullets.BulletModel;
import org.bombercraft2.game.player.ToolAble;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.BVector2f;

public abstract class Weapon implements ShootAble, ToolAble {
    private final Types       type;
    private final GameAble    parent;
    //	private static HashMap<Helper.Type, Weapon> weapons = new HashMap<Helper.Type, Weapon>();
    private       int         damage;
    private       int         cadence;
    private       BulletModel bulletType;
    private       long        lastShot;

    Weapon(GameAble parent, Types type, JSONObject data) {
        this.parent = parent;
        this.type = type;
        fromJSON(data);
    }

    private void fromJSON(JSONObject data) {
        try {
            this.damage = data.getInt(Texts.DAMAGE);
            this.cadence = data.getInt(Texts.CADENCE);
            this.bulletType = BulletManager.getBulletModel(data.getString(Texts.TYPE));
            lastShot = System.currentTimeMillis() - cadence;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    GameAble getParent() {
        return parent;
    }

    public boolean canShot() {
        return canShot(parent.getMyPlayer().getCadenceBonus());
    }
    //OTHERS

    private boolean canShot(int bonus) {
        return System.currentTimeMillis() - lastShot > cadence - bonus;
    }

    public Types getType() {return type;}

    public int getDamage() {return damage;}

    public BVector2f getPosition() {return new BVector2f();}

    public BVector2f getDirection() {return new BVector2f();}

    public BulletManager.Types getBulletType() {return bulletType.getType();}

    public int getBulletSpeed() {return bulletType.getSpeed() + parent.getMyPlayer().getSpeedBonus();}

    public int getBulletMaxHealth() {return bulletType.getMaxHealth();}

    public void setLastShot() {lastShot = System.currentTimeMillis();}
    //SETTERS


    public enum Types {
        LASER("laser");

        private final String name;

        Types(String name) {this.name = name;}

        public String getName() {return name;}
    }
}
