package Bombercraft2.Bombercraft2.game.entity.towers;

import java.awt.Color;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public abstract class Tower extends Helper implements Shootable{
	protected Entity 	target = null;
	protected int 	 	range = 100;
	private long 		lastShot;
	private int 		accularity;
	private int 		bulletSpeed = 12;
	private int 		demage;
	protected int 		cannonWidth = 6;
	protected int 		cannonLength = 40;
	protected float 	borderSize = 1;
	protected double 	cannonsSpeed = Math.PI / 180;
	protected double 	actAngle = Math.random() * Math.PI * 2;
	protected double	angle = actAngle;
	protected Color 	canonColor = Color.YELLOW;
	protected Color 	borderColor = Color.white;
	protected Color		backgroundColor = Color.cyan;
	protected Color 	rangeColor = new Color(255, 255, 0, 80);
	private TowerModel 	model;
	public Tower(GVector2f position, GameAble parent, Helper.Type type) {
		super(position, parent, type);
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public GVector2f getSur() {
		return null;
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}

	@Override
	public GVector2f getDirection() {
		return new GVector2f(Math.sin(actAngle), Math.cos(actAngle)).negate();
	}
	
	@Override
	public Types getBulletType() {
		return model.getBulletType();
	}

	public boolean canShot(){
		return System.currentTimeMillis() - lastShot > accularity;
	}
	
	public void fire(){
		getParent().getConnector().setPutBullet(getParent().getMyPlayer(), this);
		lastShot = System.currentTimeMillis();
	}
	@Override
	public int getDemage() {
		return 0;
	}

	@Override
	public int getBulletSpeed() {
		return 0;
	}

	@Override
	public int getBulletMaxHealt() {
		return 0;
	}


}
