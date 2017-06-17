package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import utils.math.GVector2f;

public interface Connector {

	void removeBlock(GVector2f pos);

	void buildBlock(GVector2f pos, Type blockType);

	void buildBlockArea(GVector2f minPos, GVector2f maxPos, Type blockType);
//
//	void putBomb(GVector2f pos, Bombercraft2.Bombercraft2.game.entity.Helper.Type type);

	void putHelper(GVector2f pos, Helper.Type type);

}
