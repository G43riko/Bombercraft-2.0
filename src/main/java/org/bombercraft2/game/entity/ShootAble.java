package org.bombercraft2.game.entity;

import org.bombercraft2.game.entity.bullets.BulletManager;
import utils.math.GVector2f;

public interface ShootAble {
    GVector2f getPosition();

    //	GVector2f 			getTargetLocation();
    GVector2f getDirection();

    int getDamage();

    int getBulletSpeed();

    int getBulletMaxHealth();

    BulletManager.Types getBulletType();
}
