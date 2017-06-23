package Bombercraft2.Bombercraft2.game.entity.weapons;

import java.awt.Image;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Map;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public class WeaponLaser extends Weapon{
	public WeaponLaser(GameAble parent, JSONObject data) {
		super(parent, Types.LASER, data);
	}
	@Override
	public void useOnLocalPos(GVector2f pos) {
		getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
	}

}
