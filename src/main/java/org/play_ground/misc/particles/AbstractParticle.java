package org.play_ground.misc.particles;

import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public class AbstractParticle extends Entity<SimpleGameAble> {
    @NotNull
    private final GVector2f direction;
    @NotNull
    private       GVector2f size;
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
        GVector2f pos = parent.getManager().getViewManager().transform(position.getSub(size.getDiv(2)));
        GVector2f transformedSize = getTransformedSize();
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
    public GVector2f getSize() {
        return size;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {
        return position;
    }

}
