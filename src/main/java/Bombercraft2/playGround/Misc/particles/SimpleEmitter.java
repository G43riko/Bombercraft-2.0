package Bombercraft2.playGround.Misc.particles;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class SimpleEmitter implements InteractAble {
    @NotNull
    protected final SimpleGameAble parent;
    private ArrayList<AbstractParticle> particles = new ArrayList<>();
    private ParticleEmitterData data = ParticleEmitterData.getData(ParticleEmitterData.Type.GREEN);

    private final GVector2f position;

    public SimpleEmitter(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
        this.parent = parent;
        this.position = position;
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
                                              //.filter(a -> a.isALive())
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
