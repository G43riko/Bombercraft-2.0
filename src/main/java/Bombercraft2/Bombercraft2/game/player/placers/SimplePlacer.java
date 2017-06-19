package Bombercraft2.Bombercraft2.game.player.placers;

import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

public class SimplePlacer extends Placer{
	
	public SimplePlacer(GameAble parent) {
		super(parent);
	}

	@Override
	public void useOnLocalPos(GVector2f pos) {
		parent.getConnector().setBuildBlock(pos, blockType);
		
	}

}
