package org.bombercraft2.game.entity.bullets;

import org.bombercraft2.game.GameAble;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BulletLaser extends Bullet {
    private final int width = 3;

    public BulletLaser(GVector2f position, GameAble parent, GVector2f direction) {
        super(position, parent, BulletManager.Types.LASER, direction);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = position.getMul(getParent().getZoom()).getSub(getParent().getOffset());
        GVector2f pos2 = pos.getSub(getDirection().getMul(getSpeed()));

        g2.setColor(getColor());
        g2.setStroke(new BasicStroke(width));
        g2.drawLine(pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi());

    }
}
