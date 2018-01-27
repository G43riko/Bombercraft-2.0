package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.particles.SimpleParticle;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticlesDemo extends GameState {
    private static final Color PARTICLE_COLOR = Color.RED;
    private final CorePlayGround parent;

    private final        List<SimpleParticle> particles = new ArrayList<>();
    private final static int                  PARTICLES = 100000;

    public ParticlesDemo(CorePlayGround parent) {
        super(Type.ParticlesDemo);
        this.parent = parent;

        int particleSpeed = 1;
        int particleSize = 1;
        for (int i = 0; i < PARTICLES; i++) {
            SimpleParticle particle = new SimpleParticle();
            particle.posX = (float) (Math.random() * (parent.getCanvas().getWidth() - particleSize * 2) + particleSize);
            particle.posY = (float) (Math.random() * (parent.getCanvas()
                                                            .getHeight() - particleSize * 2) + particleSize);
            double angle = Math.random() * 360;

            particle.velX = (float) (Math.sin(angle) * particleSpeed);
            particle.velY = (float) (Math.cos(angle) * particleSpeed);
            particle.radius = particleSize;
            particles.add(particle);
        }


    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
    }
    @Override
    public void update(float delta) {
        particles.parallelStream().forEach(particle -> {
            particle.move(delta);
            particle.checkBorders(parent.getCanvas());
        });
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        g2.setColor(PARTICLE_COLOR);
        particles.forEach(particle -> {
            particle.fillRect(g2);
            particle.fillArc(g2);
        });
    }

    @Override
    public void doAct(GVector2f click) {

    }
}
