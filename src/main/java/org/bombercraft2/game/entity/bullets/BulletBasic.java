package org.bombercraft2.game.entity.bullets;

import org.bombercraft2.game.GameAble;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BulletBasic extends Bullet {
    private final int offset = 10;
    private final int round  = 15;

    public BulletBasic(GVector2f position, GameAble parent, GVector2f direction) {
        super(position, parent, BulletManager.Types.BASIC, direction);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = position.getSub(getParent().getOffset()).getAdd(offset);

        g2.setColor(getColor());
        g2.fillRoundRect(pos.getXi(), pos.getYi(), getSize().getXi(), getSize().getYi(), round, round);
    }

}
