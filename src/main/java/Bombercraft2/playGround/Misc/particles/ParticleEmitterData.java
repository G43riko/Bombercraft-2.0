package Bombercraft2.playGround.Misc.particles;

import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ParticleEmitterData {
    public enum Type {
        CLASSIC,
        GREEN
    }

    private float     particlePerFrame;
    private GVector2f positionRandomness;
    private Color     color;
    private GVector2f speed; // x - value, y - randomness
    private GVector2f health; // - normal, y - randomness
    private GVector2f direction; // x - start angle, y - end angle
    private GVector2f size; // - normal, y - randomness
    private int       sizeRandomness;
    private int       particlesOnStart;

    private static double random() {
        return Math.random() - 0.5;
    }

    public int getParticlesOnStart() {
        return particlesOnStart;
    }
    /*
    public float getParticlesPerFrame() {
        return particlePerFrame;
    }
    */

    public int getNewParticlesCount() {
        return (int)(particlePerFrame >= 1 ? particlePerFrame : Math.random() < particlePerFrame ? 1 : 0);
    }

    public ParticleInstanceData getInstanceData(GVector2f position) {
        return new ParticleInstanceData(position.add(new GVector2f(random(), random()).mul(positionRandomness)),
                                        color,
                                        new GVector2f(random(), random()).mul(speed.getX()),
                                        size.add(sizeRandomness * (float) (random())),
                                        (int) (health.getXi() + health.getYi() * (Math.random() - 0.5)));
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
        result.size = new GVector2f(5, 5);
        result.speed = new GVector2f(20, 20);
        result.positionRandomness = new GVector2f(1, 1);
        result.direction = new GVector2f(0, 360);
        result.sizeRandomness = 1;
        result.health = new GVector2f(100, 20);
        result.color = Color.GREEN;

        return result;
    }


    @NotNull
    private static ParticleEmitterData getClassicData() {
        return getGreenData();
    }
}
