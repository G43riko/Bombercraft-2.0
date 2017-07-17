package Bombercraft2.Bombercraft2.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import utils.PerlinNoise;
import utils.SimplexNoise;
import utils.math.GVector2f;

public class Map implements Interactable{
	private HashMap<String, Block> 	blocks;
	private GVector2f 				numberOfBlocks;
	private GameAble 				parent;
	private boolean 				render 		= true;
	private long 					renderedBlocks;
	private String 					defaultMap;	//zaloha mapy pre reset
	private BufferedImage			image;
	private final static boolean	PRERENDER 	= false;
	private GVector2f 				size;
	
	//CONSTRUCTORS
	
	public Map(JSONObject object, GameAble parent){
		this.parent = parent;
		try {
			this.numberOfBlocks = new GVector2f(object.getString(Texts.BLOCKS_NUMBER));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		loadMap(object);
		size = numberOfBlocks.mul(Block.SIZE);
	};
	
	public Map(GameAble parent){
		this(parent, new GVector2f(40, 40)); //300 x 300 - je max
	}
	
	public Map(GameAble parent, GVector2f numberOfBlocks){
		this.parent = parent;
		this.numberOfBlocks = numberOfBlocks;
		
		createRandomMap();
		defaultMap = toJSON().toString();
		size = numberOfBlocks.mul(Block.SIZE);
	}
	
	//OVERRIDES

	@Override
	public void render(Graphics2D g2) {
		if(!render){
			return;
		}
		if(!PRERENDER){
			renderToImage(g2);
			return;
		}
		if(image == null){
			image = new BufferedImage(numberOfBlocks.getXi() * Config.DEFAULT_BLOCK_WIDTH, 
									  numberOfBlocks.getYi() * Config.DEFAULT_BLOCK_HEIGHT, 
									  BufferedImage.TYPE_INT_ARGB);
			
			renderToImage((Graphics2D) image.getGraphics());
		}
		
		//g2.drawImage(image, -parent.getOffset().getXi(), -parent.getOffset().getYi(), null);
		
		g2.drawImage(image, 
					 0, 
					 0, 
					 parent.getCanvas().getWidth(), 
					 parent.getCanvas().getHeight(), 
					 parent.getOffset().getXi(), 
					 parent.getOffset().getYi(), 
					 parent.getOffset().getXi() + parent.getCanvas().getWidth(), 
					 parent.getOffset().getYi() + parent.getCanvas().getHeight(), 
					null);
					
		
	}
	public void renderToImage(Graphics2D g2) {
		if(!render){
			return;
		}
		renderedBlocks = new HashMap<String, Block>(blocks).entrySet()
		      							  				   .stream()
		      							  				   .map(a -> a.getValue())
		      							  				   .filter(getParent()::isVisible)//dame prec nevyditelne
		      							  				   .peek(a -> a.render(g2))
		      							  				   .filter(a -> a.getType() != Block.Type.NOTHING)
		      							  				   .count();
		if(parent.getVisibleOption(Render.MAP_WALLS)){
			new HashMap<String, Block>(blocks).entrySet()
			   								  .stream()
			   								  .map(a -> a.getValue())
			   								  .filter(getParent()::isVisible)//dame prec nevyditelne
			   								  .filter(a -> !a.isWalkable())	//dame prec take ktore nevrhaju tien
			   								  .forEach(a -> a.renderWalls(g2));
		}
		
		
	}
	
	private void postEdit(){
		//TODO odstranit osamele policka z vodou(vacsinu)

		clearRespawnZones(parent.getLevel().getRespawnZones());
	}
	
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		try {
			for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
				for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
					result.put("b" + i + "_" + j, getBlock(i, j).toJSON());
				}
			}
			result.put(Texts.BLOCKS_NUMBER, numberOfBlocks);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//OTHERS

	public void createRandomMap(){
		render = false;
		blocks = new HashMap<String, Block>();
		float[][] data = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(numberOfBlocks.getXi(), 
																						numberOfBlocks.getXi()), 
																						6, 
																						0.7f, 
																						true);
		
