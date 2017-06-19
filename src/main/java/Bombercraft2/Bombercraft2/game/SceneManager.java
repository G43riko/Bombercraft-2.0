package Bombercraft2.Bombercraft2.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.explosion.Explosion;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.entity.particles.ParticleEmmiter;
import Bombercraft2.Bombercraft2.game.player.Player;
import utils.math.GVector2f;
import utils.resouces.JSONAble;

public class SceneManager implements Interactable, JSONAble{
	private HashMap<String, Player>	players				= new HashMap<String, Player>();
	private HashMap<String, Player>	playersNew			= new HashMap<String, Player>();
	private HashMap<String, Helper>	helpers				= new HashMap<String, Helper>();
	private ArrayList<Emitter>		emitters			= new ArrayList<Emitter>();
	private HashSet<Helper>			helpersRemoved		= new HashSet<Helper>();
	private ArrayList<Explosion>	explosions			= new ArrayList<Explosion>();
	private GameAble 				parent;
	private long					renderedParticles	= 0;
	
	public SceneManager(GameAble parent){
		this.parent = parent;
	}
	
	public void addPlayer(Player player){
		players.put(player.getName(), player);
	}
	
	public void addHelper(String key, Helper helper){
		helpers.put(key, helper);
	}
	
	@Override
	public void render(Graphics2D g2) {
		players.values().stream().filter(parent::isVisible).forEach(a -> a.render(g2));
		helpers.values().stream().filter(parent::isVisible).forEach(a -> a.render(g2));
		
		renderedParticles = new ArrayList<Emitter>(emitters).stream()
				.peek(a -> a.render(g2))
				.mapToLong(a -> a.getRenderedParticles())
				.sum();
		
		new ArrayList<Explosion>(explosions).stream().forEach(a -> a.render(g2));
	}
	
	@Override
	public void update(float delta) {
		playersNew.forEach((a, b)-> players.put(a, b));
		playersNew.clear();
		

		emitters = new ArrayList<Emitter>(emitters).stream()
											  	   .peek(a -> a.update(delta))
											  	   .collect(Collectors.toCollection(ArrayList::new));
		
		
		helpers.values().removeAll(helpersRemoved);
		helpersRemoved.clear();
		helpers.values().stream().filter(parent::isVisible).forEach(a -> a.update(delta));
		

		explosions = new ArrayList<Explosion>(explosions).stream()
														 .filter(a -> a.isAlive())
			  	   									     .peek(a -> a.update(delta))
			  	   									     .collect(Collectors.toCollection(ArrayList::new));
	}
	
	public boolean existHelperOn(String key){
		return helpers.containsKey(key);
	}
	
	public int getPlayersCount(){
		return players.size();
	}

	public Player getPlayerByName(String name){
		return players.get(name);
	}
	public void removePlayer(String key){
		players.remove(key);
	}
	public void removeHelper(String key) {
		helpersRemoved.add(helpers.get(key));
	}

	@Override
	public void fromJSON(JSONObject data) {
		
	}
	public void addEmmiter(GVector2f position, String type) {
		emitters.add(new ParticleEmmiter(type, position,  parent));
	}
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number){
		explosions.add(new Explosion(position, parent, size, color, number));
	}
	@Override
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			Collection<Player> values = players.values();
			for(Player a : values){
				result.put("player_" + result.length(), a.toJSON());
			}
			result.put(Texts.PLAYERS_NUMBER, players.size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
