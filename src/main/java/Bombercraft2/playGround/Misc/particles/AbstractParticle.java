package Bombercraft2.playGround.Misc.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class AbstractParticle implements Visible, InteractAble {
    private final SimpleGameAble parent;
    private final GVector2f      direction;
    private       GVector2f      position;
    private       GVector2f      size;
    private       boolean        alive;
    private       Color          color;
    private       int            health;

    public AbstractParticle(@NotNull SimpleGameAble parent,
                            @NotNull ParticleInstanceData data
                           ) {
        this.parent = parent;

        direction = data.direction;
        position = data.position;
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
        GVector2f pos = position.sub(size.div(2)).mul(parent.getZoom()).sub(parent.getOffset());
        GVector2f totalSize = size.mul(parent.getZoom());
        g2.setColor(color);
        g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
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
