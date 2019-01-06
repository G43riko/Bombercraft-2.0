package org.bombercraft2.game.entity.particles;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

import java.awt.*;

public class Particle extends Entity<SimpleGameAble> {
    private final BVector2f direction;
    private final BVector2f size;
    private final Color     color;
    private       int       health;


    public Particle(BVector2f position,
                    SimpleGameAble parent,
                    Color color,
                    BVector2f direction,
                    BVector2f size,
                    int health
                   ) {
        super(position, parent);
        this.direction = direction;
        this.color = color;
        this.health = health;
        this.size = size;
    }


    @Override
    public void update(float delta) {
        position = position.getAdd(direction.getMul(getParent().getZoom()));
        if (--health <= 0) {
            alive = false;
        }

    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        BVector2f pos = position.getSub(size.getDiv(2)).getMul(getParent().getZoom()).getSub(getParent().getOffset());
        BVector2f totalSize = size.getMul(getParent().getZoom());
        g2.setColor(color);
        g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public BVector2f getPosition() {
        return super.getPosition().getDiv(getParent().getZoom());
    }


    @Contract(pure = true)
    @NotNull
    public BVector2f getSize() {return size;}

}
