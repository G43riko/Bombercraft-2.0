package org.bombercraft2.game.player;

import org.bombercraft2.StaticConfig;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.utils.enums.Keys;

import java.awt.*;

public class PlayerPointer {
    private final MyPlayer parent;
    private final float    rotSpeed = 0.1f;
    private final int      length   = 100;
    private final int      width    = 5;
    private final Color    color    = Color.white;
    private       float    angle    = 0;

    public PlayerPointer(MyPlayer parent) {
        this.parent = parent;
    }

    public GVector2f getEndPos(GVector2f positionStart) {
        GVector2f positionEnd = new GVector2f(positionStart);
        positionEnd.addToX((float) Math.cos(angle) * length);
        positionEnd.addToY((float) Math.sin(-angle) * length);
        return positionEnd;
    }

    public void render(Graphics2D g2) {
        GVector2f positionStart = parent.getPosition().getAdd(StaticConfig.BLOCK_SIZE_HALF.getSub(parent.getOffset()));
        GVector2f positionEnd = getEndPos(positionStart);

        g2.setStroke(new BasicStroke(width));
        g2.setColor(color);
        g2.drawLine(positionStart.getXi(), positionStart.getYi(), positionEnd.getXi(), positionEnd.getYi());
    }

    public void input() {
        double pi2 = Math.PI * 2;
        if (Input.isKeyDown(Keys.ARROW_LEFT)) {
            angle += rotSpeed;
            if (angle > pi2) {
                angle -= pi2;
            }
        }
        else if (Input.isKeyDown(Keys.ARROW_RIGHT)) {
            angle -= rotSpeed;
            if (angle < 0) {
                angle += pi2;
            }
        }
    }

    public void update(float delta) {

    }
}
