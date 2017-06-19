package Bombercraft2.Bombercraft2.multiplayer;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.CoreGame;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
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
	private CommonMethods methods;
	//CONTRUCTORS
	
	public GameClient(MenuAble coreGame, String ip){
		super(ip);
		this.parent = coreGame;
//		GLog.write(GLog.CREATE, "GameClient vytvoren�");
	}
	
	//PUTTERS


	//GETTERS

//	public Level getLevel() {
//		return actLevel;
//	}
	@Override
	public void setBombExplode(GVector2f position, List<Block> blocks) {
		parent.getGame().explodeBombAt(position);
	}
	@Override
	public void onBombExplode(JSONObject data) {
		try {
//			JSONObject object = new JSONObject();
//			object.put(Texts.POSITION, position);
			JSONArray blocks = data.getJSONArray(Texts.HITTED_BLOCKS);
//			parent.getGame().explodeBombAt(new GVector2f(data.getString(Texts.POSITION)));
			for(int i=0 ; i<blocks.length() ; i++){
				GVector2f position = new GVector2f(blocks.getString(i));
				parent.getGame().getLevel().getMap().getBlock(position.getXi(), position.getYi()).remove();;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

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
	

	public void eatItem(GVector2f sur, int type) {
		GLogger.notImplemented();
	}
	public void bombExplode(Bomb bomb) {
		GLogger.notImplemented();
	}

	public void connectToGame(){
		sendPlayerInfo();
	}
	
	public void onPutHelper(JSONObject data){
		methods.onPutHelper(data);
	}
	
	protected void processMessage(String data) {
		try{
			JSONObject txt = new JSONObject(data);
			JSONObject msg = new JSONObject(txt.getString(Texts.MESSAGE));
			
			switch(txt.getString(Texts.TYPE)){
				case Server.BOMB_EXPLODE:
					onBombExplode(msg);
					break;
				case Server.GAME_INFO:
					parent.createGame(msg);
					methods = new CommonMethods(parent.getGame(), this);
					break;
				case Server.BASIC_INFO :
//					actLevel = new Level(new JSONObject(msg.getString("level")));
					connectToGame();
					break;
				case Server.CLOSE_CONNECTION:
					onCloseConnection();
					break;
				case Server.PLAYER_CHANGE :				
					onPlayerChange(msg);
					break;
				case Server.BUILD_BLOCK :
					onBuildBlock(msg);
					break;
				case Server.REMOVE_BLOCK :
					onRemoveBlock(msg);
					break;
				case Server.PUT_HELPER :
					onPutHelper(msg);
					break;
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
		write(parent.getPlayerInfo().toString() , Server.PLAYER_DATA);
	}

	@Override
	public void setRemoveBlock(GVector2f position) {
		methods.setRemoveBlock(position);
	}

	@Override
	public void setBuildBlock(GVector2f position, Type type) {
		methods.setBuildBlock(position, type);
	}

	@Override
	public void setBuildBlockArea(GVector2f minPos, GVector2f maxPos, Type blockType) {
		methods.setBuildBlockArea(minPos, maxPos, blockType);
	}

	@Override
	public void setPutHelper(GVector2f position, Helper.Type type) {
		methods.setPutHelper(position, type);
	}

	@Override
	public void setPlayerChange(Player player) {
		methods.setPlayerChange(player);
	}

	@Override
	protected void onConnectionBroken() {
		parent.showMessage(Texts.BROKEN_CONNECTION);
		parent.stopGame();
	}
	
	private void onCloseConnection(){
		parent.showMessage(Texts.GAME_CLOSED);
		parent.stopGame();
	}

	@Override
	public void setCloseConnection() {
		write("{}", Server.CLOSE_CONNECTION);
	}

	@Override
	public void onRemoveBlock(JSONObject data) {
		methods.onRemoveBlock(data);
	}

	@Override
	public void onBuildBlock(JSONObject data) {
		methods.onBuildBlock(data);
	}

	@Override
	public void onPlayerChange(JSONObject data) {
		methods.onPlayerChange(data);
	}
}
