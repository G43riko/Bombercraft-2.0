package Bombercraft2.Bombercraft2.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import utils.ImageUtils;
import utils.SpriteViewer;
import utils.math.GVector2f;
import utils.math.LineLineIntersect;
import utils.resouces.ResourceLoader;

public class Block extends Entity{
	public enum Type implements Iconable{
		NOTHING	("block_floor",  0, true),
		WOOD	("block_wood",   1, false),
		IRON	("block_iron",  10, false),
		GRASS	("block_grass",  0, true),
		WATER	("block_water",  0, true),
//		PATH	("block_path",   0, true),
//		STONE	("block_stone",  7, false),
		FUTURE	("block_future", 0, true);
		
		private Image 	image;
		private Color	minimapColor;
		private int 	healt;
		private boolean walkable;
		Type(String imageName, int healt, boolean walkable){
			this.healt = healt;
			this.walkable = walkable;
			image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
			final BufferedImage bimage = new BufferedImage(image.getWidth(null), 
													       image.getHeight(null), 
													       BufferedImage.TYPE_INT_ARGB);
			
			final Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(image, 0, 0, null);
		    bGr.dispose();
		    
		    minimapColor = ImageUtils.getAverageColor(bimage, 0, 0, image.getWidth(null), image.getHeight(null));
		}
		
		public boolean isWalkable(){return walkable;}
		public int getHealt(){return healt;}
		public Image getImage(){return image;}
		public Color getMinimapColor(){return minimapColor;}
	}
	public final static GVector2f SIZE  = new GVector2f(Config.DEFAULT_BLOCK_WIDTH, 
														Config.DEFAULT_BLOCK_HEIGHT);

//	private static HashMap<String, Type> blocks = new HashMap<String, Type>();
	private Type 	type;	
	private int 	healt;
	
	//CONTRUCTORS
	
