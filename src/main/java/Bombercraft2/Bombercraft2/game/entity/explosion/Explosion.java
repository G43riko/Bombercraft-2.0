package Bombercraft2.Bombercraft2.game.entity.explosion;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.particles.Particle;
import org.json.JSONObject;
import utils.SpriteAnimation;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Explosion extends Entity {
    private ArrayList<Shockwave>       waves      = new ArrayList<>();
    private ArrayList<Particle>        particles  = new ArrayList<>();
    private ArrayList<SpriteAnimation> animations = new ArrayList<>();
    private GVector2f                  size       = null;

    public Explosion(GVector2f position,
                     GameAble parent,
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
            waves.add(new Shockwave(getParent(), position, 120, 5, 5, Color.yellow, true));
        }
        if (explosion) {
            animations.add(new SpriteAnimation("explosion1.png", 5, 5, 2));
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
    public void render(Graphics2D g2) {
        waves.forEach(a -> a.render(g2));
        particles.forEach(a -> a.render(g2));
        animations = new ArrayList<>(animations).stream()
                                                .filter(a -> !drawExplosion(g2, a))
                                                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean drawExplosion(Graphics2D g2, SpriteAnimation sprite) {
        GVector2f pos = position.sub(getSize()).mul(getParent().getZoom()).sub(getParent().getOffset());
        return sprite.renderAndCheckLastFrame(g2, pos, getSize().mul(2).mul(getParent().getZoom()));
    }

    @Override
    public void update(float delta) {
        waves = new ArrayList<>(waves).stream()
                                      .filter(Shockwave::isAlive)
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

    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GVector2f getSize() {
        return size;
    }

}
