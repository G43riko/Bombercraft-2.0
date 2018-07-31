package org.play_ground.misc.particles;

import org.bombercraft2.core.Visible;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class SimpleParticle implements Visible {
    public final float mass   = 1;
    public       float velX   = 0;
    public       float velY   = 0;
    public       int   radius = 20;
    public       float posX;
    public       float posY;
    public       Color color;

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

    @Override
    public @NotNull GVector2f getPosition() {
        return new GVector2f(posX, posY);
    }

    @Override
    public @NotNull GVector2f getSize() {
        return new GVector2f(radius, radius);
    }
}
