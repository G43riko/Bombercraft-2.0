package Bombercraft2.Bombercraft2.multiplayer;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import Bombercraft2.Bombercraft2.multiplayer.core.Client;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;

public class GameClient extends Client implements Connector{
	private MenuAble parent;
	
	//CONTRUCTORS
	
	public GameClient(MenuAble coreGame){
		this.parent = coreGame;
//		GLog.write(GLog.CREATE, "GameClient vytvoren�");
	}
	
	//PUTTERS


	//GETTERS

//	public Level getLevel() {
//		return actLevel;
//	}

	public int getNumberPlayersInGame() {
		GLogger.notImplemented();
		return 0;
	}

	public GVector2f getMyPosition() {
		GLogger.notImplemented();
		return null;
	}

	public boolean isOnline() {
		GLogger.notImplemented();
		return false;
	}

	//OTHERS
	
	public void playerNewPos(Player player) {
		try {
			JSONObject object = new JSONObject();
			object.put("position", player.getPosition());
			object.put("direction", player.getDirection());
			object.put("player", player.getName());
			write(object.toString(), Server.PLAYER_MOVE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void eatItem(GVector2f sur, int type) {
		GLogger.notImplemented();
	}
	public void bombExplode(Bomb bomb) {
		GLogger.notImplemented();
	}

	protected void processMessage(String data) {
		try{
			JSONObject txt = new JSONObject(data);
			JSONObject msg = new JSONObject(txt.getString("msg"));
			
			switch(txt.getString("type")){
				case Server.LEVEL_INFO :
//					actLevel = new Level(new JSONObject(msg.getString("level")));
					parent.createGame(msg);
					Utils.sleep(100);
					sendPlayerInfo();
					break;
				case Server.PLAYER_MOVE :
					
					Player p = parent.getGame().getPlayers().get(msg.getString("player"));
					p.setDirection(Direction.valueOf(msg.getString("direction")));
					p.setPosition(new GVector2f(msg.getString("position")));
					break;
				case Server.PUT_HELPER :
					parent.getGame().addHelper(new GVector2f(msg.getString("selectorSur")),
												 msg.getInt("cadenceBonus"),
												 new GVector2f(msg.getString("position")),
												 msg.getInt("demage"),
												 msg.getString("type"),
												 new GVector2f(msg.getString("target")));
					break;
				case Server.PUT_BOMB :
					parent.getGame().addBomb(new GVector2f(msg.getString("position")), 
											   msg.getInt("range"),
											   msg.getInt("time"),
											   msg.getInt("demage"));
				case Server.HIT_BLOCK :
	//				GVector2f pos = new GVector2f(msg.getString("position"));
	//				actLevel.getParent().getLevel().getMap().getBlockOnPosition(pos).hit(msg.getInt("demage"));
					break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void hitBlock(GVector2f position, int demage) {
		GLogger.notImplemented();
	}

	@Override
	public void sendPlayerInfo() {
		try {
			JSONObject result = new JSONObject();
//			result.put("name", actLevel.getParent().getProfil().getName());
			result.put("name", parent.getGame().getMyPlayer().getName());
			result.put("avatar", parent.getGame().getProfil().getAvatar());
			write(result.toString() , Server.PLAYER_NAME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeBlock(GVector2f pos) {
		GLogger.notImplemented();
	}

	@Override
	public void buildBlock(GVector2f pos, Type blockType) {
		GLogger.notImplemented();
	}

	@Override
	public void buildBlockArea(GVector2f minPos, GVector2f maxPos, Type blockType) {
		GLogger.notImplemented();
	}

	@Override
	public void putHelper(GVector2f pos, Bombercraft2.Bombercraft2.game.entity.Helper.Type type) {
		GLogger.notImplemented();
	}
}
