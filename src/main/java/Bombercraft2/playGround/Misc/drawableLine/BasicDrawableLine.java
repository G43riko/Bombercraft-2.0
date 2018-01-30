package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

import java.awt.*;

public class BasicDrawableLine {
    private       float phase = 0;
    private final float speed = 3f;
    private       Color color = Color.GREEN;
    private SimpleGameAble parent;
    @NotNull
    protected GVector2f start = new GVector2f();
    @NotNull
    protected GVector2f end   = new GVector2f();

    public BasicDrawableLine(SimpleGameAble parent) {
        this.parent = parent;
    }

    public BasicDrawableLine(SimpleGameAble parent, @NotNull GVector2f start, @NotNull GVector2f end) {
        this.start = start;
        this.end = end;
        this.parent = parent;
    }

    @NotNull
    protected GVector2f getStartPos() {
        return start;
    }

    @NotNull
    protected GVector2f getEndPos() {
        return end;
    }

    public void render(@NotNull Graphics2D g2, float zoom) {
        final GVector2f a = getStartPos().mul(zoom).sub(parent.getOffset());
        final GVector2f b = getEndPos().mul(zoom).sub(parent.getOffset());
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(parent.getZoom()));
        g2.drawLine(a.getXi(), a.getYi(), b.getXi(), b.getYi());

        g2.setColor(color);
        g2.setStroke(new BasicStroke(6 * parent.getZoom(),
                                     BasicStroke.CAP_ROUND,
                                     BasicStroke.JOIN_BEVEL,
                                     0,
                                     new float[]{90 * parent.getZoom()},
                                     phase));
        g2.drawLine(b.getXi(), b.getYi(), a.getXi(), a.getYi());
    }

    public void update(float delta) {
        phase += speed * delta * parent.getZoom();
    }

    public float getSpeed() {
        return speed;
    }

    public Color getColor() {
        return color;
    }
}
