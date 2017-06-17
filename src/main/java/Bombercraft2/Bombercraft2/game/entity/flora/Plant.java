package Bombercraft2.Bombercraft2.game.entity.flora;

import java.awt.Graphics2D;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

public class Plant extends Flora{
	private float scale = (float)Math.random() / 4 + 0.175f;
	public Plant(Plants type, GVector2f position, GameAble parent) {
		super(position, parent, type);
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset());
		g2.drawImage(type.getImage(), 
					 pos.getXi(), 
					 pos.getYi(), 
					 (int)(type.getSize().getX() * scale), 
					 (int)(type.getSize().getY() * scale),
					 null);
		super.render(g2);
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
		return type.getSize().mul(scale);
	}
}