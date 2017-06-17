package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public class GameServer implements Connector{
	private GameAble parent;
	
	public GameServer(GameAble parent) {
		this.parent = parent;
	}

	@Override
	public void removeBlock(GVector2f pos) {
		parent.getLevel().getMap().remove(pos);
	}

	@Override
	public void buildBlock(GVector2f pos, Block.Type blockType) {
		parent.getLevel().getMap().getBlock(pos.getXi(), pos.getYi()).build(blockType);
	}

	@Override
	public void buildBlockArea(GVector2f minPos, GVector2f maxPos, Block.Type blockType) {
		for(int i=minPos.getXi() ; i<=maxPos.getX() ; i++){
			for(int j=minPos.getYi() ; j<=maxPos.getY() ; j++){
				parent.getLevel().getMap().getBlock(i, j).build(blockType);
			}
		}
	}

	@Override
	public void putHelper(GVector2f pos, Helper.Type type) {
		parent.putHelper(pos, type, System.currentTimeMillis());
	}
}
