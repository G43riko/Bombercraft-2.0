package org.bombercraft2.game.entity.particles;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ParticleEmitter extends Emitter {
    private final Entity target;

    public ParticleEmitter(EmitterTypes type, GVector2f position, GameAble parent) {
        this(type, position, parent, null);
    }

    private ParticleEmitter(EmitterTypes type, GVector2f position, GameAble parent, Entity target) {
        super(type, position, parent);
        this.target = target;
    }

    //OVEERIDES

    @Override
    public void update(float delta) {
        if (target != null) {
            setPosition(target.getPosition().getAdd(target.getSize().getDiv(2)).getMul(getParent().getZoom()));
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

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }


}
