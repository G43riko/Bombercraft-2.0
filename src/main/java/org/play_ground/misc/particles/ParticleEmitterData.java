package org.play_ground.misc.particles;

import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

import java.awt.*;

public class ParticleEmitterData {
    private float     particlePerFrame;
    private BVector2f positionRandomness;
    private Color     color;
    private BVector2f speed; // x - value, y - randomness
    private BVector2f health; // - normal, y - randomness
    private BVector2f direction; // x - start angle, y - end angle
    private BVector2f size; // - normal, y - randomness
    private int       sizeRandomness;
    private int       particlesOnStart;

    private static double random() {
        return Math.random() - 0.5;
    }

    @NotNull
    public static ParticleEmitterData getData(@NotNull Type type) {
        switch (type) {
            case CLASSIC:
                return getClassicData();
            case GREEN:
                return getGreenData();
            default:
                throw new Error("Unknow particly type: " + type);
        }
    }

    @NotNull
    private static ParticleEmitterData getGreenData() {
        final ParticleEmitterData result = new ParticleEmitterData();

        result.particlePerFrame = 20f;
        result.size = new BVector2f(5, 5);
        result.speed = new BVector2f(10, 20);
        result.positionRandomness = new BVector2f(1, 1);
        result.direction = new BVector2f(0, 360);
        result.sizeRandomness = 1;
        result.health = new BVector2f(20, 20);
        result.color = Color.GREEN;

        return result;
    }
    /*
    public float getParticlesPerFrame() {
        return particlePerFrame;
    }
    */

    @NotNull
    private static ParticleEmitterData getClassicData() {
        return getGreenData();
    }

    public int getParticlesOnStart() {
        return particlesOnStart;
    }

    public int getNewParticlesCount() {
        return (int) (particlePerFrame >= 1 ? particlePerFrame : Math.random() < particlePerFrame ? 1 : 0);
    }

    public ParticleInstanceData getInstanceData(BVector2f position) {
        return new ParticleInstanceData(position.getAdd(new BVector2f(random(), random()).getMul(positionRandomness)),
                                        color,
                                        new BVector2f(random(), random()).getMul(speed.getX()),
                                        size.getAdd(sizeRandomness * (float) (random())),
                                        (int) (health.getXi() + health.getYi() * (Math.random() - 0.5)));
    }


    public enum Type {
        CLASSIC,
        GREEN
    }
}
