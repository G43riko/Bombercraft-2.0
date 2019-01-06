package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.misc.GCanvas;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public class SelectableBlock extends AbstractBlock {
    public boolean selected      = true;
    public Color   defaultColor  = Color.LIGHT_GRAY;
    public Color   selectedColor = Color.DARK_GRAY;

    public SelectableBlock(@NotNull GVector2f position,
                           @NotNull SimpleGameAble parent
                          ) {
        super(position, parent);
    }

    public void render(@NotNull Graphics2D g2) {
        final GVector2f size = StaticConfig.BLOCK_SIZE.getMul(parent.getManager().getViewManager().getZoom());
        final GVector2f realPos = position.getMul(size);
        GVector2f pos = (offset == null ? realPos : realPos.getAdd(offset.getMul(parent.getManager()
                                                                                 .getViewManager()
                                                                                         .getZoom()))).getSub(parent.getManager()
                                                                                                         .getViewManager()
                                                                                                         .getOffset());

        GCanvas.fillRect(g2, pos, size, selected ? selectedColor : defaultColor);
        // g2.setColor(selected ? selectedColor : defaultColor);
        // g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());


        GCanvas.drawRect(g2, pos, size, Color.BLACK, 3);
        // g2.setColor(Color.black);
        // g2.setStroke(new BasicStroke(3));
        // g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }
}
