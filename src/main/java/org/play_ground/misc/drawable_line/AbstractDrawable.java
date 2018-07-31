package org.play_ground.misc.drawable_line;

import org.bombercraft2.StaticConfig;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public abstract class AbstractDrawable {
    protected final float          speed = StaticConfig.PATH_SPEED;
    protected       float          phase = 0;
    @NotNull
    protected       Color          color = StaticConfig.PATH_FILL_COLOR;
    @NotNull
    protected       SimpleGameAble parent;

    protected AbstractDrawable(@NotNull SimpleGameAble parent) {
        this.parent = parent;
    }

    public void update(float delta) {
        phase += speed * delta * parent.getManager().getViewManager().getZoom();
    }

    @Contract(pure = true)
    public float getSpeed() {
        return speed;
    }

    @NotNull
    @Contract(pure = true)
    public Color getColor() {
        return color;
    }
}
