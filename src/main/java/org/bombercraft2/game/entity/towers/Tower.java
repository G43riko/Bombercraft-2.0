package org.bombercraft2.game.entity.towers;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.ShootAble;
import org.bombercraft2.game.entity.bullets.BulletManager.Types;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.BVector2f;

import java.awt.*;

public abstract class Tower extends Helper implements ShootAble {
    final         int        range           = 100;
    final         int        cannonWidth     = 6;
    final         int        cannonLength    = 40;
    final         float      borderSize      = 1;
    final         double     cannonsSpeed    = Math.PI / 180;
    final         Color      canonColor      = Color.YELLOW;
    final         Color      borderColor     = Color.white;
    final         Color      backgroundColor = Color.cyan;
    final         Color      rangeColor      = new Color(255, 255, 0, 80);
    private final int        cadence         = 0;
    private final TowerModel model           = null;
    Entity target   = null;
    double actAngle = Math.random() * Math.PI * 2;
    double angle    = actAngle;
    private long lastShot    = 0;
    private int  damage      = 0;
    private int  bulletSpeed = 12;

    Tower(BVector2f position, GameAble parent, Helper.Type type) {
        super(position, parent, type);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public BVector2f getDirection() {
        return new BVector2f(Math.sin(actAngle), Math.cos(actAngle)).negate();
    }

    @Override
    public Types getBulletType() {
        return model.getBulletType();
    }

    boolean canShot() {
        return System.currentTimeMillis() - lastShot > cadence;
    }

    void fire() {
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
