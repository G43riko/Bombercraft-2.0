package Bombercraft2.Bombercraft2.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

public class PlayerPointer {
	private MyPlayer 	parent;
	private float 		angle 		= 0;
	private float 		rotSpeed 	= 0.1f;
	private int 		length 		= 60;
	private int			width 		= 5;
	private Color		color		= Color.white;
	public PlayerPointer(MyPlayer parent){
		this.parent = parent;
	}
	

	public void render(Graphics2D g2){
		GVector2f positionStart = parent.getPosition().add(Block.SIZE.div(2).sub(parent.getOffset()));
		GVector2f positionEnd 	= new GVector2f(positionStart);
		positionEnd.addToX((float)Math.cos(angle) * length);
		positionEnd.addToY((float)Math.sin(-angle) * length);
		

		g2.setStroke(new BasicStroke(width));
		g2.setColor(color);
		g2.drawLine(positionStart.getXi(), positionStart.getYi(), positionEnd.getXi(), positionEnd.getYi());
	}
	public void input(){
		double pi2 = Math.PI * 2;
		if(Input.isKeyDown(Input.KEY_ARROW_LEFT)){
			angle += rotSpeed;
			if(angle > pi2){
				angle -= pi2;
			}
		}
		else if(Input.isKeyDown(Input.KEY_ARROW_RIGHT)){
			angle -= rotSpeed;
			if(angle < 0){
				angle += pi2;
			}
		}
	}
	public void update(float delta){
		
	}
}
