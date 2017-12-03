package Bombercraft2.Bombercraft2.game.entity.weapons;

import Bombercraft2.Bombercraft2.game.GameAble;
import org.json.JSONObject;
import utils.math.GVector2f;

public class WeaponLaser extends Weapon {
    public WeaponLaser(GameAble parent, JSONObject data) {
        super(parent, Types.LASER, data);
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
    }

}
