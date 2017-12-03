package Bombercraft2.Bombercraft2.gui.submenu;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.Iconable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubmenuItem implements Iconable{
	public interface GAction{
		boolean fire();
	}
	public enum Types{
		CHECKBOX, TEXT, PARENT, ICONABLE, RADIO
	}
	private List<SubmenuItem> 	items	= new ArrayList<>();
	private String 				label;
	private Types 				type	= Types.TEXT;
	private SubmenuRadioGroup	group;
	private Image 				image;
	private boolean				value;
	private GAction				action;
	
	public SubmenuItem(String label){this(label, (List<SubmenuItem>)null);}
	public SubmenuItem(String label, Image image){this(label, image, null);}
	public SubmenuItem(String label, Image image, GAction action){
		this.action = action;
		this.label 	= label;
		this.image 	= image;
		this.type 	= Types.ICONABLE;
	}
	public SubmenuItem(String label, Types type){this(label, type, null);}
	public SubmenuItem(String label, boolean value, GAction action){
		this.action = action;
		this.label 	= label;
		this.value	= value;
		this.type 	= Types.CHECKBOX;
	}
	public SubmenuItem(String label, SubmenuRadioGroup group){this(label, group, null);}
	public SubmenuItem(String label, SubmenuRadioGroup group, GAction action){
		this.action = action == null ? group.getActionFor(this) : action;
		this.label 	= label;
		this.group	= group;
		this.type 	= Types.RADIO;
		group.addItem(label, this);
	}
	
	
	public SubmenuItem(String label, Types type, GAction action){
		this.action = action;
		this.label 	= label;
		this.type 	= type;
	}
	public SubmenuItem(String label, GAction action){
		this.action = action;
		this.label	= label;
	}
	public SubmenuItem(String label, List<SubmenuItem> items){
		this.label = label;
		if(items != null){
			this.items.addAll(items);
			type = Types.PARENT;
		}
	}
	public boolean fire() {
		if (type == Types.RADIO) {
			group.setActive(this);
		}
		value = !value;
		return action != null && action.fire();
	}

	public void renderIcon(Graphics2D g2, int beginTextX, int beginTextY){
		int maxIconHeight = Config.SUBMENU_LINE_HEIGHT - (Config.SUBMENU_ICON_OFFSET << 1);
		float ratio = (float)image.getWidth(null) / (float)image.getHeight(null);
		g2.drawImage(image, 
					 beginTextX, 
					 beginTextY + Config.SUBMENU_ICON_OFFSET, 
					 (int)(maxIconHeight * ratio), 
					 maxIconHeight, 
					 null);
		
		//TODO bud to nenakresli ram alebo sa obrazok zmensi ak je sirka vacsia ako maximalna vyska
		g2.setColor(Config.SUBMENU_ICON_BORDER_COLOR);
		g2.setStroke(new BasicStroke(Config.SUBMENU_ICON_BORDER_WIDTH));
		g2.drawRect(beginTextX, 
					beginTextY + Config.SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
	}
	

	public void renderCheckbox(Graphics2D g2, int beginTextX, int beginTextY){
		int maxIconHeight = Config.SUBMENU_LINE_HEIGHT - (Config.SUBMENU_ICON_OFFSET << 1);
		g2.setColor(value ? Color.GREEN : Color.RED);
		g2.fillRect(beginTextX, 
					beginTextY + Config.SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
		
		g2.setColor(Config.SUBMENU_ICON_BORDER_COLOR);
		g2.setStroke(new BasicStroke(Config.SUBMENU_ICON_BORDER_WIDTH));
		g2.drawRect(beginTextX, 
					beginTextY + Config.SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
	}
	
	public void renderRadio(Graphics2D g2, int beginTextX, int beginTextY) {
		int maxIconHeight = Config.SUBMENU_LINE_HEIGHT - (Config.SUBMENU_ICON_OFFSET << 1);
		g2.setColor(Config.SUBMENU_FONT_COLOR);
		
		g2.fillPolygon(new int[]{beginTextX, 
				  				 beginTextX + maxIconHeight, 
				  				 beginTextX}, 
					   new int[]{beginTextY + Config.SUBMENU_ICON_OFFSET, 
							     beginTextY + Config.SUBMENU_ICON_OFFSET + maxIconHeight / 2,
							     beginTextY + Config.SUBMENU_ICON_OFFSET + maxIconHeight}, 3);		
	}


	@Override
	public Image 				getImage() 	{return image;}
	public boolean 				getValue()	{return value;}
	public List<SubmenuItem> 	getItems()	{return items;}
	public String 				getLabel()	{return label;}
	public Types 				getType()	{return type;}
	public boolean 				isFinal()	{return items.isEmpty();}
	public boolean 				isSelected(){return type == Types.RADIO && group.getSelectedItem() == this;}


}
