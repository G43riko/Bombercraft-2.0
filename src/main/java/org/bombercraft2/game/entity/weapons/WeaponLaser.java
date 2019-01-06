package org.bombercraft2.game.entity.weapons;

import org.bombercraft2.game.GameAble;
import org.glib2.math.vectors.GVector2f;
import org.json.JSONObject;

public class WeaponLaser extends Weapon {
    public WeaponLaser(GameAble parent, JSONObject data) {
        super(parent, Types.LASER, data);
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
    }

}
