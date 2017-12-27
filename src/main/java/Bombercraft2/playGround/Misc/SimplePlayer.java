package Bombercraft2.playGround.Misc;

import Bombercraft2.engine.Input;

import java.awt.*;

public class SimplePlayer extends SimpleParticle {
    public float   angle        = 0;
    public int     speed        = 1;
    public float   length       = 60;
    public boolean lookForMouse = true;

    public void render(Graphics2D g2) {
        fillArc(g2, Color.white);
        drawArc(g2, Color.black);

        g2.drawLine((int) (posX),
                    (int) (posY),
                    (int) (posX + Math.cos(angle) * length),
                    (int) (posY + Math.sin(angle) * length));
    }

    public void input() {
        if (Input.isKeyDown(Input.KEY_A)) { posX -= speed; }
        if (Input.isKeyDown(Input.KEY_D)) { posX += speed; }
        if (Input.isKeyDown(Input.KEY_S)) { posY += speed; }
        if (Input.isKeyDown(Input.KEY_W)) { posY -= speed; }

        if (lookForMouse) {
            angle = (float)Math.atan2(Input.getMousePosition().getY() - posY, Input.getMousePosition().getX() - posX);
        }
        else {
            if (Input.isKeyDown(Input.KEY_Q)) { angle -= ((float) speed) / 10; }
            if (Input.isKeyDown(Input.KEY_E)) { angle += ((float) speed) / 10; }
        }
    }
}
