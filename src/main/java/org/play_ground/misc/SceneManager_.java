package org.play_ground.misc;

import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.entity.explosion.Explosion;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SceneManager_ extends AbstractManager {
    @NotNull
    private final List<SimpleBomb>  bombs      = new ArrayList<>();
    @NotNull
    private final ArrayList<Entity> explosions = new ArrayList<>();
    @NotNull
    private final SimpleGameAble    parent;

    private int renderedBombs = 0;
    private int createdBombs  = 0;

    public SceneManager_(@NotNull SimpleGameAble parent) {
        this.parent = parent;
    }


    public void render(@NotNull Graphics2D g2) {
        renderedBombs = 0;
        bombs.stream().filter(parent::isVisible).forEach(bomb -> {
            bomb.render(g2);
            renderedBombs++;
        });
        explosions.forEach(explosion -> explosion.render(g2));
    }

    public void addBomb(SimpleBomb bomb) {
        bombs.add(bomb);
        createdBombs++;
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    public void update(float delta) {
        bombs.removeIf(Entity::isDeath);
        bombs.forEach(bomb -> bomb.update(delta));

        explosions.removeIf(Entity::isDeath);
        explosions.forEach(explosion -> explosion.update(delta));
    }

    @Override
    @NotNull
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("Bombs: " + renderedBombs + " / " + bombs.size() + " / " + createdBombs);
        return result;
    }

    public void reset() {
        bombs.clear();
        createdBombs = 0;
        renderedBombs = 0;
    }

}
