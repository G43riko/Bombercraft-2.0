package org.bombercraft2.game.entity.explosion;

import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.entity.particles.Particle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;
import org.utils.SpriteAnimation;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Explosion extends Entity<SimpleGameAble> {
    private static Image                      explosionImage = ResourceLoader.loadTexture("explosion1.png");
    private final  GVector2f                  size;
    private        ArrayList<ShockWave>       waves          = new ArrayList<>();
    private        ArrayList<Particle>        particles      = new ArrayList<>();
    private        ArrayList<SpriteAnimation> animations     = new ArrayList<>();

    public Explosion(GVector2f position,
                     SimpleGameAble parent,
                     GVector2f size,
                     Color color,
                     int number,
                     boolean explosion,
                     boolean shockWave
                    ) {
        super(position, parent);

        this.size = size;

        createParticles(color, number);
        if (shockWave) {
            final int maxRadius = 100 + (int) (Math.random() * 50);
            final int stroke = 4 + (int) (Math.random() * 7);
            final int speed = 3 + (int) (Math.random() * 6);
            waves.add(new ShockWave(getParent(), position, maxRadius, stroke, speed, Color.yellow, true));
        }
        if (explosion) {
            animations.add(new SpriteAnimation(explosionImage, 5, 5, 2));
        }
    }

    private void createParticles(Color color, int number) {
        GVector2f particleSize = size.div(number);
        int particlesCount = number / 2;
        for (int i = -particlesCount; i < particlesCount; i++) {
            for (int j = -particlesCount; j < particlesCount; j++) {
                GVector2f dir = new GVector2f(i, j);
                particles.add(new Particle(position.add(particleSize.mul(dir)),
                                           getParent(),
                                           color,
                                           dir.add(new GVector2f(Math.random(), Math.random()).sub(0.5f).Normalized()),
                                           particleSize.mul((float) (Math.random() * 0.4f - 0.2f) + 1.0f),
                                           40 + (int) (Math.random() * 30) - 15));
            }
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        waves.forEach(a -> a.render(g2));
        particles.forEach(a -> a.render(g2));
        animations = new ArrayList<>(animations).stream()
                                                .filter(a -> !drawExplosion(g2, a))
                                                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean drawExplosion(Graphics2D g2, SpriteAnimation sprite) {
        GVector2f pos = position.sub(getSize()).mul(getParent().getZoom()).sub(getParent().getOffset());
        GVector2f size = getSize().mul(2).mul(getParent().getZoom());

        return sprite.renderAndCheckLastFrame(g2,
                                              new org.glib2.math.vectors.GVector2f(pos.getX(), pos.getY()),
                                              new org.glib2.math.vectors.GVector2f(size.getX(), size.getY()));
    }

    @Override
    public void update(float delta) {
        waves = new ArrayList<>(waves).stream()
                                      .filter(ShockWave::isAlive)
                                      .peek(a -> a.update(delta))
                                      .collect(Collectors.toCollection(ArrayList::new));

        particles = new ArrayList<>(particles).stream()
                                              .filter(Entity::isAlive)
                                              .peek(a -> a.update(delta))
                                              .collect(Collectors.toCollection(ArrayList::new));

        if (waves.isEmpty() && particles.isEmpty() && animations.isEmpty()) {
            alive = false;
        }
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return size;
    }

}
