package Bombercraft2.Bombercraft2.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Timer;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import utils.GLogger;
import utils.math.GVector2f;

public class Bomb extends Helper{
	public final static int 	BOMB_WIDTH 		= 64;
	public final static int 	BOMB_HEIGHT 	= 64;
	public final static Color	BOMB_AREA_COLOR = new Color(255, 255, 0, 100);
	public final static int 	BOMB_AREA_ROUND	= 10;
	
	private Helper.Type type;
	private long 		addedAt;
	private int 		detonationTime			= 2000;
	private Timer		timer;
	private int 		range					= 3;
	public Bomb(GVector2f position, GameAble parent, Helper.Type type, long addedAt) {
		super(position, parent);
		this.type = type;
		this.addedAt = addedAt;
		timer = new Timer(this, addedAt, detonationTime);
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f actPos = position.sub(getParent().getOffset());

		renderSimpleArea(g2, actPos);
		
		timer.render(g2);
		g2.setColor(Color.BLACK);
		g2.fillArc(actPos.getXi(), actPos.getYi(), BOMB_WIDTH, BOMB_HEIGHT, 0, 360);
	}
	
	private void renderSimpleArea(Graphics2D g2, GVector2f actPos){
		GVector2f localPos = Map.globalPosToLocalPos(position);
		int value, counter;
		g2.setColor(BOMB_AREA_COLOR);
		
		
		//RIGHT
		value = localPos.getXi() + 1;
		counter = 0;
		while(getParent().getLevel().getMap().isWalkable(value, localPos.getYi()) && counter < range){
			value++;
			counter++;
		}

		g2.fillRect(actPos.getXi() + Block.SIZE.getXi(), 
					actPos.getYi(), 
					Block.SIZE.getXi() * counter, 
					Block.SIZE.getYi());
		
		//LEFT
		value = localPos.getXi() - 1;
		counter = 0;
		while(getParent().getLevel().getMap().isWalkable(value, localPos.getYi()) && counter < range){
			value--;
			counter++;
		}
		
		g2.fillRect(actPos.getXi() - Block.SIZE.getXi() * counter, 
					actPos.getYi(), 
					Block.SIZE.getXi() * counter, 
					Block.SIZE.getYi());
		
		
		//DOWN
		value = localPos.getYi() + 1;
		counter = 0;
		while(getParent().getLevel().getMap().isWalkable(localPos.getXi(), value) && counter < range){
			value++;
			counter++;
		}
		
		g2.fillRect(actPos.getXi(), 
					actPos.getYi() + Block.SIZE.getYi(), 
					Block.SIZE.getXi(), 
					Block.SIZE.getYi() * counter);
		
		//UP
		value = localPos.getYi() - 1;
		counter = 0;
		while(getParent().getLevel().getMap().isWalkable(localPos.getXi(), value) && counter < range){
			value--;
			counter++;
		}

		g2.fillRect(actPos.getXi(), 
					actPos.getYi() - Block.SIZE.getYi() * counter, 
					Block.SIZE.getXi(), 
					Block.SIZE.getYi() * counter);
	}
	
	
	@Override
	public void update(float delta) {
		timer.update(delta);
		if(addedAt + detonationTime < System.currentTimeMillis()){
			explode();
		}
	}
	
	public void explode(){
		getParent().explodeBombAt(Map.globalPosToLocalPos(position));
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}
	
}
