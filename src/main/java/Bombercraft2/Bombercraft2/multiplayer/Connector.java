package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.Player;
import utils.math.GVector2f;

public interface Connector {

	void removeBlock(GVector2f pos);

	void buildBlock(GVector2f pos, Type blockType);

	void buildBlockArea(GVector2f minPos, GVector2f maxPos, Type blockType);

	void putHelper(GVector2f pos, Helper.Type type);

	void hitBlock(GVector2f position, int demage);


}
