package org.bombercraft2.game.entity;

import org.bombercraft2.game.entity.bullets.BulletManager;
import utils.math.BVector2f;

public interface ShootAble {
    BVector2f getPosition();

    //	BVector2f 			getTargetLocation();
    BVector2f getDirection();

    int getDamage();

    int getBulletSpeed();

    int getBulletMaxHealth();

    BulletManager.Types getBulletType();
}
