package Bombercraft2.Bombercraft2.gui.submenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.submenu.SubmenuItem.Types;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;



public class Submenu{
	public final static int			SUBMENU_WIDTH 					= 250;
	public final static int			SUBMENU_LINE_HEIGHT 			= 30;
	public final static GVector2f	SUBMENU_DEFAUL_POSITION			= new GVector2f(5, 5);
	public final static Color		SUBMENU_BORDER_COLOR			= Color.WHITE;
	public final static int			SUBMENU_BORDER_WIDTH			= 1;
	public final static Color		SUBMENU_FILL_COLOR				= Color.LIGHT_GRAY;
	public final static Color		SUBMENU_ACTIVE_ITEM_FILL_COLOR	= Color.DARK_GRAY;
	public final static Color		SUBMENU_FONT_COLOR				= Color.WHITE;
	public final static int			SUBMENU_FONT_HORIZONTAL_OFFSET	= 5;
	public final static int			SUBMENU_FONT_VERTICAL_OFFSET	= 5;
	public final static int			SUBMENU_ICON_OFFSET				= 3;
	public final static Color		SUBMENU_ICON_BORDER_COLOR		= Color.BLACK;
	public final static int			SUBMENU_ICON_BORDER_WIDTH		= 1;
	public final static int			SUBMENU_ROUND					= 10;
	public final static int			SUBMENU_LINE_ROUND				= 5;
	
	private GVector2f 			position;
	private boolean				visible			= false;
	private List<SubmenuItem> 	items; 
	private int 				selectedIndex	= 0;
	private Submenu 			children 		= null;
	private Submenu 			parent 			= null;
	private GameAble 			game 			= null;
	
	public Submenu(GameAble game, List<SubmenuItem> items) {
		this(game, items, null);
	}
	public Submenu(GameAble game, List<SubmenuItem> items, Submenu parent) {
		this.parent = parent;
		this.items = items;
		this.game = game;
		
		position = parent == null ? SUBMENU_DEFAUL_POSITION : parent.getChildrenPosition();
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	private Submenu getFirst(){
		Submenu result = this;
		while(result.parent != null){
			result = result.parent;
		}
		return result;
	}

	public void input() {
		if(children != null){
			children.input();
			return;
		}
		if(Input.getKeyDown(Input.KEY_ARROW_DOWN)){
			selectedIndex++;
			if(selectedIndex == items.size()){
				selectedIndex = 0;
			}
		}
		if(Input.getKeyDown(Input.KEY_ARROW_UP)){
			selectedIndex--;
			if(selectedIndex < 0){
				selectedIndex = items.size() - 1;
			}
		}

		if(Input.getKeyDown(Input.KEY_ARROW_RIGHT)){
			SubmenuItem item = items.get(selectedIndex);
			if(!item.isFinal()){
				openChildren(item);
			}
		}
		if(Input.getKeyDown(Input.KEY_ARROW_LEFT)){
			if(parent != null){
				parent.closeChildren();
			}
			else{
				setVisible(false);
			}
		}
		if(Input.getKeyDown(Input.KEY_ENTER)){
			SubmenuItem item = items.get(selectedIndex);
			if(item.isFinal()){
				if(item.fire()){
					setVisible(false);
				}
			}
			else{
				openChildren(item);
			}
		}
	}
	public void render(Graphics2D g2){
		int submenuHeight = SUBMENU_LINE_HEIGHT * items.size();
		g2.setColor(SUBMENU_FILL_COLOR);
		g2.fillRoundRect(position.getXi(), position.getYi(), SUBMENU_WIDTH, submenuHeight, SUBMENU_ROUND, SUBMENU_ROUND);
		
		
		g2.setColor(SUBMENU_FONT_COLOR);
		g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , 20));
		for(int i=0 ; i<items.size() ; i++){
			
			if(selectedIndex == i){
				g2.setColor(SUBMENU_ACTIVE_ITEM_FILL_COLOR);
				g2.fillRoundRect(position.getXi(), 
								 position.getYi() + i * SUBMENU_LINE_HEIGHT, 
								 SUBMENU_WIDTH, 
								 SUBMENU_LINE_HEIGHT, 
								 SUBMENU_LINE_ROUND, 
								 SUBMENU_LINE_ROUND);
			}
			
			SubmenuItem a = items.get(i);
			if(a.isFinal()){
				int beginTextX = position.getXi();
				int beginTextY = position.getYi() + i * SUBMENU_LINE_HEIGHT;
				int imageOffset = 0;
				
				if(a.getType() == Types.ICONABLE){
					beginTextX += SUBMENU_ICON_OFFSET;
					
					a.renderIcon(g2, beginTextX, beginTextY);
//					
					imageOffset += SUBMENU_LINE_HEIGHT;
					beginTextX += SUBMENU_ICON_OFFSET;
				}
				else if(a.getType() == Types.CHECHKBOX){
					beginTextX += SUBMENU_ICON_OFFSET;
					
					a.renderCheckbox(g2, beginTextX, beginTextY);
					imageOffset += SUBMENU_LINE_HEIGHT;
					beginTextX += SUBMENU_ICON_OFFSET;
				}
				else if(a.getType() == Types.RADIO && a.isSelected()){
					beginTextX += SUBMENU_ICON_OFFSET;
					
					a.renderRadio(g2, beginTextX, beginTextY);
					imageOffset += SUBMENU_LINE_HEIGHT;
					beginTextX += SUBMENU_ICON_OFFSET;
				}
				
				g2.setColor(SUBMENU_FONT_COLOR);
				g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , 20));
				g2.drawString(a.getLabel(), 
							  beginTextX + SUBMENU_FONT_HORIZONTAL_OFFSET + imageOffset, 
							  beginTextY + SUBMENU_FONT_VERTICAL_OFFSET + 20);
				
				
			}
			else{
				g2.setColor(SUBMENU_FONT_COLOR);
				g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , 20));
				g2.drawString(a.getLabel(), 
							  position.getXi() + SUBMENU_FONT_HORIZONTAL_OFFSET, 
							  position.getYi() + i * SUBMENU_LINE_HEIGHT + SUBMENU_FONT_VERTICAL_OFFSET + 20);
			}
		}
		
		g2.setStroke(new BasicStroke(SUBMENU_BORDER_WIDTH));
		g2.setColor(SUBMENU_BORDER_COLOR);
		g2.drawRoundRect(position.getXi(), position.getYi(), SUBMENU_WIDTH, submenuHeight, SUBMENU_ROUND, SUBMENU_ROUND);
		
		
		if(children != null){
			children.render(g2);
		}
	}
	
	private GVector2f getChildrenPosition(){
		return new GVector2f(position.getX() + SUBMENU_WIDTH, position.getY() + SUBMENU_LINE_HEIGHT * selectedIndex);
	}
	
	private void openChildren(SubmenuItem item){
		children = new Submenu(game, item.getItems(), this);
	}
	private void closeChildren(){
		children = null;
	}
	
	public boolean isActive(){
		return children == null;
	}
	public void setVisible(boolean value) {
		Submenu first = getFirst();
		first.visible = value;
		if(!first.visible){
			first.closeChildren();
			first.selectedIndex = 0;
		}
	}
}
