package Bombercraft2.Bombercraft2.gui.submenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import Bombercraft2.Bombercraft2.game.Iconable;

public class SubmenuItem implements Iconable{
	private final static int	SUBMENU_LINE_HEIGHT 			= 30;
	private final static Color	SUBMENU_FONT_COLOR				= Color.WHITE;
	private final static int	SUBMENU_ICON_OFFSET				= 3;
	private final static Color	SUBMENU_ICON_BORDER_COLOR		= Color.BLACK;
	private final static int	SUBMENU_ICON_BORDER_WIDTH		= 1;
	
	public interface GAction{
		public boolean fire();
	}
	public enum Types{
		CHECHKBOX, TEXT, PARENT, ICONABLE, RADIO
	}
	private List<SubmenuItem> 	items	= new ArrayList<SubmenuItem>();
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
		this.type 	= Types.CHECHKBOX;
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
	public boolean fire(){
		if(type == Types.RADIO){
			group.setActive(this);
		}
		value = !value;
		if(action != null){
			return action.fire();
		}
		return false;	
	}

	public void renderIcon(Graphics2D g2, int beginTextX, int beginTextY){
		int maxIconHeight = SUBMENU_LINE_HEIGHT - (SUBMENU_ICON_OFFSET << 1);
		float ratio = (float)image.getWidth(null) / (float)image.getHeight(null);
		g2.drawImage(image, 
					 beginTextX, 
					 beginTextY + SUBMENU_ICON_OFFSET, 
					 (int)(maxIconHeight * ratio), 
					 maxIconHeight, 
					 null);
		
		//TODO bud to nenakresli ram alebo sa obrazok zmensi ak je sirka vacsia ako maximalna vyska
		g2.setColor(SUBMENU_ICON_BORDER_COLOR);
		g2.setStroke(new BasicStroke(SUBMENU_ICON_BORDER_WIDTH));
		g2.drawRect(beginTextX, 
					beginTextY + SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
	}
	

	public void renderCheckbox(Graphics2D g2, int beginTextX, int beginTextY){
		int maxIconHeight = SUBMENU_LINE_HEIGHT - (SUBMENU_ICON_OFFSET << 1);
		g2.setColor(value ? Color.GREEN : Color.RED);
		g2.fillRect(beginTextX, 
					beginTextY + SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
		
		g2.setColor(SUBMENU_ICON_BORDER_COLOR);
		g2.setStroke(new BasicStroke(SUBMENU_ICON_BORDER_WIDTH));
		g2.drawRect(beginTextX, 
					beginTextY + SUBMENU_ICON_OFFSET, 
					maxIconHeight, 
					maxIconHeight);
	}
	
	public void renderRadio(Graphics2D g2, int beginTextX, int beginTextY) {
		int maxIconHeight = SUBMENU_LINE_HEIGHT - (SUBMENU_ICON_OFFSET << 1);
		g2.setColor(SUBMENU_FONT_COLOR);
		
		g2.fillPolygon(new int[]{beginTextX, 
				  				 beginTextX + maxIconHeight, 
				  				 beginTextX}, 
					   new int[]{beginTextY + SUBMENU_ICON_OFFSET, 
							     beginTextY + SUBMENU_ICON_OFFSET + maxIconHeight / 2,
							     beginTextY + SUBMENU_ICON_OFFSET + maxIconHeight}, 3);		
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
