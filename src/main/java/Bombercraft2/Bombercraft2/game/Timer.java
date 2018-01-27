package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import utils.math.GVector2f;

import java.awt.*;

public class Timer {
    private final static GVector2f TIMER_SIZE         = Config.BLOCK_SIZE.mul(1.5f);
    private final static Color     TIMER_FILL_COLOR   = new Color(255, 0, 0, 100);
    private final static Color     TIMER_BORDER_COLOR = new Color(0, 0, 0, 255);
    private final static int       TIMER_BORDER_WIDTH = 3;

    private final long startTime;
    private final long duration;
    private long angle     = 0;
    private final Entity parent;

    public Timer(Entity parent, long startTime, long duration) {
        this.parent = parent;
        this.startTime = startTime;
        this.duration = duration;
        // TODO Auto-generated constructor stub
    }

    public void render(Graphics2D g2) {
        GVector2f parentPos = parent.getPosition().sub(parent.getParent().getOffset()).add(parent.getSize().div(2));
        GVector2f realPos = parentPos.sub(TIMER_SIZE.div(2));

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
