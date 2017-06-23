package Bombercraft2.Bombercraft2.game.entity.particles;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import utils.math.GVector2f;

public class ParticleEmmiter extends Emitter{
	private Entity target;
	
	//CONSTRUCTORS
	public ParticleEmmiter(Emitter.Types type, GVector2f position, GameAble parent){
		this(type, position, parent, null);
	}
	
	public ParticleEmmiter(Emitter.Types type, GVector2f position, GameAble parent, Entity target) {
		super(type, position, parent);
		this.target = target;
	}
	
	//OVEERIDES

	@Override
	public void update(float delta) {
		if(target != null){
			setPosition(target.getPosition().add(target.getSize().div(2)).mul(getParent().getZoom()));
			if(!target.isAlive())
				alive = false;
		}
		
		if(alive){
			float add = 0;
			if(particlePerFrame >= 1)
				add = particlePerFrame;
			else
				add = Math.random() < particlePerFrame ? 1 : 0;

			createParticles((int)add);
		}
				
		particles = new ArrayList<Particle>(particles).stream()
							 						  .filter(a -> a.isAlive())
							 						  .peek(a -> a.update(delta))
							 						  .collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public void cleanUp() {
		particles.clear();
	}
	
	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
