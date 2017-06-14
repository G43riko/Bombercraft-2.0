package Bombercraft2.Bombercraft2.game;

import java.awt.Graphics2D;
import java.util.HashMap;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

public class MyPlayer extends Player{
	private GVector2f		offset;
	private GVector2f		move			= new GVector2f();
	private GVector2f		totalMove		= new GVector2f();
	private boolean			showSelector	= true;
	private PlayerSelector 	selector 		= new PlayerSelector(this);
	private PlayerPointer	pointer			= new PlayerPointer(this);
	
	private HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>(); 
		
	public MyPlayer(GameAble parent, 
					GVector2f position, 
					String name, 
					int speed, 
					int healt, 
					String image, 
					int range) {
		super(parent, position, name, speed, healt, image, range);
		resetOffset();
		checkOffset();
		keys.put(Input.KEY_W, false);
		keys.put(Input.KEY_A, false);
		keys.put(Input.KEY_S, false);
		keys.put(Input.KEY_D, false);
	}
	
	private void resetOffset(){
		offset = new GVector2f(getParent().getCanvas().getWidth(), 
							   getParent().getCanvas().getHeight()).div(-2);;
	}
	
	@Override
	public void input(){
		move = new GVector2f();
		
		if(!keys.get(Input.KEY_W) && Input.isKeyDown(Input.KEY_W))
			setDirection(Direction.UP);
		keys.put(Input.KEY_W, Input.isKeyDown(Input.KEY_W));
		
		
		if(!keys.get(Input.KEY_S) && Input.isKeyDown(Input.KEY_S))
			setDirection(Direction.DOWN);
		keys.put(Input.KEY_S, Input.isKeyDown(Input.KEY_S));

		
		if(!keys.get(Input.KEY_A) && Input.isKeyDown(Input.KEY_A))
			setDirection(Direction.LEFT);
		keys.put(Input.KEY_A, Input.isKeyDown(Input.KEY_A));
		
		
		if(!keys.get(Input.KEY_D) && Input.isKeyDown(Input.KEY_D))
			setDirection(Direction.RIGHT);
		keys.put(Input.KEY_D, Input.isKeyDown(Input.KEY_D));
			
		
		if(keys.get(Input.KEY_W))
			move.addToY(-1);
		if(keys.get(Input.KEY_S))
			move.addToY(1);
		if(keys.get(Input.KEY_A))
			move.addToX(-1);
		if(keys.get(Input.KEY_D))
			move.addToX(1);
		
		
		setMoving(!move.isNull());
		
		
		if(move.getX() < 0 && move.getY() == 0)
			setDirection(Direction.LEFT);
		else if(move.getX() > 0 && move.getY() == 0)
			setDirection(Direction.RIGHT);
		else if(move.getX() == 0 && move.getY() < 0)
			setDirection(Direction.UP);
		else if(move.getX() == 0 && move.getY() > 0)
			setDirection(Direction.DOWN);
		
		pointer.input();

	}
	
	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
		pointer.render(g2);
	}

	public void update(float delta){
		if(position == null)
			return;

		totalMove = totalMove.add(move);
		
		position.addToX(move.getX() * getSpeed() * delta);
		if(isInBlock())
			position.addToX(-move.getX() * getSpeed() * delta);
		
		position.addToY(move.getY() * getSpeed() * delta);
		if(isInBlock())
			position.addToY(-move.getY() * getSpeed() * delta);
		
		checkBorders();
		checkOffset();

		pointer.update(delta);
//		if(!move.isNull())
//			getParent().getConnection().playerNewPos(this);
	}
	
	private boolean isInBlock(){
//		//pri 40x40
//		final float topOffset 		= 20;
//		final float bottomOffset 	= 35;
//		final float rightOffset 	= 11;
//		final float leftOffset 		= 9;
		
		//pri 60x60
		final float topOffset 		= 35;
		final float bottomOffset 	= 65;
		final float rightOffset 	= 21;
		final float leftOffset 		= 19;
		
		GVector2f t = position.add(new GVector2f(Block.SIZE.getX(), Block.SIZE.getY() - topOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f b = position.add(new GVector2f(Block.SIZE.getX(), Block.SIZE.getY() + bottomOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f r = position.add(new GVector2f(Block.SIZE.getX() - rightOffset, Block.SIZE.getY()).div(2)).div(Block.SIZE).toInt();
		GVector2f l = position.add(new GVector2f(Block.SIZE.getX() + leftOffset , Block.SIZE.getY()).div(2)).div(Block.SIZE).toInt();
		
		try{
			return !getParent().getLevel().getMap().getBlock(t.getXi(), t.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(b.getXi(), b.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(r.getXi(), r.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(l.getXi(), l.getYi()).isWalkable();
		}catch(NullPointerException e){
			return true;
		}
	}
	
	public void checkOffset(){
		
		GVector2f pos = getPosition().mul(getParent().getZoom()).add(Block.SIZE.mul(getParent().getZoom() / 2));
		
		offset.setX(pos.getX() - getParent().getCanvas().getWidth() / 2);
		offset.setY(pos.getY() - getParent().getCanvas().getHeight() / 2);

		GVector2f nums = getParent().getLevel().getMap().getNumberOfBlocks();
		
		//skontroluje posun
		if(offset.getX() < 0)
			offset.setX(0);
        
        if(offset.getX() > (nums.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas().getWidth())
        	offset.setX((nums.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas().getWidth());
        
        if(offset.getY() < 0)
        	offset.setY(0);
        
        if(offset.getY() > (nums.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas().getHeight())
        	offset.setY((nums.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas().getHeight()); 
        
	}
	
	private void checkBorders(){
		if(position.getX() * getParent().getZoom() < 0)
			position.setX(0);
        
        if(position.getY() * getParent().getZoom() < 0)
        	position.setY(0);
        
        while(getParent() == null || getParent().getLevel() == null || getParent().getLevel().getMap() == null)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        
        GVector2f nums = getParent().getLevel().getMap().getNumberOfBlocks();
        
        if(position.getX() * getParent().getZoom() + Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom() > nums.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom())
        	position.setX((nums.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom() - Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) / getParent().getZoom());
        
        if(position.getY() * getParent().getZoom() + Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom() > nums.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom())
        	position.setY((nums.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom() - Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom())  / getParent().getZoom());
	}
	
	public void clearTotalMove(){
		totalMove = new GVector2f(); 
	}
	
	public void renderSelector(Graphics2D g2){
		selector.render(g2);
	}

	public boolean showSelector() {
		return showSelector;
	}

	public GVector2f getOffset() {return offset;}
	public GVector2f getBulletDirection() {
		GVector2f sur = position.add(Block.SIZE.mul(getParent().getZoom() / 2));
		return Input.getMousePosition().add(getOffset()).sub(sur).Normalized();
	}
}