package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

import java.awt.*;

public class Timer {
    public final static GVector2f TIMER_SIZE         = Block.SIZE.mul(1.5f);
    public final static Color     TIMER_FILL_COLOR   = new Color(255, 0, 0, 100);
    public final static Color     TIMER_BORDER_COLOR = new Color(0, 0, 0, 255);
    public final static int       TIMER_BORDER_WIDTH = 3;

    long startTime = 0;
    long duration  = 0;
    long angle     = 0;
    private Entity parent;

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
