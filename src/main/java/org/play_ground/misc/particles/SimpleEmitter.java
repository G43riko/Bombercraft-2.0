package org.play_ground.misc.particles;

import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SimpleEmitter implements InteractAbleG2 {
    @NotNull
    protected final SimpleGameAble              parent;
    private final   GVector2f                   position;
    private         ArrayList<AbstractParticle> particles = new ArrayList<>();
    @NotNull
    private         ParticleEmitterData         data;

    public SimpleEmitter(@NotNull GVector2f position,
                         @NotNull SimpleGameAble parent,
                         @NotNull ParticleEmitterData.Type type
                        ) {
        this.parent = parent;
        this.position = position;
        data = ParticleEmitterData.getData(ParticleEmitterData.Type.GREEN);
        createParticles(data.getParticlesOnStart());
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        particles.stream()
                 .filter(parent::isVisible)
                 .forEach(a -> a.render(g2));
    }

    private void createParticles(int numOfParticles) {
        for (int i = 0; i < numOfParticles / 2; i++) {
            particles.add(new AbstractParticle(parent, data.getInstanceData(position)));
        }
    }

    @Override
    public void update(float delta) {
        createParticles(data.getNewParticlesCount());

        particles = new ArrayList<>(particles).stream()
                                              .filter(AbstractParticle::isAlive)
                                              .peek(a -> a.update(delta))
                                              .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setPosition(@NotNull GVector2f position) {
        this.position.set(position);
    }

    public boolean isAlive() {
        return !particles.isEmpty();
    }
}
