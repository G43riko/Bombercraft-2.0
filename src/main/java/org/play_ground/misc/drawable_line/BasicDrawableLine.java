package org.play_ground.misc.drawable_line;

import org.bombercraft2.StaticConfig;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public class BasicDrawableLine extends AbstractDrawable {
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
        final GVector2f a = parent.getManager().getViewManager().transform(getStartPos());
        final GVector2f b = parent.getManager().getViewManager().transform(getEndPos());
        g2.setColor(StaticConfig.PATH_BORDER_COLOR);
        g2.setStroke(new BasicStroke(StaticConfig.PATH_NORMAL_WIDTH * parent.getManager().getViewManager().getZoom()));
        g2.drawLine(a.getXi(), a.getYi(), b.getXi(), b.getYi());

        g2.setColor(color);
        g2.setStroke(new BasicStroke(StaticConfig.PATH_BOLD_WIDTH * parent.getManager().getViewManager().getZoom(),
                                     BasicStroke.CAP_ROUND,
                                     BasicStroke.JOIN_BEVEL,
                                     0,
                                     new float[]{StaticConfig.PATH_DASH_GAP * parent.getManager()
                                                                                    .getViewManager()
                                                                                    .getZoom()},
                                     phase));
        g2.drawLine(b.getXi(), b.getYi(), a.getXi(), a.getYi());
    }
}
