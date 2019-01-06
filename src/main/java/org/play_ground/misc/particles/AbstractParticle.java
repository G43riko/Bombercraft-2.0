package org.play_ground.misc.particles;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

import java.awt.*;

public class AbstractParticle extends Entity<SimpleGameAble> {
    @NotNull
    private final BVector2f direction;
    @NotNull
    private       BVector2f size;
    @NotNull
    private       Color     color;
    private       int       health;

    public AbstractParticle(@NotNull SimpleGameAble parent,
                            @NotNull ParticleInstanceData data
                           ) {
        super(data.position, parent);

        direction = data.direction;
        health = data.health;
        color = data.color;
        size = data.size;
    }


    @Override
    public void update(float delta) {
        position = position.getAdd(direction);
        alive = --health > 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void render(@NotNull Graphics2D g2) {
        BVector2f pos = parent.getManager().getViewManager().transform(position.getSub(size.getDiv(2)));
        BVector2f transformedSize = getTransformedSize();
        g2.setColor(color);
        g2.fillArc(pos.getXi(), pos.getYi(), transformedSize.getXi(), transformedSize.getYi(), 0, 360);
    }


    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Contract(pure = true)
    @NotNull
    public BVector2f getSize() {
        return size;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public BVector2f getPosition() {
        return position;
    }

}
