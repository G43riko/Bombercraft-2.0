package org.bombercraft2.components.health_bar;

import org.bombercraft2.StaticConfig;
import org.glib2.interfaces.HealthAble;
import org.glib2.math.vectors.GVector2f;
import org.gui.utils.ColorBox;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public class HealthBar {
    private final HealthAble     target;
    private final SimpleGameAble parent;
    private final ColorBox       colorBoxFill    = new ColorBox(StaticConfig.HEALTH_BAR_FILL_COLOR);
    private final ColorBox       colorBoxOutline = new ColorBox(StaticConfig.HEALTH_BAR_BORDER_COLOR,
                                                                StaticConfig.HEALTH_BAR_BORDER_WIDTH);

    public HealthBar(@NotNull HealthAble target, @NotNull SimpleGameAble parent) {
        this.parent = parent;
        this.target = target;
    }

    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = target.getPosition().getMul(parent.getZoom()).getSub(parent.getOffset());
        final int maxWidth = (int) ((target.getSize()
                                           .getXi() - (StaticConfig.HEALTH_BAR_HORIZONTAL_OFFSET << 1)) * parent.getZoom());
        final int height = (int) (StaticConfig.HEALTH_BAR_HORIZONTAL_HEIGHT * parent.getZoom());

/*
        g2.setStroke(new BasicStroke(StaticConfig.HEALTH_BAR_BORDER_WIDTH));
        g2.setColor(StaticConfig.HEALTH_BAR_BORDER_COLOR);
        g2.drawRect(pos.getXi() + StaticConfig.HEALTH_BAR_HORIZONTAL_OFFSET,
                    pos.getYi() + StaticConfig.HEALTH_BAR_VERTICAL_OFFSET,
                    maxWidth,
                    height);


        g2.setColor(StaticConfig.HEALTH_BAR_FILL_COLOR);
        g2.fillRect(pos.getXi() + StaticConfig.HEALTH_BAR_HORIZONTAL_OFFSET,
                    pos.getYi() + StaticConfig.HEALTH_BAR_VERTICAL_OFFSET,
                    target.getHealth() * maxWidth / target.getMaxHealth(),
                    height);

        */
        colorBoxOutline.renderBorder(g2,
                                     pos.getXi() + StaticConfig.HEALTH_BAR_HORIZONTAL_OFFSET,
                                     pos.getYi() + StaticConfig.HEALTH_BAR_VERTICAL_OFFSET,
                                     maxWidth,
                                     height);


        colorBoxFill.renderBackground(g2,
                                      pos.getXi() + StaticConfig.HEALTH_BAR_HORIZONTAL_OFFSET,
                                      pos.getYi() + StaticConfig.HEALTH_BAR_VERTICAL_OFFSET,
                                      target.getHealth() * maxWidth / target.getMaxHealth(),
                                      height);
    }

}
