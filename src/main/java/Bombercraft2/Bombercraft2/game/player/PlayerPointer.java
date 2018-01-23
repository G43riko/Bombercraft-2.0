package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

import java.awt.*;

class PlayerPointer {
    private final MyPlayer parent;
    private       float    angle    = 0;
    private final float    rotSpeed = 0.1f;
    private final int      length   = 100;
    private final int      width    = 5;
    private final Color    color    = Color.white;

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
        GVector2f positionStart = parent.getPosition().add(Block.SIZE.div(2).sub(parent.getOffset()));
        GVector2f positionEnd = getEndPos(positionStart);

        g2.setStroke(new BasicStroke(width));
        g2.setColor(color);
        g2.drawLine(positionStart.getXi(), positionStart.getYi(), positionEnd.getXi(), positionEnd.getYi());
    }

    public void input() {
        double pi2 = Math.PI * 2;
        if (Input.isKeyDown(Input.KEY_ARROW_LEFT)) {
            angle += rotSpeed;
            if (angle > pi2) {
                angle -= pi2;
            }
        }
        else if (Input.isKeyDown(Input.KEY_ARROW_RIGHT)) {
            angle -= rotSpeed;
            if (angle < 0) {
                angle += pi2;
            }
        }
    }

    public void update(float delta) {

    }
}
