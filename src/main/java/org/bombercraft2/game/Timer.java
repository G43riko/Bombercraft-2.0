package org.bombercraft2.game;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

import java.awt.*;

public class Timer {
    private final static BVector2f TIMER_SIZE         = StaticConfig.BLOCK_SIZE.getMul(1.5f);
    private final static Color     TIMER_FILL_COLOR   = new Color(255, 0, 0, 100);
    private final static Color     TIMER_BORDER_COLOR = new Color(0, 0, 0, 255);
    private final static int       TIMER_BORDER_WIDTH = 3;

    private final long   startTime;
    private final long   duration;
    @NotNull
    private final Entity parent;
    private       long   angle = 0;

    public Timer(@NotNull Entity parent, long startTime, long duration) {
        this.parent = parent;
        this.startTime = startTime;
        this.duration = duration;
        // TODO Auto-generated constructor stub
    }

    public void render(@NotNull Graphics2D g2) {
        BVector2f parentPos = parent.getPosition().getSub(parent.getParent().getOffset()).getAdd(parent.getSize().getDiv(
                2));
        BVector2f realPos = parentPos.getSub(TIMER_SIZE.getDiv(2));

        g2.setColor(TIMER_FILL_COLOR);
        g2.fillArc(realPos.getXi(), realPos.getYi(), TIMER_SIZE.getXi(), TIMER_SIZE.getYi(), 90, (int) angle);

        g2.setStroke(new BasicStroke(TIMER_BORDER_WIDTH));
        g2.setColor(TIMER_BORDER_COLOR);
        g2.drawArc(realPos.getXi(), realPos.getYi(), TIMER_SIZE.getXi(), TIMER_SIZE.getYi(), 90, (int) angle);
    }

    public void update(float delta) {
        angle = (System.currentTimeMillis() - startTime) * 360 / duration;
    }

}
