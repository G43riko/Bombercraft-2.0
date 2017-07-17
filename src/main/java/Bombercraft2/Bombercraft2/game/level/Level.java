package Bombercraft2.Bombercraft2.game.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.flora.FloraManager;
import utils.Utils;
import utils.math.GVector2f;

public class Level implements Interactable {
	private Map 			map;
	private GameAble 		parent;
	private List<GVector2f> respawnZones 	= new ArrayList<GVector2f>();
	private String 			mapData;
	private JSONObject		floraData;
	private JSONObject 		playerInfo;
	private FloraManager	floraManager;
	//CONSTRUCTORS
	
	public Level(JSONObject object){
		try {
			mapData = object.getString(Texts.MAP);
			playerInfo = object.getJSONObject(Texts.PLAYER_INFO);
			int i = 0;
			while(object.has(Texts.RESPAWN_ZONE + i)){
				respawnZones.add(new GVector2f(object.getString((Texts.RESPAWN_ZONE + i))));
				i++;
			}
			floraData = object.getJSONObject(Texts.FLORA);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Level(){
		respawnZones.add(Block.SIZE);
		System.out.println("Level vytvoreny");
		setDefaultPlayerInfo();
		
	}
	
	//OTHERS
	
	public void changeBlock(GVector2f position, int healt, int type){
		
	}

	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		map.render(g2);
		floraManager.renderLowerLevel(g2);
	}
	
	public void renderUpperFlora(Graphics2D g2) {
		floraManager.renderUpperLevel(g2);
	}
	
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		try {
			result.put(Texts.MAP, map.toJSON());
			for(int i=0 ; i<respawnZones.size() ; i++){
				result.put(Texts.RESPAWN_ZONE + i, respawnZones.get(i));
			}
				
			result.put(Texts.PLAYER_INFO, playerInfo);
			result.put(Texts.FLORA, floraManager.toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//GETTERS
	
	public Map getMap(){return map;}
	public GameAble getParent() {return parent;}
	public boolean isReady(){return parent != null && map != null;}
	public List<GVector2f> getRespawnZones() {return new ArrayList<GVector2f>(respawnZones);}
	public GVector2f getRandomRespawnZone(){return new GVector2f(Utils.choose(respawnZones));}
	
	//SETTERS
	
	public void setDefaultPlayerInfo(){
		playerInfo = new JSONObject();
		try {
			playerInfo.put(Texts.SPEED, 4);
			playerInfo.put(Texts.RANGE, 2);
			playerInfo.put(Texts.HEALTH, 10);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setGame(GameAble game){
		parent = game;
		
		if(mapData != null){
			try {
				map = new Map(new JSONObject(mapData), parent);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else{
			map = new Map(game);
		}

		floraManager = new FloraManager(game, map);
		if(floraData != null){
			floraManager.fromJSON(floraData);
		}

	}
	

	public JSONObject getDefaultPlayerInfo() {
		return playerInfo;
	}


	
}
