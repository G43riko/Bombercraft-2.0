package Bombercraft2.Bombercraft2.game.entity.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public class Particle extends Entity{
	private GVector2f 	direction;
	private GVector2f 	size;
	private Color 		color;
	private int 		healt;
	
	//CONTRUCTORS
	
	public Particle(GVector2f position, GameAble parent, Color color, GVector2f direction, GVector2f size, int healt) {
		super(position, parent);
		this.direction 	= direction;
		this.color 		= color;
		this.healt 		= healt;
		this.size 		= size;
	}

	//OVERRIDES
	
	@Override
	public void update(float delta) {
		position = position.add(direction.mul(getParent().getZoom()));
		if(--healt <= 0)
			alive = false;
			
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(size.div(2)).mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = size.mul(getParent().getZoom());
		g2.setColor(color);
		g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public GVector2f getPosition() {
		return super.getPosition().div(getParent().getZoom());
	}
	
	//GETTERS
	
	public GVector2f getSize() {return size;}

}
