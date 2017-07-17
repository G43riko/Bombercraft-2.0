package Bombercraft2.Bombercraft2.game.bots;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotManager.Types;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.flora.Flora.Plants;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import utils.math.GVector2f;

public abstract class Bot extends Entity{
	protected Direction direction;
	protected int 		health;
	protected BotModel	model;
	
	public Bot(GVector2f position, GameAble parent, Types type, Direction direction) {
		super(position, parent);
		model = BotManager.getBotModel(type);
		this.direction = direction;
		health = model.getMaxHealt();
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void hit(int demage){
		health -= demage;
		if(health <= 0){
			alive = false;
		}
	}
	
	public int getSpeed(){return model.getSpeed();}

}
