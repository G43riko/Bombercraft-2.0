package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map.Entry;

public class StatsPanel  implements Interactable{
	private final GameAble parent;
	private       HashMap<String, String> stats          = new HashMap<>();
	private       int                     width          = 600;
	private final int                     verticalOffset = 10;
	private final int 					  fontSize		 = 22;
	private final GVector2f position;
	
	public StatsPanel(GameAble parent, GVector2f position, HashMap<String, String> hashMap){
		this.position = position;
		this.parent = parent;
		width = parent.getCanvas().getWidth() - position.getXi() * 2;
		stats = hashMap;
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC , fontSize));
		int pos = position.getYi() + 22;
		for(Entry<String, String> pair : stats.entrySet()){
			g2.drawString(pair.getKey(), position.getXi(), pos);
			int textWidth = g2.getFontMetrics().stringWidth(pair.getValue());

			g2.drawString(pair.getValue(), position.getXi() + width - textWidth, pos);

			g2.drawLine(position.getXi() + g2.getFontMetrics().stringWidth(pair.getKey()),
						pos,
						position.getXi() +
						width - textWidth - 5, pos);
			pos += verticalOffset + 22;
		}
		
	}
}
