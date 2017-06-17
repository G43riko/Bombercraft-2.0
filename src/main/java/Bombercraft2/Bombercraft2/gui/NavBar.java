package Bombercraft2.Bombercraft2.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashMap;

import org.junit.runners.ParentRunner;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.ToolManager;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.player.Showel;
import Bombercraft2.engine.Input;
import utils.math.GColision;
import utils.math.GVector2f;

public class NavBar extends Bar{
	private HashMap<Integer, Iconable> items = new HashMap<Integer, Iconable>(); 
	private int selectedItem = 0;
	
	public NavBar(GameAble parent) {
		super(parent, Config.NAVBAR_SIZE);

		items.put(0, Helper.Type.SHOVEL);
		items.put(1, Helper.Type.TOWER_LASER);
		items.put(2, Helper.Type.TOWER_MACHINE_GUN);
		items.put(3, Helper.Type.WEAPON_LASER);
		items.put(4, Helper.Type.WEAPON_BOW);
		items.put(5, Helper.Type.BOMB_NORMAL);
		items.put(6, Helper.Type.WEAPON_LIGHTNING);
		items.put(7, Helper.Type.WEAPON_STICK);
		items.put(8, Helper.Type.WEAPON_BOOMERANG);
		
		items.put(9, Block.Type.IRON);
		items.put(10, Block.Type.WOOD);
		
		items.put(11, Helper.Type.OTHER_RESPAWNER);
		items.put(12, Helper.Type.OTHER_ADDUCTOR);
		
		calcPosition();
		init();
	}
	private void init(){
		setBackgroundColor(Config.NAVBAR_BACKGROUND_COLOR);
		setBorderColor(Config.NAVBAR_BORDER_COLOR);
		setBorderWidth(Config.NAVBAR_BORDER_WIDTH);
		
		getParent().getToolsManager().setSelectedTool(getSelectedIcon());
	}
	public Iconable getSelectedIcon(){
		return items.get(selectedItem);
	}
	public void removeItem(int index){
		items.remove(index);
	}
	
	public void moveItem(int from, int to){
		items.put(to, items.remove(from));
	}
	
	public int getSelecedIndex(){
		return selectedItem;
	}
	
	public void calcPosition(){
		totalSize = size.mul(new GVector2f(Config.NAVBAR_NUMBER_OF_BLOCKS, 1));
		totalPos = new GVector2f((getParent().getCanvas().getWidth() - totalSize.getX()) / 2, 
								  getParent().getCanvas().getHeight() - Config.NAVBAR_BOTTOM_OFFSET - totalSize.getY());
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(getBackgroundColor());
		g2.fillRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi());
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		
		for(int i=0 ; i<Config.NAVBAR_NUMBER_OF_BLOCKS ; i++){
			if(items.containsKey(i)){
				g2.drawImage(items.get(i).getImage(), 
							 totalPos.getXi() + size.getXi() * i,  
							 totalPos.getYi(), 
							 size.getXi(), 
							 size.getYi(), 
							 null);
			}
			g2.drawRect(totalPos.getXi() + size.getXi() * i,  totalPos.getYi(), size.getXi(), size.getYi());
		}
		
		g2.setColor(Config.NAVBAR_SELECT_BORDER_COLOR);
		g2.drawRect(totalPos.getXi() + size.getXi() * selectedItem,  totalPos.getYi(), size.getXi(), size.getYi());
	}

	@Override
	public void doAct(GVector2f click) {
		if(GColision.pointRectCollision(totalPos, totalSize, Input.getMousePosition())){
			selectedItem = Input.getMousePosition().sub(totalPos).div(size).getXi();
			getParent().getToolsManager().setSelectedTool(getSelectedIcon());
		}
	}

	@Override
	public GVector2f getPosition() {
		return totalPos;
	}

	@Override
	public GVector2f getSize() {
		return size;
	}
}
