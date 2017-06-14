package Bombercraft2.Bombercraft2.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.game.GameAble;


public class GameLogs implements Interactable{
	private GameAble parent;
	
	public GameLogs(GameAble parent){
		this.parent = parent;
	}
	
	@Override
	public void render(Graphics2D g2) {
		ArrayList<String> data = parent.getLogInfos();

		g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , Config.LOG_TEXT_SIZE));
		g2.setColor(Config.LOG_TEXT_COLOR);
		
		int maxWidth = 0;
		for(int i=0 ; i< data.size() ; i++){
			maxWidth = Math.max(g2.getFontMetrics().stringWidth(data.get(i)), maxWidth);
		}
		final int offset = 10; 
		
		g2.setColor(Config.LOG_BG_COLOR);
		
		g2.fillRoundRect(0, 
						 0,  
						 maxWidth + offset, 
						 Config.LOG_TEXT_SIZE * data.size() + 5, 
						 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);
		
		g2.setStroke(new BasicStroke(Config.LOG_BORDER_WIDTH));
		g2.setColor(Config.LOG_BORDER_COLOR);
		g2.drawRoundRect(0, 
						 0,  
						 maxWidth + offset, 
						 Config.LOG_TEXT_SIZE * data.size() + 5, 
						 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);

		
		
		
		for(int i=0 ; i< data.size() ; i++){
			g2.drawString(data.get(i), 6, Config.LOG_TEXT_SIZE * i + Config.LOG_TEXT_SIZE);
		}
		
	}
}
