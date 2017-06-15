package Bombercraft2.Bombercraft2.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.Utils;
import utils.math.GVector2f;

public class PlayerSelector {
	private MyPlayer 	parent;
	private int			selectorWidth	= Config.PLAYER_SELECTOR_WIDTH;
	private Color		selectorColor	= Config.PLAYER_SELECTOR_COLOR;
	
	public PlayerSelector(MyPlayer parent){
		this.parent = parent;
	}
	public void render(Graphics2D g2){
		GVector2f pos = getSelectorPos().mul(parent.getParent().getZoom()).sub(parent.getParent().getOffset());
		GVector2f size = Block.SIZE.mul(parent.getParent().getZoom());
		g2.setStroke(new BasicStroke(selectorWidth));
		g2.setColor(selectorColor);
		g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
		
		g2.fillRect(pos.getXi() - 2, pos.getYi() - 2, 4, 4);
	}
	private GVector2f getSelectorPos(){
		GVector2f pos = parent.getPosition().add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		pos = pos.add(Utils.getNormalMoveFromDir(parent.getDirection())).mul(Block.SIZE);
		return pos;
	}
}
