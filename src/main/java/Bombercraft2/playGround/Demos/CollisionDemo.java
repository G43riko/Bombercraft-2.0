package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleParticle;
import Bombercraft2.playGround.Misc.SimplePlayer;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDemo extends GameState {
    private static GVector2f rotate(float x, float y, float angle) {
        return new GVector2f(x * Math.cos(angle) - y * Math.sin(angle),
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
            final GVector2f u1 = rotate(particle.velX, particle.velY, angle);
            final GVector2f u2 = rotate(otherParticle.velX, otherParticle.velY, angle);

            // Velocity after 1d collision equation
            final GVector2f v1 = new GVector2f(u1.getX() * (m1 - m2) / (m1 + m2) + u2.getX() * 2 * m2 / (m1 + m2),
                                               u1.getY());
            final GVector2f v2 = new GVector2f(u2.getX() * (m1 - m2) / (m1 + m2) + u1.getX() * 2 * m2 / (m1 + m2),
                                               u2.getY());

            // Final velocity after rotating axis back to original location
            final GVector2f vFinal1 = rotate(v1.getX(), v1.getY(), -angle);
            final GVector2f vFinal2 = rotate(v2.getX(), v2.getY(), -angle);

            // Swap particle velocities for realistic bounce effect
            particle.velX = vFinal1.getX();
            particle.velY = vFinal1.getY();

            otherParticle.velX = vFinal2.getX();
            otherParticle.velY = vFinal2.getY();
        }
    }

    private List<SimpleParticle> barrels = new ArrayList<>();
    private CorePlayGround parent;
    private SimplePlayer mySimplePlayer = new SimplePlayer();


    public CollisionDemo(CorePlayGround parent) {
        super(Type.CollisionDemo);
        this.parent = parent;

        mySimplePlayer.posX = 40;
        mySimplePlayer.posY = 40;
        mySimplePlayer.radius = 20;


        SimpleParticle barrel = new SimpleParticle();
        barrel.radius = 50;
        barrel.color = Color.GREEN;
        barrel.posX = 400;
        barrel.posY = 400;

        barrels.add(barrel);
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
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());

        barrels.forEach(barrel -> barrel.fillArc(g2, null));
        mySimplePlayer.render(g2);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        mySimplePlayer.input();

        if (Input.getKeyDown(Input.KEY_LCONTROL) || Input.isKeyDown(Input.KEY_LSHIFT)) {
            SimpleParticle barrel = new SimpleParticle();
            barrel.posX = (float) (mySimplePlayer.posX + Math.cos(mySimplePlayer.angle) * mySimplePlayer.length);
            barrel.posY = (float) (mySimplePlayer.posY + Math.sin(mySimplePlayer.angle) * mySimplePlayer.length);

            final float BULLET_SPEED = 10f;
            barrel.velX = (float) (Math.cos(mySimplePlayer.angle) * BULLET_SPEED);
            barrel.velY = (float) (Math.sin(mySimplePlayer.angle) * BULLET_SPEED);
            barrel.color = Color.BLACK;
            barrel.radius = 5;
            barrels.add(barrel);
        }
    }

    @Override
    public void doAct(GVector2f click) {

    }
}