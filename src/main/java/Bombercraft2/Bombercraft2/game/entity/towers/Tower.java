package Bombercraft2.Bombercraft2.game.entity.towers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public abstract class Tower extends Helper implements Shootable {
    private   long       lastShot        = 0;
    private   int        damage          = 0;
    private   int        cadence         = 0;
    protected Entity     target          = null;
    protected int        range           = 100;
    private   int        bulletSpeed     = 12;
    protected int        cannonWidth     = 6;
    protected int        cannonLength    = 40;
    protected float      borderSize      = 1;
    protected double     cannonsSpeed    = Math.PI / 180;
    protected double     actAngle        = Math.random() * Math.PI * 2;
    protected double     angle           = actAngle;
    protected Color      canonColor      = Color.YELLOW;
    protected Color      borderColor     = Color.white;
    protected Color      backgroundColor = Color.cyan;
    protected Color      rangeColor      = new Color(255, 255, 0, 80);
    private   TowerModel model           = null;

    public Tower(GVector2f position, GameAble parent, Helper.Type type) {
        super(position, parent, type);
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public GVector2f getDirection() {
        return new GVector2f(Math.sin(actAngle), Math.cos(actAngle)).negate();
    }

    @Override
    public Types getBulletType() {
        return model.getBulletType();
    }

    public boolean canShot() {
        return System.currentTimeMillis() - lastShot > cadence;
    }

    public void fire() {
        getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
        lastShot = System.currentTimeMillis();
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getBulletSpeed() {
        return 0;
    }

    @Override
    public int getBulletMaxHealth() {
        return 0;
    }


}