	public Block(JSONObject object, GameAble parent){
		super(new GVector2f(), parent);
		
		try {
			position 	= new GVector2f(object.getString(Texts.BLOCK_POSITION));
			healt 		= object.getInt(Texts.BLOCK_HEALT);
			type 		= Type.valueOf(object.getString(Texts.BLOCK_TYPE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Block(GVector2f position, int type, GameAble parent){
		this(position, getTypeFromInt(type), parent, getTypeFromInt(type).getHealt());
	}
	
	public Block(GVector2f position, Type type, GameAble parent, int healt) {
		super(position, parent);
		this.healt 	= healt;
		this.type 	= type;
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
//		if(type == NOTHING)
//			return;
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());

		g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
	}

	@Override
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		
		try {
			result.put(Texts.BLOCK_HEALT, healt);
			result.put(Texts.BLOCK_TYPE, type);
			result.put(Texts.BLOCK_POSITION, position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//OTHERS
	
	public boolean hit(int demage){
		healt -= demage;
		boolean res = healt <= 0;
		if(res){
			remove();
		}
		return res;
	}
	public void remove(){remove(false);}
	public void remove(boolean addExplosion) {
		if(addExplosion){
			getParent().addExplosion(getPosition().add(Block.SIZE.div(2)), 
									 Block.SIZE, 
									 type.getMinimapColor(), 
									 5);
		}
		type = Type.NOTHING; 
		healt = 0;
	}

	public void drawSprites(Graphics2D g2){
		Block t = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() - 1);
		Block b = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() + 1);
		Block r = getParent().getLevel().getMap().getBlock(position.getXi() + 1, position.getYi());
		Block l = getParent().getLevel().getMap().getBlock(position.getXi() - 1, position.getYi());
		
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
		if(t != null && t.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 8, 4), pos.getXi(), pos.getYi() - Block.SIZE.getYi(), size.getXi(), size.getYi(), null);
		
		if(b != null && b.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 2, 4), pos.getXi(), pos.getYi() + Block.SIZE.getYi(), size.getXi(), size.getYi(), null);
		
		if(r != null && r.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 1, 4), pos.getXi() + Block.SIZE.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
		
		if(l != null && l.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 4, 4), pos.getXi() - Block.SIZE.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
	}
	
	public void drawShadow(Graphics2D g2, Color color, int length, int angle) {
		if(type == Type.NOTHING)
			return;
		
		double uhol = Math.toRadians(angle + 90); 
		GVector2f offset = new GVector2f(-Math.cos(uhol), Math.sin(uhol)).mul(length);
		GVector2f pos = position.mul(Block.SIZE).sub(getParent().getOffset());
		g2.setColor(color);
		
		/*   2---3
		 *  /    |
		 * 1     4
		 *      /
		 *     5
		 * 		
		 */
		int[] xPos = new int[]{
			(int)(pos.getX()),
			(int)(pos.add(offset).getX()),
			(int)(pos.add(offset).getX() + Block.SIZE.getX()),
			(int)(pos.add(offset).getX() + Block.SIZE.getX()),
			(int)(pos.getXi() + Block.SIZE.getX())
		};
		
		int[] yPos = new int[]{
				(int)(pos.getY()),
				(int)(pos.sub(offset).getY()),
				(int)(pos.sub(offset).getY()),
				(int)(pos.getY() + Block.SIZE.getY() - offset.getY()),
				(int)(pos.getY() + Block.SIZE.getY())
			};
		
		g2.fillPolygon(xPos, yPos, 5);
	}

	public void build(Type type) {
		this.type = type;
		this.healt = type.getHealt();
	}

	public void renderWalls(Graphics2D g2) {
		Map map = getParent().getLevel().getMap();
		boolean t = map.isWalkable(position.getXi(), position.getYi() - 1);
		boolean b = map.isWalkable(position.getXi(), position.getYi() + 1);
		boolean l = map.isWalkable(position.getXi() - 1, position.getYi());
		boolean r = map.isWalkable(position.getXi() + 1, position.getYi());
		
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
		int offset = (int)(Config.WALL_OFFSET * getParent().getZoom());
		
		GVector2f p0 = pos.sub(offset);
		GVector2f p1 = pos.add(size).sub(new GVector2f(-offset, offset + size.getY()));
		GVector2f p2 = pos.add(size).add(offset);
		GVector2f p3 = pos.add(size).sub(new GVector2f(offset + size.getX(), -offset));
			
		Block block0 = map.getBlock(position.getXi() - 1, position.getYi() - 1);
		Block block1 = map.getBlock(position.getXi() + 1, position.getYi() - 1);
		Block block2 = map.getBlock(position.getXi() + 1, position.getYi() + 1);
		Block block3 = map.getBlock(position.getXi() - 1, position.getYi() + 1);
		boolean val0 = block0 != null && !block0.isWalkable();
		boolean val1 = block1 != null && !block1.isWalkable();
		boolean val2 = block2 != null && !block2.isWalkable();
		boolean val3 = block3 != null && !block3.isWalkable();
		
		
		boolean walls3D = true;
		if(walls3D){
			g2.setColor(Config.WALL_DARKER_COLOR);
			if(t){
				g2.fillPolygon(new int[]{pos.getXi(), 
										 pos.getXi() + size.getXi(), 
										 p1.getXi() + (val1 ? - offset : 0), 
										 p0.getXi() + (true ? + offset * 2 : 0)},
					   	   	   new int[]{pos.getYi(), pos.getYi(), p1.getYi(),  p0.getYi()}, 
					   	   	   4);
			}
			g2.setColor(Config.WALL_LIGHTER_COLOR);
			if(r){
				g2.fillPolygon(new int[]{pos.getXi() + size.getXi(), pos.getXi() + size.getXi(), p2.getXi(), p1.getXi()},
				   	   	   	   new int[]{pos.getYi(), 
				   	   	   			   	 pos.getYi() + size.getYi(), 
				   	   	   			   	 p2.getYi() + (true ? - offset * 2 : 0),  
				   	   	   			   	 p1.getYi() + (val1 ? + offset : 0)},
				   	   	   	   4);
			}
			
		}
		else{
			int i = offset * 2;
			g2.setColor(Config.WALL_LIGHTER_COLOR);
			if(t){
				g2.fillPolygon(new int[]{pos.getXi(), 
										 pos.getXi() + size.getXi(), 
										 p1.getXi() + (val1 ? - i : 0), 
										 p0.getXi() + (val0 ? + i : 0)},
					   	   	   new int[]{pos.getYi(), pos.getYi(), p1.getYi(),  p0.getYi()}, 
					   	   	   4);
			}
			if(r){
				g2.fillPolygon(new int[]{pos.getXi() + size.getXi(), pos.getXi() + size.getXi(), p2.getXi(), p1.getXi()},
				   	   	   	   new int[]{pos.getYi(), 
				   	   	   			   	 pos.getYi() + size.getYi(), 
				   	   	   			   	 p2.getYi() + (val2 ? - i : 0),  
				   	   	   			   	 p1.getYi() + (val1 ? + i : 0)},
				   	   	   	   4);
			}
			
			g2.setColor(Config.WALL_DARKER_COLOR);
			if(b){
				g2.fillPolygon(new int[]{pos.getXi(), 
										 p3.getXi() + (val3 ? + i : 0), 
										 p2.getXi() + (val2 ? - i : 0), 
										 pos.getXi() + size.getXi()},
							   new int[]{pos.getYi() + size.getYi(), p3.getYi(), p2.getYi(), pos.getYi() + size.getYi()},
				   	   	   	   4);
			}
			if(l){
				g2.fillPolygon(new int[]{pos.getXi(), p0.getXi(), p3.getXi(), pos.getXi()},
							   new int[]{pos.getYi(), 
									   	 p0.getYi() + (val0 ? + i : 0), 
									   	 p3.getYi() + (val3 ? - i : 0), 
									   	 pos.getYi() + size.getYi()},
				   	   	   		4);
			}
		}
	}
	

	//GETTERS
	
	public GVector2f 	getSur() 		{return position/*.div(SIZE).toInt()*/;}
	public GVector2f 	getPosition() 	{return position.mul(SIZE);}
	public GVector2f 	getSize() 		{return SIZE;}
	public Type 		getType() 		{return type;}
	public int 			getHealt() 		{return healt;}
	public boolean 		isWalkable()	{return type.isWalkable();}

	public static Type getTypeFromInt(int num){
		return num > 0 && num < Type.values().length ?  Type.values()[num] : Type.NOTHING;
	}
	public GVector2f getInterSect(GVector2f ss, GVector2f se){
		ArrayList<GVector2f> res = new ArrayList<GVector2f>();
		
		GVector2f p = position.mul(Block.SIZE);
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(Block.SIZE.getX(), 0)), p));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(0, Block.SIZE.getY())), p));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(Block.SIZE.getX(), 0)), p.add(Block.SIZE)));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(0, Block.SIZE.getY())), p.add(Block.SIZE)));
		
		res = res.stream().filter(a -> a != null).collect(Collectors.toCollection(ArrayList::new));
		if(res.size() == 0){
			return null;
		}

		return res.stream().reduce((a, b) -> a.dist(ss) < b.dist(ss) ? a : b).get();
	}
}
	
	
