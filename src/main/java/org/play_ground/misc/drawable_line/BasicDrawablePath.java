package org.play_ground.misc.drawable_line;

import org.bombercraft2.StaticConfig;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BasicDrawablePath extends AbstractDrawable {
    @NotNull
    private List<GVector2f> path;

    public BasicDrawablePath(@NotNull SimpleGameAble parent, @NotNull List<GVector2f> points) {
        super(parent);
        path = points.stream().map(a -> a.getMul(StaticConfig.BLOCK_SIZE)
                .getAdd(StaticConfig.BLOCK_SIZE_HALF)).collect(Collectors.toList());
    }

    public void render(@NotNull Graphics2D g2) {
        final int[] surX = new int[path.size()];
        final int[] surY = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            final GVector2f actPoint = parent.getManager().getViewManager().transform(path.get(i));
            surX[i] = actPoint.getXi();
            surY[i] = actPoint.getYi();
        }

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(parent.getManager().getViewManager().getZoom()));
        g2.drawPolyline(surX, surY, path.size());

        g2.setColor(color);
        g2.setStroke(new BasicStroke(6 * parent.getManager().getViewManager().getZoom(),
                                     BasicStroke.CAP_ROUND,
                                     BasicStroke.JOIN_BEVEL,
                                     0,
                                     new float[]{90 * parent.getManager().getViewManager().getZoom()},
                                     phase));
        g2.drawPolyline(surX, surY, path.size());
    }

}
