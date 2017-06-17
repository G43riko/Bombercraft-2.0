package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.multiplayer.core.ClientPlayer;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;

public class GameServer extends Server implements Connector{
	private MenuAble parent;
	
	public GameServer(MenuAble parent) {
		this.parent = parent;

		parent.createGame(null);
	}

	@Override
	public void removeBlock(GVector2f pos) {
		parent.getGame().getLevel().getMap().remove(pos);
	}

	@Override
	public void buildBlock(GVector2f pos, Block.Type blockType) {
		parent.getGame().getLevel().getMap().getBlock(pos.getXi(), pos.getYi()).build(blockType);
	}

	@Override
	public void buildBlockArea(GVector2f minPos, GVector2f maxPos, Block.Type blockType) {
		for(int i=minPos.getXi() ; i<=maxPos.getX() ; i++){
			for(int j=minPos.getYi() ; j<=maxPos.getY() ; j++){
				parent.getGame().getLevel().getMap().getBlock(i, j).build(blockType);
			}
		}
	}

	@Override
	public void putHelper(GVector2f pos, Helper.Type type) {
		parent.getGame().putHelper(pos, type, System.currentTimeMillis());
	}

	@Override
	protected void processMessage(String txt, ClientPlayer client) {
		GLogger.notImplemented();
	}

	@Override
	protected String getBasicInfo() {
		return parent.getGame().getBasicInfo();
	}

	@Override
	public void hitBlock(GVector2f position, int demage) {
		GLogger.notImplemented();
	}
}
