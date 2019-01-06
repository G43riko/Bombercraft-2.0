package org.play_ground.demos;

import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.misc.SimpleParticlePlayer;
import org.play_ground.misc.particles.SimpleParticle;
import org.utils.enums.Keys;
import utils.math.BVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDemo extends GameState {
    private final List<SimpleParticle> barrels                = new ArrayList<>();
    private final CorePlayGround       parent;
    private final SimpleParticlePlayer mySimpleParticlePlayer = new SimpleParticlePlayer();

    public CollisionDemo(CorePlayGround parent) {
        super(GameStateType.CollisionDemo);
        this.parent = parent;

        mySimpleParticlePlayer.posX = 40;
        mySimpleParticlePlayer.posY = 40;
        mySimpleParticlePlayer.radius = 20;


        SimpleParticle barrel = new SimpleParticle();
        barrel.radius = 50;
        barrel.color = Color.GREEN;
        barrel.posX = 400;
        barrel.posY = 400;

        barrels.add(barrel);
    }

    private static BVector2f rotate(float x, float y, float angle) {
        return new BVector2f(x * Math.cos(angle) - y * Math.sin(angle),
                             x * Math.sin(angle) + y * Math.cos(angle));
    }

    private static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private static void resolveCollision(SimpleParticle particle, SimpleParticle otherParticle) {
        final float xVelocityDiff = particle.velX - otherParticle.velX;
        final float yVelocityDiff = particle.velY - otherParticle.velY;

        final float xDist = otherParticle.posX - particle.posX;
        final float yDist = otherParticle.posY - particle.posY;

        // Prevent accidental overlap of particles
        if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) {

            // Grab angle between the two colliding particles
            final float angle = -(float) Math.atan2(otherParticle.posY - particle.posY,
                                                    otherParticle.posX - particle.posX);

            // Store mass in var for better readability in collision equation
            final float m1 = particle.mass;
            final float m2 = otherParticle.mass;

            // Velocity before equation
            final BVector2f u1 = rotate(particle.velX, particle.velY, angle);
            final BVector2f u2 = rotate(otherParticle.velX, otherParticle.velY, angle);

            // Velocity after 1d collision equation
            final BVector2f v1 = new BVector2f(u1.getX() * (m1 - m2) / (m1 + m2) + u2.getX() * 2 * m2 / (m1 + m2),
                                               u1.getY());
            final BVector2f v2 = new BVector2f(u2.getX() * (m1 - m2) / (m1 + m2) + u1.getX() * 2 * m2 / (m1 + m2),
                                               u2.getY());

            // Final velocity after rotating axis back to original location
            final BVector2f vFinal1 = rotate(v1.getX(), v1.getY(), -angle);
            final BVector2f vFinal2 = rotate(v2.getX(), v2.getY(), -angle);

            // Swap particle velocities for realistic bounce effect
            particle.velX = vFinal1.getX();
            particle.velY = vFinal1.getY();

            otherParticle.velX = vFinal2.getX();
            otherParticle.velY = vFinal2.getY();
        }
    }

    @Override
    public void update(float delta) {
        barrels.forEach(barrel -> {
            barrel.move(delta);

            barrels.forEach(barrel2 -> {
                if (barrel2 != barrel) {
                    if (distance(barrel.posX,
                                 barrel.posY,
                                 barrel2.posX,
                                 barrel2.posY) < barrel.radius + barrel2.radius) {

                        resolveCollision(barrel, barrel2);
                    }
                }
            });

            barrel.checkBorders(parent.getCanvas());
        });
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());

        barrels.forEach(barrel -> barrel.fillArc(g2, null));
        mySimpleParticlePlayer.render(g2);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        mySimpleParticlePlayer.input();

        if (Input.getKeyDown(Keys.LCONTROL) || Input.isKeyDown(Keys.LSHIFT)) {
            SimpleParticle barrel = new SimpleParticle();
            barrel.posX = (float) (mySimpleParticlePlayer.posX + Math.cos(mySimpleParticlePlayer.angle) * mySimpleParticlePlayer.length);
            barrel.posY = (float) (mySimpleParticlePlayer.posY + Math.sin(mySimpleParticlePlayer.angle) * mySimpleParticlePlayer.length);

            final float BULLET_SPEED = 10f;
            barrel.velX = (float) (Math.cos(mySimpleParticlePlayer.angle) * BULLET_SPEED);
            barrel.velY = (float) (Math.sin(mySimpleParticlePlayer.angle) * BULLET_SPEED);
            barrel.color = Color.BLACK;
            barrel.radius = 5;
            barrels.add(barrel);
        }
    }
}
