package Bombercraft2.Bombercraft2.game.entity.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.Utils;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public abstract class Emitter extends Entity{
	private static HashMap<Types, JSONObject> predefinedParticles = new HashMap<Types, JSONObject>();
	public enum Types{
		PARTICLE_EMITTER_TEST("emmiterTest"),
		PARTICLE_EXPLOSION_TEST("explosionTest"),
		PARTICLE_EXPLOSION_BOW_HIT("explosionBowHit"),
		PARTICLE_EXPLOSION_DEFAULT_HIT("explosionDefaultHit"),
		PARTICLE_EMITTER_GREEN_MAGIC("particleEmmiterGreenMagic"),
		PARTICLE_EXPLOSION_BLUE_SPARK("particleExplosionBlueSpark");
		private String name;
		Types(String name){this.name = name;}
		public String getName(){return name;}
	}
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	protected Color color;
	protected float particlePerFrame;
	protected GVector2f speed; // x - value, y - randomness
	protected GVector2f rotation; // - speed, y - randomness
	protected GVector2f healt; // - normal, y - randomness
	protected GVector2f direction; // x - start angle, y - end angle
	protected GVector2f size; 
	protected int sizeRandomness;
	protected GVector2f positionRandomness; 
	protected long renderedParticles;
	private int particlesOnStart;
	static{
		initDefault();
	}
//	
	//CONTRUCTORS
	
	public Emitter(String type, GVector2f position, GameAble parent) {
		super(position, parent);
		
		loadDataFromJSON(predefinedParticles.get(type));
		
		if(type == Types.PARTICLE_EXPLOSION_BLUE_SPARK.toString()){
			color = Utils.choose(Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHT_GRAY, Color.BLUE);
		}
			
		createParticles(particlesOnStart);
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		renderedParticles = new ArrayList<Particle>(particles).stream()
															  .filter(a -> getParent().isVisible(a))
															  .peek(a->a.render(g2))
															  .count();
	}
	
	protected void createParticles(int numOfParticles){
		for(int i=0 ; i<numOfParticles ; i++)
			particles.add(new Particle(position.add(new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(positionRandomness)), 
									   getParent(), 
									   color, 
									   new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(speed.getX()), 
									   size.add(sizeRandomness * (float)(Math.random() - 0.5)), 
									   (int)(healt.getXi() + healt.getYi() * (Math.random() - 0.5))));
	}

	private void loadDataFromJSON(JSONObject object){
		try {
			this.color = new Color(object.getInt("color"));
			this.speed = new GVector2f(object.getString("speed"));
			this.rotation = new GVector2f(object.getString("rotation"));
			this.healt = new GVector2f(object.getString("healt"));
			this.direction = new GVector2f(object.getString("speed"));
			this.size = new GVector2f(object.getString("size"));
			this.sizeRandomness = object.getInt("sizeRandomness");
			this.positionRandomness = new GVector2f(object.getString("positionRandomness"));
			this.particlePerFrame = (float)object.getDouble("particlePerFrame");
			this.particlesOnStart = object.getInt("particlesOnStart");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initDefault() {
		try {
			Types[] types = Types.values();
			JSONObject object = ResourceLoader.getJSON(Config.FILE_PARTICLES);
			for(int i=0 ; i<types.length ; i++){
				predefinedParticles.put(types[i], object.getJSONObject(types[i].getName()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public GVector2f getSur() {return getPosition().div(Block.SIZE).toInt();}
	public long getRenderedParticles() {return renderedParticles;}
	public GVector2f getSize() {return size;}
	public boolean isAlive() {return alive || !particles.isEmpty();}

}
