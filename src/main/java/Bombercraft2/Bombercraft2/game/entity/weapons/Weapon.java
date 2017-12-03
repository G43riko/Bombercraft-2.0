package Bombercraft2.Bombercraft2.game.entity.weapons;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletModel;
import Bombercraft2.Bombercraft2.game.player.Toolable;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.GVector2f;

public abstract class Weapon implements Shootable, Toolable {
    public enum Types {
        LASER("laser");

        private final String name;

        Types(String name) {this.name = name;}

        public String getName() {return name;}
    }

    //	private static HashMap<Helper.Type, Weapon> weapons = new HashMap<Helper.Type, Weapon>();
    private       int         damage;
    private       int         cadence;
    private final Types       type;
    private       BulletModel bulletType;
    private       long        lastShot;
    private final GameAble    parent;

    //CONSTRUCTORS
    public Weapon(GameAble parent, Types type, JSONObject data) {
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

    protected GameAble getParent() {
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

    public Types getType() {return type;}

    public int getDamage() {return damage;}

    public GVector2f getPosition() {return new GVector2f();}

    public GVector2f getDirection() {return new GVector2f();}

    public BulletManager.Types getBulletType() {return bulletType.getType();}

    public int getBulletSpeed() {return bulletType.getSpeed() + parent.getMyPlayer().getSpeedBonus();}

    public int getBulletMaxHealth() {return bulletType.getMaxHealth();}
    //SETTERS


    public void setLastShot() {lastShot = System.currentTimeMillis();}
}
