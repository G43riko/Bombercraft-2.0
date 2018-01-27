package Bombercraft2.playGround.Misc.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class SimpleEmitter implements InteractAble{
	@NotNull 
    protected final SimpleGameAble parent;
    private ArrayList<SimpleParticle> particles = new ArrayList<>();

    float particlePerFrame;
    private GVector2f positionRandomness;
    private Color color;
    private GVector2f speed; // x - value, y - randomness
    private GVector2f health; // - normal, y - randomness
    private GVector2f direction; // x - start angle, y - end angle
    private GVector2f size; // - normal, y - randomness
    private int       sizeRandomness;
    private final GVector2f position;
    private int       particlesOnStart;
    
    public SimpleEmitter(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
    	this.parent = parent;
    	this.position = position;
    	initDefault();
        createParticles(particlesOnStart);
    }
    @NotNull
    private double random() {
    	return Math.random() - 0.5;
    }
    
    public void initDefault() {
    	particlePerFrame = 20f;
    	size = new GVector2f(5, 5);
    	speed = new GVector2f(20, 20);
    	positionRandomness = new GVector2f(1, 1);
    	health = new GVector2f(100, 20);
    	direction = new GVector2f(0, 360);
    }

    @Override
    public void render(Graphics2D g2) {
    	particles.stream()
    			 //.filter(parent::isVisible)
    			 .forEach(a -> a.render(g2));
    }
    
    private void createParticles(int numOfParticles) {
        for (int i = 0; i < numOfParticles; i++) {
            particles.add(new SimpleParticle(position.add(new GVector2f(random(), random()).mul(positionRandomness)),
                                       parent,
                                       color,
                                       new GVector2f(random(), random()).mul(speed.getX()),
                                       size.add(sizeRandomness * (float) (random())),
                                       (int) (health.getXi() + health.getYi() * (Math.random() - 0.5))));
        }
    }
    
    @Override
    public void update(float delta) {
            float add;
            if (particlePerFrame >= 1) { add = particlePerFrame; }
            else { add = Math.random() < particlePerFrame ? 1 : 0; }

            createParticles((int) add);

        particles = new ArrayList<>(particles).stream()
                                              //.filter(a -> a.isALive())
                                              .peek(a -> a.update(delta))
                                              .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public void setPosition(GVector2f position) {
    	this.position.set(position);
    }

    public boolean isAlive() {return !particles.isEmpty();}
}
