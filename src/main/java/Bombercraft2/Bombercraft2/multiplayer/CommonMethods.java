package Bombercraft2.Bombercraft2.multiplayer;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import Bombercraft2.Bombercraft2.multiplayer.core.Writable;
import utils.GLogger;
import utils.math.GVector2f;

public class CommonMethods {
	private GameAble game;
	private Writable parent;
	
	public CommonMethods(GameAble game, Writable parent){
		this.game = game;
		this.parent = parent;
	}
	
	public void onRemoveBlock(JSONObject data) {
		try {
			game.getLevel().getMap().remove(new GVector2f(data.getString(Texts.POSITION)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void onBuildBlock(JSONObject data) {
		try {
			GVector2f position = new GVector2f(data.getString(Texts.POSITION));
			Block block = game.getLevel().getMap().getBlock(position.getXi(), position.getYi());
			if(block == null){
				GLogger.printLine("ide sa postavit blok na neexistujucej pozicii: " + position);
				return;
			}
			block.build(Type.valueOf(data.getString(Texts.TYPE)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void onPlayerChange(JSONObject data) {
		try {
			Player p = game.getPlayerByName(data.getString(Texts.PLAYER));
			p.setDirection(Direction.valueOf(data.getString(Texts.PLAYER_DIRECTION)));
			p.setPosition(new GVector2f(data.getString(Texts.PLAYER_POSITION)));
			p.setMoving(true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void setPutHelper(GVector2f position, Helper.Type type) {
		long createdAt = System.currentTimeMillis();
		game.putHelper(position, type, createdAt);

		try {
			JSONObject result = new JSONObject();
			result.put(Texts.CREATED_AT, createdAt);
			result.put(Texts.POSITION, position);
			result.put(Texts.TYPE, type);
			parent.write(result.toString(), Server.PUT_HELPER);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void onPutHelper(JSONObject data){
		try {
			game.putHelper(new GVector2f(data.getString(Texts.POSITION)), 
									   Helper.Type.valueOf(data.getString(Texts.TYPE)), 
									   data.getLong(Texts.CREATED_AT));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setRemoveBlock(GVector2f position) {
		game.getLevel().getMap().remove(position);
		try {
			JSONObject result = new JSONObject();
			result.put(Texts.POSITION, position);
			parent.write(result.toString(), Server.REMOVE_BLOCK);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setBuildBlock(GVector2f position, Type type) {
		game.getLevel().getMap().getBlock(position.getXi(), position.getYi()).build(type);
		try {
			JSONObject result = new JSONObject();
			result.put(Texts.POSITION, position);
			result.put(Texts.TYPE, type);
			parent.write(result.toString(), Server.BUILD_BLOCK);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setBuildBlockArea(GVector2f minPos, GVector2f maxPos, Type blockType) {
		for(int i=minPos.getXi() ; i<=maxPos.getX() ; i++){
			for(int j=minPos.getYi() ; j<=maxPos.getY() ; j++){
				game.getLevel().getMap().getBlock(i, j).build(blockType);
			}
		}
	}
	
	public void setPlayerChange(Player player) {
		try {
			JSONObject object = new JSONObject();
			object.put(Texts.PLAYER_POSITION, player.getPosition());
			object.put(Texts.PLAYER_DIRECTION, player.getDirection());
			object.put(Texts.PLAYER, player.getName());
			parent.write(object.toString(), Server.PLAYER_CHANGE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
