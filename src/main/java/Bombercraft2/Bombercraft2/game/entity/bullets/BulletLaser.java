package Bombercraft2.Bombercraft2.game.entity.bullets;

import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

import java.awt.*;

public class BulletLaser extends Bullet {
    int width = 3;

    public BulletLaser(GVector2f position, GameAble parent, GVector2f direction) {
        super(position, parent, BulletManager.Types.LASER, direction);
    }

    @Override
    public void render(Graphics2D g2) {
        GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
        GVector2f pos2 = pos.sub(getDirection().mul(getSpeed()));

        g2.setColor(getColor());
        g2.setStroke(new BasicStroke(width));
        g2.drawLine(pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi());

    }
}
