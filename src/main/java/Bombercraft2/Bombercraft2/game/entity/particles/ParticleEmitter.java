package Bombercraft2.Bombercraft2.game.entity.particles;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ParticleEmitter extends Emitter {
    private final Entity target;

    //CONSTRUCTORS
    public ParticleEmitter(Emitter.Types type, GVector2f position, GameAble parent) {
        this(type, position, parent, null);
    }

    public ParticleEmitter(Emitter.Types type, GVector2f position, GameAble parent, Entity target) {
        super(type, position, parent);
        this.target = target;
    }

    //OVEERIDES

    @Override
    public void update(float delta) {
        if (target != null) {
            setPosition(target.getPosition().add(target.getSize().div(2)).mul(getParent().getZoom()));
            if (!target.isAlive()) { alive = false; }
        }

        if (alive) {
            float add;
            if (particlePerFrame >= 1) { add = particlePerFrame; }
            else { add = Math.random() < particlePerFrame ? 1 : 0; }

            createParticles((int) add);
        }

        particles = new ArrayList<>(particles).stream()
                                                      .filter(Entity::isAlive)
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
