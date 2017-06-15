package Bombercraft2.Bombercraft2.game.entity.flora;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import utils.math.GVector2f;


public class FloraManager {
	private List<Bush> 		bushes 	= new ArrayList<Bush>();
	private List<Plant> 	plants 	= new ArrayList<Plant>();
	private List<Tree> 		trees 	= new ArrayList<Tree>();
	private final GameAble 	parent;
	private final Map		map;
	
	public FloraManager(GameAble parent, Map map){
		this.parent = parent;
		this.map = map;
		createFlora();
	}
	
	public void addFlora(Flora flora){
		switch(flora.getType().getType()){
			case BUSH:
				bushes.add((Bush)flora);
				break;
			case PLANT:
				plants.add((Plant)flora);
				break;
			case TREE:
				trees.add((Tree)flora);
				break;
		}
	}
	
	public void renderUpperLevel(Graphics2D g2){
		trees.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
	}
	public void renderLowerLevel(Graphics2D g2){
		plants.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
		bushes.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
	}
	
	private void creteBushByType(Flora.Bushes type, GVector2f maxSize){
		GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
		if(map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS){
			addFlora(new Bush(type, sur, parent));
		}
	}
	private void creteTreeByType(Flora.Trees type, GVector2f maxSize){
		GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
		if(map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS){
			addFlora(new Tree(type, sur, parent));
		}
	}
	private void cretePlantByType(Flora.Plants type, GVector2f maxSize){
		GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
		if(map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS){
			addFlora(new Plant(type, sur, parent));
		}
	}
	private void createFlora() {
		GVector2f nums = map.getSize().sub(Block.SIZE);
		for(int i=0 ; i<33 ; i++){
			for(int j=0 ; j<Flora.Bushes.values().length ; j++){
				creteBushByType(Flora.Bushes.values()[j], nums);	
			}
			for(int j=0 ; j<Flora.Trees.values().length ; j++){
				creteTreeByType(Flora.Trees.values()[j], nums);	
			}
			for(int j=0 ; j<Flora.Plants.values().length ; j++){
				cretePlantByType(Flora.Plants.values()[j], nums);	
			}
		}
	}
}
