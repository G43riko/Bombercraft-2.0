package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class BasicDrawableLine extends AbstractDrawable{
    @NotNull
    protected GVector2f start = new GVector2f();
    @NotNull
    protected GVector2f end   = new GVector2f();

    public BasicDrawableLine(SimpleGameAble parent) {
        super(parent);
    }

    public BasicDrawableLine(SimpleGameAble parent, @NotNull GVector2f start, @NotNull GVector2f end) {
        super(parent);
        this.start = start;
        this.end = end;
    }

    @NotNull
    protected GVector2f getStartPos() {
        return start;
    }

    @NotNull
    protected GVector2f getEndPos() {
        return end;
    }

    public void render(@NotNull Graphics2D g2) {
        final GVector2f a = getStartPos().mul(parent.getZoom()).sub(parent.getOffset());
        final GVector2f b = getEndPos().mul(parent.getZoom()).sub(parent.getOffset());
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
}
