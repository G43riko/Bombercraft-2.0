package Bombercraft2.Bombercraft2.game.entity.bullets;

import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

import java.awt.*;

public class BulletBasic extends Bullet {
    private int offset = 10;
    private int round  = 15;

    public BulletBasic(GVector2f position, GameAble parent, GVector2f direction) {
        super(position, parent, BulletManager.Types.BASIC, direction);
    }

    @Override
    public void render(Graphics2D g2) {
        GVector2f pos = position.sub(getParent().getOffset()).add(offset);

        g2.setColor(getColor());
        g2.fillRoundRect(pos.getXi(), pos.getYi(), getSize().getXi(), getSize().getYi(), round, round);
    }

}
