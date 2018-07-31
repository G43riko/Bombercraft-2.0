package org.play_ground.misc;

import org.engine.Input;
import org.play_ground.misc.particles.SimpleParticle;
import org.utils.enums.Keys;

import java.awt.*;

public class SimpleParticlePlayer extends SimpleParticle {
    public final int     speed        = 1;
    public final float   length       = 60;
    public final boolean lookForMouse = true;
    public       float   angle        = 0;

    public void render(Graphics2D g2) {
        fillArc(g2, Color.white);
        drawArc(g2, Color.black);

        g2.drawLine((int) (posX),
                    (int) (posY),
                    (int) (posX + Math.cos(angle) * length),
                    (int) (posY + Math.sin(angle) * length));
    }

    public void input() {
        if (Input.isKeyDown(Keys.A)) { posX -= speed; }
        if (Input.isKeyDown(Keys.D)) { posX += speed; }
        if (Input.isKeyDown(Keys.S)) { posY += speed; }
        if (Input.isKeyDown(Keys.W)) { posY -= speed; }

        if (lookForMouse) {
            angle = (float) Math.atan2(Input.getMousePosition().getY() - posY, Input.getMousePosition().getX() - posX);
        }
        else {
            if (Input.isKeyDown(Keys.Q)) { angle -= ((float) speed) / 10; }
            if (Input.isKeyDown(Keys.E)) { angle += ((float) speed) / 10; }
        }
    }
}
