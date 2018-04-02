package Bombercraft2.playGround.Misc.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.json.JSONObject;
import utils.math.GVector2f;

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
        position = position.add(direction);
        alive = --health > 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = parent.getManager().getViewManager().transform(position.sub(size.div(2)));
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
