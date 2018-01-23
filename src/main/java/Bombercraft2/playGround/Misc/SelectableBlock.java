package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.game.level.Block;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

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
        final GVector2f size = Block.SIZE.mul(parent.getZoom());
        final GVector2f realPos = position.mul(size);
        GVector2f pos = (offset == null ? realPos : realPos.add(offset.mul(parent.getZoom()))).sub(parent.getOffset());

        g2.setColor(selected ? selectedColor : defaultColor);
        g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }
}
