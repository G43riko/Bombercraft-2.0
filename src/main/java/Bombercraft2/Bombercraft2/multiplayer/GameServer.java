package Bombercraft2.Bombercraft2.multiplayer;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletBasic;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletLaser;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter.Types;
import Bombercraft2.Bombercraft2.game.entity.weapons.WeaponLaser;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.level.Map;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import Bombercraft2.Bombercraft2.multiplayer.core.ClientPlayer;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import Bombercraft2.engine.Input;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;

public class GameServer extends Server implements Connector{
	private MenuAble parent;
	private CommonMethods methods;
	
	public GameServer(MenuAble parent) {
		this.parent = parent;

		parent.createGame(null);
		methods = new CommonMethods(parent.getGame(), this);
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
	public void setBuildBlockArea(GVector2f minPos, GVector2f maxPos, Type type) {
		methods.setBuildBlockArea(minPos, maxPos, type);
	}
	
	@Override
	public void setBombExplode(GVector2f position, List<Block> blocks, List<GVector2f> areas) {
		final int demage = 1;
		parent.getGame().explodeBombAt(position);
		List<String> hittedBlocks = blocks.stream()
										  .filter(a -> !a.isWalkable())
										  .peek(a -> a.remove())
										  .map(a -> a.getSur().toString())
										  .collect(Collectors.toList());
		int i = 0;
		JSONArray hittedPlayers = new JSONArray();
		while(i < areas.size()){
			parent.getGame()
				  .getSceneManager()
				  .getPlayersInArea(areas.get(i++), areas.get(i++))
				  .stream()
				  .peek(a -> a.hit(demage))
				  .map(a -> a.getName())
				  .forEach(hittedPlayers::put);
		}
		try {
			JSONObject object = new JSONObject();
			object.put(Texts.POSITION, position);
			object.put(Texts.HITTED_BLOCKS, new JSONArray(hittedBlocks));
			object.put(Texts.HITTED_PLAYERS, hittedPlayers);
			object.put(Texts.DEMAGE, demage);
			write(object.toString(), Server.BOMB_EXPLODE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setPutHelper(GVector2f position, Helper.Type type) {
		methods.setPutHelper(position, type);
	}
	
	public void onPutHelper(JSONObject data){
		methods.onPutHelper(data);
	}
	@Override
	protected void processMessage(String data, ClientPlayer client) {
		try {
			JSONObject txt = new JSONObject(data);
			JSONObject o = new JSONObject(txt.getString(Texts.MESSAGE));
			String type = txt.getString(Texts.TYPE);
			switch(type){
				case PUT_HELPER :
					writeExcept(o.toString(), type, client);
					onPutHelper(o);
					break;
				case PUT_BULLET :
					writeExcept(o.toString(), type, client);
					onPutBullet(o);
					break;
				case PLAYER_DATA :
					renameClient(client.getId() + "", o.getString(Texts.NAME));
					parent.getGame().addPlayer(o.getString(Texts.NAME), o.getString(Texts.IMAGE));
					client.write(getGameInfo(), GAME_INFO);
					parent.showMessage(Texts.PLAYER_CONNECTED, client.getName());
					break;
				case Server.CLOSE_CONNECTION:
					onClientDisonnected(client);
					break;
				case Server.BUILD_BLOCK :
					writeExcept(txt.getString(Texts.MESSAGE), type, client);
					onBuildBlock(o);
					break;
				case Server.REMOVE_BLOCK :
					writeExcept(txt.getString(Texts.MESSAGE), type, client);
					onRemoveBlock(o);
					break;
				case PLAYER_CHANGE :
					writeExcept(txt.getString(Texts.MESSAGE), type, client);
					onPlayerChange(o);
					break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	private void onClientDisonnected(ClientPlayer client) {
		removeClient(client.getName());
		parent.getGame().removePlayer(client.getName());
		parent.showMessage(Texts.PLAYER_DISCONNECTED, client.getName());
	}

	@Override
	public void setPlayerChange(Player player) {
		methods.setPlayerChange(player);
	}
	
	protected String getGameInfo(){
		return parent.getGame().getGameInfo();
	}
	@Override
	protected String getBasicInfo() {
		return parent.getGame().getBasicInfo();
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

	@Override
	public void onHitPlayer(String name, int demage) {
		parent.getGame().getSceneManager().getPlayerByName(name).hit(demage);
	}
	@Override
	public void setPutEmmiter(Types emitterOnHit, GVector2f position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean bulletHitEnemy(Bullet bulletInstance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void hitBlock(GVector2f position, int demage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPutBullet(MyPlayer myPlayer, Shootable shooter) {
		methods.setPutBullet(myPlayer, shooter);
	}

	@Override
	public void onPutBullet(JSONObject data) {
		methods.onPutBullet(data);
	}
}
