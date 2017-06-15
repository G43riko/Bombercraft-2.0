package Bombercraft2.Bombercraft2.game;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import utils.math.GVector2f;

public class HealtBar{
	private Healtable target;
	
	public HealtBar(Healtable target){
		this.target = target;
	}
	
	public void render(Graphics2D g2, GVector2f offset) {
		GVector2f pos = target.getPosition().sub(offset);
		g2.setStroke(new BasicStroke(Config.HEATLBAR_BORDER_WIDTH));
		
		int maxWidth = target.getSize().getXi() - (Config.HEATLBAR_HORIZONTAL_OFFSET << 1);
				
		g2.setColor(Config.HEATLBAR_BORDER_COLOR);
		g2.drawRect(pos.getXi() + Config.HEATLBAR_HORIZONTAL_OFFSET, 
				    pos.getYi() + Config.HEATLBAR_VERTICAL_OFFSET, 
				    maxWidth, 
				    Config.HEATLBAR_HORIZONTAL_HEIGHT);
		
		g2.setColor(Config.HEATLBAR_FILL_COLOR);
		g2.fillRect(pos.getXi() + Config.HEATLBAR_HORIZONTAL_OFFSET, 
				    pos.getYi() + Config.HEATLBAR_VERTICAL_OFFSET, 
				    target.getActHealt() * maxWidth / target.getMaxHealt(), 
				    Config.HEATLBAR_HORIZONTAL_HEIGHT);
	}
	
}
