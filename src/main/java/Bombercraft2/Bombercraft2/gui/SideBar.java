package Bombercraft2.Bombercraft2.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map.Entry;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import Bombercraft2.Bombercraft2.gui.components.MiniButton;
import Bombercraft2.Bombercraft2.gui.components.MiniSwitch;
import utils.math.GVector2f;

public class SideBar extends Bar{
	private int offset = Config.SIDEBAR_OFFSET;
	private HashMap<String, GuiComponent> buttons = new HashMap<String, GuiComponent>();

	public SideBar(GameAble parent) {
		super(parent, new GVector2f(200, 0));
		calcPosition();
		buttons.put("newGame" ,		new MiniButton(this, "New game"));
		buttons.put("resetGame", 	new MiniButton(this, "Reset game"));
		buttons.put("showLogs", 	new MiniSwitch(this, "Show logs", 		parent.getVisibleOption("renderLogs")));
		buttons.put("showMinimap", 	new MiniSwitch(this, "Show minimap", 	parent.getVisibleOption("renderMiniMap")));
		buttons.put("showLights", 	new MiniSwitch(this, "Show shadows", 	parent.getVisibleOption("renderLights")));
		buttons.put("showLightMap", new MiniSwitch(this, "Show light map", 	parent.getVisibleOption("renderOnlyLightMap")));
		buttons.put("showWalls", 	new MiniSwitch(this, "Show walls", 		parent.getVisibleOption("renderMapWalls")));
		
		init();
		
	}
	
	private void init(){
		setBackgroundColor(Config.SIDEBAR_BACKGROUND_COLOR);
		setBorderColor(Config.SIDEBAR_BORDER_COLOR);
		setVisible(false);
		setBorderWidth(Config.SIDEBAR_BORDER_WIDTH);
	}
	
	@Override
	public void calcPosition() {
		final int localOffset = 30;
		totalPos = new GVector2f(getParent().getCanvas().getWidth() - size.getX() + offset, offset);
		totalPos.addToX(-localOffset);
		totalSize = new GVector2f(size).sub(offset * 2);
		totalSize.addToX(localOffset);
		
		if(size.getY() == 0){
			totalSize.setY(getParent().getCanvas().getHeight() - offset * 2);
		}
		
		if(size.getX() == 0){
			totalSize.setX(getParent().getCanvas().getWidth() - offset * 2);
		}
		
		buttons.entrySet().stream().forEach(a -> a.getValue().calcPosition());
	}
	
	@Override
	public void update(float delta) {
		buttons.entrySet().stream().forEach(a -> a.getValue().update(delta));
	}
	
	@Override
	public void render(Graphics2D g2) {
		
		g2.setColor(getBackgroundColor());
		g2.fillRoundRect(totalPos.getXi(), 
						 totalPos.getYi(), 
						 totalSize.getXi(), 
						 totalSize.getYi(), 
						 Config.DEFAULT_ROUND, 
						 Config.DEFAULT_ROUND);
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		g2.drawRoundRect(totalPos.getXi(), 
						 totalPos.getYi(), 
				 		 totalSize.getXi(), 
				 		 totalSize.getYi(), 
				 		 Config.DEFAULT_ROUND, 
				 		 Config.DEFAULT_ROUND);
		
		
		buttons.entrySet().stream().forEach(a -> a.getValue().render(g2));
	}

	@Override
	public GVector2f getPosition() {
		return totalPos;
	}

	@Override
	public GVector2f getSize() {
		return totalSize;
	}

	@Override
	public void doAct(GVector2f click) {
		if(!isVisible())
			return;
		if(buttons.get("newGame").isClickIn(click)){
			getParent().newGame();
		}
		else if(buttons.get("resetGame").isClickIn(click)){
			getParent().resetGame();
		}
		else if(buttons.get("showLogs").isClickIn(click)){
			getParent().switchVisibleOption("renderLogs");
		}
		else if(buttons.get("showMinimap").isClickIn(click)){
			getParent().switchVisibleOption("renderMiniMap");
		}
		else if(buttons.get("showLights").isClickIn(click)){
			getParent().switchVisibleOption("renderLights");
		}
		else if(buttons.get("showLightMap").isClickIn(click)){
			getParent().switchVisibleOption("renderOnlyLightMap");
		}
		else if(buttons.get("showWalls").isClickIn(click)){
			getParent().switchVisibleOption("renderMapWalls");
		}
		
		
	}
	
	
	
}
