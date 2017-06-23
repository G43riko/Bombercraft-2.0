package Bombercraft2.Bombercraft2.game;

import java.awt.Graphics2D;
import java.util.HashMap;

import Bombercraft2.Bombercraft2.game.entity.BombCreator;
import Bombercraft2.Bombercraft2.game.entity.BombCreator;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.weapons.Weapon;
import Bombercraft2.Bombercraft2.game.entity.weapons.WeaponLaser;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.player.placers.AreaPlacer;
import Bombercraft2.Bombercraft2.game.player.placers.Placer;
import Bombercraft2.Bombercraft2.game.player.placers.SimplePlacer;
import Bombercraft2.Bombercraft2.game.player.placers.Placer.Types;
import Bombercraft2.Bombercraft2.game.player.Showel;
import Bombercraft2.Bombercraft2.game.player.Toolable;

public class ToolManager {
	private HashMap<Iconable, Toolable> tools = new HashMap<Iconable, Toolable>() ;
	private Placer 						actualPlacer;
	private Toolable 					selectedTool;
	private GameAble 					parent;
	
	public ToolManager(GameAble parent){
		this.parent = parent;
		init();
	}
	
	private void init(){
		setActualPlacer(Types.SIMPLE);
		tools.put(Helper.Type.SHOVEL, new Showel(parent));
		tools.put(Helper.Type.WEAPON_LASER, new WeaponLaser(parent, parent.getWeapon("laser")));
		tools.put(Helper.Type.BOMB_NORMAL, new BombCreator(parent, Helper.Type.BOMB_NORMAL));
	}

	public void render(Graphics2D g2){
		actualPlacer.render(g2);
	}
	
	//GETTES
	
	public Toolable getTool(Iconable icon){return tools.get(icon);}
	public Toolable getSelectedTool(){return selectedTool;}
	private Toolable getItemByIconable(Iconable selectedIcon){
		if(selectedIcon.getClass().isAssignableFrom(Block.Type.class)){
			actualPlacer.setBlockType((Block.Type)selectedIcon);
			return actualPlacer;
		}
		return tools.get(selectedIcon);
	}
	
	//SETTERS
	
	public void setSelectedTool(Iconable icon){selectedTool = getItemByIconable(icon);}
	public void setActualPlacer(Placer.Types type){
		Placer newPlacer = null;
		switch(type){
			case SIMPLE:
				newPlacer = new SimplePlacer(parent);
				break;
			case AREA:
				newPlacer = new AreaPlacer(parent);
				break;
		}
		if(actualPlacer != null){
			newPlacer.setBlockType(actualPlacer.getType());
		}
		actualPlacer = newPlacer;
		if(selectedTool instanceof Placer){
			selectedTool = actualPlacer;
		}
	}
}
