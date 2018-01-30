package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BasicDrawablePath extends AbstractDrawable {
    @NotNull
    private List<GVector2f> path;

    public BasicDrawablePath(@NotNull SimpleGameAble parent, @NotNull List<GVector2f> points) {
        super(parent);
        path = points.stream().map(a -> a.mul(Config.BLOCK_SIZE)
                                         .add(Config.BLOCK_SIZE_HALF)).collect(Collectors.toList());
    }

    public void render(@NotNull Graphics2D g2) {
        final int[] surX = new int[path.size()];
        final int[] surY = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            final GVector2f actPoint = path.get(i)
                                           .mul(parent.getZoom())
                                           .sub(parent.getOffset());
            surX[i] = actPoint.getXi();
            surY[i] = actPoint.getYi();
        }

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(parent.getZoom()));
        g2.drawPolyline(surX, surY, path.size());

        g2.setColor(color);
        g2.setStroke(new BasicStroke(6 * parent.getZoom(),
                                     BasicStroke.CAP_ROUND,
                                     BasicStroke.JOIN_BEVEL,
                                     0,
                                     new float[]{90 * parent.getZoom()},
                                     phase));
        g2.drawPolyline(surX, surY, path.size());
    }

}
