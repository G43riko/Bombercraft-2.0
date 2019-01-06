package org.bombercraft2.game.entity.weapons;

import org.bombercraft2.game.GameAble;
import org.json.JSONObject;
import utils.math.BVector2f;

public class WeaponLaser extends Weapon {
    public WeaponLaser(GameAble parent, JSONObject data) {
        super(parent, Types.LASER, data);
    }

    @Override
    public void useOnLocalPos(BVector2f pos) {
        getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
    }

}