		for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
			for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
//				addBlock(i, j, new Block(new GVector2f(i,j),(int)(Math.random() * 10), parent));
				addBlock(i, j, new Block(new GVector2f(i,j),
										 (int)(Math.min(Math.max(data[i][j] * 10, 0), 10)), 
										 parent));
			}
		}
		postEdit();
		render = true;
	}
	
	public void loadMap(JSONObject object){
		render = false;
		blocks = new HashMap<String, Block>();
		try {
			for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
				for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
					addBlock(i, j, new Block(new JSONObject(object.getString("b" + i + "_" + j)), parent));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		render = true;
	}
	
	/**
	 * Odstrani bloky z respawnovacich zon
	 * @param zones
	 */
	private void clearRespawnZones(List<GVector2f> zones){
		zones.stream().forEach(a -> {
			remove(a.div(Block.SIZE).toInt());
		});
	}
	
	public void remove(GVector2f sur){
		Block b = getBlock(sur.getXi(), sur.getYi());
		if(b != null && b.getType() != Block.Type.NOTHING){
			b.remove();
		}
	}
	
	public void resetMap() {
		try {
			loadMap(new JSONObject(defaultMap));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//ADDERS

	private void addBlock(int i, int j, Block block){
		blocks.put(i + "_" + j, block);
	}
	
	//GETTERS
	
	public long getRenderedBlocks() {return renderedBlocks;}
	public GVector2f getNumberOfBlocks() {return numberOfBlocks;}
	public Block getBlock(String block){return blocks.get(block);}	
	public Block getBlock(int i, int j){return blocks.get(i + "_" + j);}
	public GameAble getParent() {return parent;}
	
	public boolean isWalkable(int i, int j){
		Block b = getBlock(i, j);
		return b != null && b.isWalkable();
	}

	public Direction[] getPossibleWays(GVector2f sur){
		ArrayList<Direction> result = new ArrayList<Direction>();
		Block b;
		
		b = getBlock(sur.getXi(), sur.getYi() - 1);
		if(b != null && b.isWalkable()){
			result.add(Direction.UP);
		}
		
		b = getBlock(sur.getXi() + 1, sur.getYi());
		if(b != null && b.isWalkable()){
			result.add(Direction.RIGHT);
		}
		
		b = getBlock(sur.getXi(), sur.getYi() + 1);
		if(b != null && b.isWalkable()){
			result.add(Direction.DOWN);
		}
		
		b = getBlock(sur.getXi() - 1, sur.getYi());
		if(b != null && b.isWalkable()){
			result.add(Direction.LEFT);
		}
		
		Direction[] ret = new Direction[result.size()];
		for(int i=0 ; i<result.size() ; i++){
			ret[i] = result.get(i);
		}
		
		return ret;
	}
	
	/**
	 * @deprecated since 15.6.2017 - use Map.getRandomBlockByType(Block.Type.NOTHING)
	 * @return
	 */
	public Block getRandomEmptyBlock(){
		return getRandomBlockByType(Block.Type.NOTHING);
	}
	
	public Block getRandomBlockByType(Block.Type type){
		ArrayList<Block> b = blocks.entrySet()
								   .stream()
								   .map(a -> a.getValue())
								   .filter(a -> a.getType() == type)
								   .collect(Collectors.toCollection(ArrayList<Block>::new));
		return b.get((int)(Math.random() * b.size()));
	}
	public static GVector2f globalPosToLocalPos(GVector2f pos){
		return pos.sub(pos.mod(Block.SIZE)).div(Block.SIZE);
	}
	public static GVector2f localPosToGlobalPos(GVector2f pos){
		return pos.mul(Block.SIZE);
	}
	public Block getBlockOnPosition(GVector2f sur){
		GVector2f blockSize = Block.SIZE;
		GVector2f pos = sur.sub(sur.mod(blockSize)).div(blockSize);
		
		return getBlock(pos.getXi(), pos.getYi());
	}

	/**
	 * @deprecated since 15.6.2017 - vsetky operacie z blokmi by maly byt vykonane v tejto triede
	 * @return
	 */
	public ArrayList<Block> getBlocks(){
		if(render){
			return new ArrayList<Block>(blocks.values());
		}
		
		return new ArrayList<Block>();
	}
	
	public GVector2f getSize(){
		return size;
	}

}
