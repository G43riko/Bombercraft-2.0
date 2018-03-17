package Bombercraft2.Bombercraft2.gui2.core;


import Bombercraft2.Bombercraft2.gui2.utils.ColorBox;

import java.awt.*;

public final class RoundedColorBox extends ColorBox {
    private int roundX = 5;
    private int roundY = 5;


    public void render(Graphics2D g2, PositionableComponent target) {
        renderBackground(g2, target);
        renderBorder(g2, target);
    }

    public void renderBorder(Graphics2D g2, PositionableComponent target) {
        if (borderWidth <= 0) {
            return;
        }
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(target.getX(), target.getY(), target.getWidth(), target.getHeight(), roundX, roundY);
    }

    public void renderBackground(Graphics2D g2, PositionableComponent target) {
        g2.setColor(backgroundColor);
        g2.fillRoundRect(target.getX(), target.getY(), target.getWidth(), target.getHeight(), roundX, roundY);
    }

    public int getRoundX() {return roundX;}

    public int getRoundY() {return roundY;}

    public void setRoundX(int roundX) {this.roundX = roundX;}

    public void setRoundY(int roundY) {this.roundY = roundY;}
}
