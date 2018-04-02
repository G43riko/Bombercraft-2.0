package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class AbstractDrawable {
    protected       float phase = 0;
    protected final float speed = Config.PATH_SPEED;
    @NotNull
    protected       Color color = Config.PATH_FILL_COLOR;
    @NotNull
    protected SimpleGameAble parent;

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
