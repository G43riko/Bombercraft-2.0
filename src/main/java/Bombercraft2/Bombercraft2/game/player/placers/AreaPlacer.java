package Bombercraft2.Bombercraft2.game.player.placers;

import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public class AreaPlacer extends Placer{
	private GVector2f starPos = null;
	public AreaPlacer(GameAble parent) {
		super(parent);
	}
	@Override
	public void useOnLocalPos(GVector2f pos) {
		if(starPos == null){
			starPos = pos;
		}
		else{
			GVector2f minPos = pos.min(starPos);
			GVector2f maxPos = pos.max(starPos);
			
			parent.getConnector().buildBlockArea(minPos, maxPos, blockType);
			
			starPos = null;
		}
		
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(starPos == null){
			return;
		}
		
		GVector2f globalPosStart 	= starPos.mul(Block.SIZE).sub(parent.getOffset());
		GVector2f globalPosEnd 		= parent.getPlayerTarget().sub(parent.getOffset()).add(Block.SIZE);
		GVector2f size				= globalPosEnd.sub(globalPosStart).div(Block.SIZE).toInt().mul(Block.SIZE).abs();
		
		System.out.println("size: " + size);
		g2.setColor(Color.ORANGE);
		g2.fillRect(globalPosStart.getXi(), globalPosStart.getYi(), size.getXi(), size.getYi());
	}
}
