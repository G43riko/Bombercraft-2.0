package Bombercraft2.playGround.Misc.particles;

import java.awt.*;

public class SimpleDrawableObject {
    public float posX;
    public float posY;
    public       float velX   = 0;
    public       float velY   = 0;
    public final float mass   = 1;
    public       int   radius = 20;
    public Color color;

    public void fillRect(Graphics2D g2, Color color) {
        g2.setColor(color == null ? this.color : color);
        fillRect(g2);
    }

    public void fillRect(Graphics2D g2) {
        g2.fillRect((int) (posX - radius),
                    (int) (posY - radius),
                    radius << 1,
                    radius << 1);
    }

    public void fillArc(Graphics2D g2, Color color) {
        g2.setColor(color == null ? this.color : color);
        fillArc(g2);
    }

    public void fillArc(Graphics2D g2) {
        g2.fillArc((int) (posX - radius),
                   (int) (posY - radius),
                   radius << 1,
                   radius << 1,
                   0,
                   360);
    }

    public void drawArc(Graphics2D g2, Color color) {
        g2.setColor(color == null ? this.color : color);
        drawArc(g2);
    }

    public void drawArc(Graphics2D g2) {
        g2.drawArc((int) (posX - radius),
                   (int) (posY - radius),
                   radius << 1,
                   radius << 1,
                   0,
                   360);
    }

    public void checkBorders(Canvas canvas) {
        if (posX < radius) {
            velX *= -1;
            posX = radius;
        }
        if (posY < radius) {
            velY *= -1;
            posY = radius;
        }
        if (posX + radius > canvas.getWidth()) {
            velX *= -1;
            posX = canvas.getWidth() - radius;
        }
        if (posY + radius > canvas.getHeight()) {
            velY *= -1;
            posY = canvas.getHeight() - radius;
        }
    }

    public void move(float delta) {
        posX += velX * delta;
        posY += velY * delta;
    }
}