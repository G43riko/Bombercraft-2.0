package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Config;
import utils.math.GVector2f;

import java.awt.*;

public class HealthBar {
	private final Healthable target;
	
	public HealthBar(Healthable target){
		this.target = target;
	}
	
	public void render(Graphics2D g2, GVector2f offset) {
		GVector2f pos = target.getPosition().sub(offset);
		g2.setStroke(new BasicStroke(Config.HEALTH_BAR_BORDER_WIDTH));
		
		int maxWidth = target.getSize().getXi() - (Config.HEALTH_BAR_HORIZONTAL_OFFSET << 1);
				
		g2.setColor(Config.HEALTH_BAR_BORDER_COLOR);
		g2.drawRect(pos.getXi() + Config.HEALTH_BAR_HORIZONTAL_OFFSET,
				    pos.getYi() + Config.HEALTH_BAR_VERTICAL_OFFSET,
				    maxWidth, 
				    Config.HEALTH_BAR_HORIZONTAL_HEIGHT);
		
		g2.setColor(Config.HEALTH_BAR_FILL_COLOR);
		g2.fillRect(pos.getXi() + Config.HEALTH_BAR_HORIZONTAL_OFFSET,
				    pos.getYi() + Config.HEALTH_BAR_VERTICAL_OFFSET,
				    target.getActHealth() * maxWidth / target.getMaxHealth(),
				    Config.HEALTH_BAR_HORIZONTAL_HEIGHT);
	}
	
}
