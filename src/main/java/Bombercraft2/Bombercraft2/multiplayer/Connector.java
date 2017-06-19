package Bombercraft2.Bombercraft2.multiplayer;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import utils.math.GVector2f;

public interface Connector {
	public void onPlayerChange(JSONObject data);
	public void setPlayerChange(Player player);
	
	public void onRemoveBlock(JSONObject data);
	public void setRemoveBlock(GVector2f position);

	public void onBuildBlock(JSONObject data);
	public void setBuildBlock(GVector2f position, Type type);

	public void setBuildBlockArea(GVector2f minPosition, GVector2f maxPosition, Type type);

	default public void onBombExplode(JSONObject data){};
	default public void setBombExplode(GVector2f position, List<Block> blocks){};
	
	public void onPutHelper(JSONObject data);
	public void setPutHelper(GVector2f pos, Helper.Type type);

	public void hitBlock(GVector2f position, int demage);

	/**
	 * Tato funkcia sa vola pri presusenie hry aby sa dalo ostatnym hracom vediet ze bola hra prerusena
	 */
	public void setCloseConnection();

	public void cleanUp();


}
