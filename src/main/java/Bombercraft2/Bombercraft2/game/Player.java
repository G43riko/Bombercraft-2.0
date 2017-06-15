package Bombercraft2.Bombercraft2.game;

import java.awt.Graphics2D;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.PlayerSprite;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.Utils;
import utils.math.GVector2f;

public class Player extends Entity implements Healtable{
	public enum Direction{
		UP(2), DOWN(3), LEFT(0), RIGHT(1);
		
		private int id;
		private Direction(int id){
			this.id = id;
		}
		public int getID(){
			return id;
		}
	}
	private int			speed;
	private int			healt;
	private int			range;
	private Direction	direction		= Direction.DOWN;
	private int			demage;
	private int			cadenceBonus	= 0;
	private String		name;
	private String		image;
	private boolean		moving;

	
	public Player(GameAble parent, JSONObject object){
		super(new GVector2f(), parent);
		try{
			position = new GVector2f(object.getString("position"));
			speed = object.getInt("speed");
			healt = object.getInt("healt");
			range = object.getInt("range");
			name = object.getString("name");
			demage = 1;
			setImage(object.getString("image"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player(GameAble parent, GVector2f position, String name, int speed, int healt, String image, int range) {
		super(position, parent);
		this.speed = speed;
		this.healt = healt;
		this.range = range;
		this.name = name;
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
		
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

		GVector2f size = Block.SIZE.mul(getParent().getZoom());
		
		PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage(), isMoving());
		
		
	}
	
	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("position", position);
			result.put("name", name);
			result.put("speed", speed);
			result.put("healt", healt);
			result.put("image", image);
			result.put("range", range);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}

	public void respawn() {
		position = getParent().getLevel().getPlayerRespawnZone();
	}
	
	public GVector2f getSelectorPos(){
		GVector2f pos = getPosition().add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		pos = pos.add(Utils.getNormalMoveFromDir(getDirection())).mul(Block.SIZE);
		return pos;
	}
	
	@Override
	public GVector2f getSur() {return position.div(Block.SIZE).toInt();}
	
	@Override
	public GVector2f	getSize() {return Block.SIZE;}
	public boolean 		isMoving() {return moving;}
	public String 		getImage() {return image;}
	public String 		getName() {return name;}
	public int 			getSpeed() {return speed;}
	public int 			getDemage() {return demage;}
	public Direction	getDirection() {return direction;}
	public int 			getCadenceBonus() {return cadenceBonus;}

	public void setDirection(Direction direction) {this.direction = direction;}
	public void setMoving(boolean moving) {this.moving = moving;}
	public void setImage(String image) {
		this.image = image;
		PlayerSprite.setSprite(image, 5, 5, 4, 6);
	}

	@Override
	public int getMaxHealt() {
		return Config.PLAYER_MAX_HEALT;
	}

	@Override
	public int getActHealt() {
		return healt;
	}

	

}
