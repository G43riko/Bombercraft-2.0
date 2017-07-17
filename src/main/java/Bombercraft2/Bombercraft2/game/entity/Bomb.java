package Bombercraft2.Bombercraft2.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Timer;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import utils.GLogger;
import utils.math.GVector2f;

public class Bomb extends Helper{
	
	private long 			addedAt;
	private int 			detonationTime	= 2000;
	private Timer			timer;
	private int 			range			= 3;
	private List<Block> 	blocks			= new ArrayList<Block>();
	private List<GVector2f> demageAreas		= new ArrayList<GVector2f>();
	public Bomb(GVector2f position, GameAble parent, Helper.Type type, long addedAt) {
		super(position, parent, type);
		this.addedAt = addedAt;
		timer = new Timer(this, addedAt, detonationTime);
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(!alive){
			return;
		}
		GVector2f actPos = position.sub(getParent().getOffset());

		renderSimpleArea(g2, actPos);
		
		timer.render(g2);
		g2.setColor(Color.BLACK);
		g2.fillArc(actPos.getXi(), actPos.getYi(), Config.BOMB_WIDTH, Config.BOMB_HEIGHT, 0, 360);
	}
	
	private void renderSimpleArea(Graphics2D g2, GVector2f actPos){
		GVector2f localPos = Map.globalPosToLocalPos(position);
		int value, counter;
		g2.setColor(Config.BOMB_AREA_COLOR);
		
		
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
	
	private void calcTargetBlocks(){
		GVector2f actPos = position.sub(getParent().getOffset());
		GVector2f localPos = Map.globalPosToLocalPos(position);
		int value, counter;
		Block b;
		
		
		//RIGHT
		value = localPos.getXi() + 1;
		counter = 0;
		b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
		while(b != null && b.isWalkable() && counter + 1 < range){
			value++;
			counter++;
			blocks.add(b);
			b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
		}
		if(b != null && !b.isWalkable()){
			blocks.add(b);
		}
		counter++;
		demageAreas.add(new GVector2f(position.getXi() + Block.SIZE.getXi(), position.getYi()));
		demageAreas.add(new GVector2f(Block.SIZE.getXi() * counter, Block.SIZE.getYi()));
		

		
		//LEFT
		value = localPos.getXi() - 1;
		counter = 0;
		b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
		while(b != null && b.isWalkable() && counter + 1 < range){
			value--;
			counter++;
			blocks.add(b);
			b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
		}
		if(b != null && !b.isWalkable()){
			blocks.add(b);
		}
		counter++;
		demageAreas.add(new GVector2f(position.getXi() - Block.SIZE.getXi() * counter, position.getYi()));
		demageAreas.add(new GVector2f(Block.SIZE.getXi() * counter, Block.SIZE.getYi()));
		
		
		//DOWN
		value = localPos.getYi() + 1;
		counter = 0;
		b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
		while(b != null && b.isWalkable() && counter + 1 < range){
			value++;
			counter++;
			blocks.add(b);
			b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
		}
		if(b != null && !b.isWalkable()){
			blocks.add(b);
		}
		counter++;
		demageAreas.add(new GVector2f(position.getXi(), position.getYi() + Block.SIZE.getYi()));
		demageAreas.add(new GVector2f(Block.SIZE.getXi(), Block.SIZE.getYi() * counter));
		
		//UP
		value = localPos.getYi() - 1;
		counter = 0;
		b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
		while(b != null && b.isWalkable() && counter + 1 < range){
			value--;
			counter++;
			blocks.add(b);
			b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
		}
		if(b != null && !b.isWalkable()){
			blocks.add(b);
		}
		counter++;
		demageAreas.add(new GVector2f(position.getXi(), position.getYi() - Block.SIZE.getYi() * counter));
		demageAreas.add(new GVector2f(Block.SIZE.getXi(), Block.SIZE.getYi() * counter));
		
	}
	@Override
	public void update(float delta) {
		timer.update(delta);
		if(addedAt + detonationTime < System.currentTimeMillis()){
			explode();
		}
	}
	
	public void explode(){
		alive = false;
		calcTargetBlocks();
		getParent().getConnector().setBombExplode(Map.globalPosToLocalPos(position), blocks, demageAreas);
		getParent().addExplosion(position.add(Block.SIZE.div(2)), Block.SIZE, Color.black, 15, true, true);
	}
	
	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
