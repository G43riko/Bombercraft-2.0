package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import utils.math.GVector2f;

public interface Shootable {
	public GVector2f 			getPosition();
//	public GVector2f 			getTargetLocation();
	public GVector2f 			getDirection();
	public int 					getDemage();
	public int 					getBulletSpeed();
	public int 					getBulletMaxHealt();
	public BulletManager.Types	getBulletType();
}
