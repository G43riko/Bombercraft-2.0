package Bombercraft2.Bombercraft2.game.player;

import java.awt.Graphics2D;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Healtable;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import utils.Utils;
import utils.math.GVector2f;

public class Player extends Entity implements Healtable{
	public enum Direction{
		UP(2, new GVector2f(0, -1)), 
		DOWN(3, new GVector2f(0, 1)), 
		LEFT(0, new GVector2f(-1, 0)), 
		RIGHT(1, new GVector2f(1, 0));
		
		private int id;
		private GVector2f direction;
		private Direction(int id, GVector2f direction){
			this.id = id;
			this.direction = direction;
		}
		public int getID(){return id;}
		public GVector2f getDirection(){return direction;}
		public static Direction getRandomDirection() {return Direction.values()[(int)(Math.random() * 4)];}
	}
	private int			speed;
	private int			healt;
	private int			range;
	private Direction	direction		= Direction.DOWN;
	private int			demage;
	private String		name;
	private String		image;
	private boolean		moving;
	private boolean		wasMoving		= false;
	private final int	movingLimit		= 1; 
	private int 		movingCounter	= 0;

	
	public Player(GameAble parent, JSONObject object){
		super(new GVector2f(), parent);
		try{
			position 	= new GVector2f(object.getString(Texts.POSITION));
			speed 		= object.getInt(Texts.SPEED);
			healt 		= object.getInt(Texts.HEALTH);
			range 		= object.getInt(Texts.RANGE);
			name 		= object.getString(Texts.NAME);
			demage 		= 1;
			setImage(object.getString(Texts.IMAGE));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player(GameAble parent, GVector2f position, String name, int speed, int healt, String image, int range) {
		super(position, parent);
		this.speed 	= speed;
		this.healt 	= healt;
		this.range 	= range;
		this.name 	= name;
		this.demage = 1;
		setImage(image);
	}
	
	public void move(GVector2f move){
		position = position.add(move.mul(speed));
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(getParent() == null || position == null)
			return;
		

		if(moving){
			if(movingCounter++ >= movingLimit){
				moving = false;
			}
		}
		
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

		GVector2f size = Block.SIZE.mul(getParent().getZoom());
		
		PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage(), isMoving());
		
		
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put(Texts.POSITION, position);
			result.put(Texts.SPEED, speed);
			result.put(Texts.HEALTH, healt);
			result.put(Texts.IMAGE, image);
			result.put(Texts.RANGE, range);
			result.put(Texts.NAME, name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		return name + ": [image: " + image + ", pos: " + position +"]"; 
	}
	public void respawn() {
		position = getParent().getLevel().getRandomRespawnZone();
	}
	
	public GVector2f getSelectorPos(){
		GVector2f pos = getPosition().add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		pos = pos.add(Utils.getNormalMoveFromDir(getDirection())).mul(Block.SIZE);
		return pos;
	}
	
	
	public boolean 		isMoving() {return moving;}
	public String 		getImage() {return image;}
	public String 		getName() {return name;}
	public int 			getSpeed() {return speed;}
	public int 			getDemage() {return demage;}
	public Direction	getDirection() {return direction;}

	public void setDirection(Direction direction) {this.direction = direction;}
	public void setMoving(boolean moving) {
		this.moving = moving;
		movingCounter = 0;
	}
	public void setImage(String image) {
		this.image = image;
		PlayerSprite.setSprite(image, 5, 5, 4, 6);
	}

	@Override
	public int getMaxHealt() {
		return Config.PLAYER_MAX_HEALT;
	}
	
	@Override
	public boolean isAlive() {
		return healt > 0;
	}

	@Override
	public int getActHealt() {
		return healt;
	}

	public void hit(int demage) {
		healt -= demage;
	}

	

}
