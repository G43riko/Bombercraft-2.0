package org.bombercraft2.gui2.utils;

import org.bombercraft2.core.Visible;
import org.bombercraft2.gui2.core.PositionableComponent;

import java.awt.*;

public class ColorBox {
    protected Color borderColor     = Color.BLACK;
    protected Color backgroundColor = Color.WHITE;
    protected int   borderWidth     = 0;

    public ColorBox(Color backgroundColor, Color borderColor, int borderWidth) {
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.borderWidth = borderWidth;
    }

    public ColorBox(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public ColorBox(Color borderColor, int borderWidth) {
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public ColorBox() { }

    public void render(Graphics2D g2, int x, int y, int width, int height) {
        renderBackground(g2, x, y, width, height);
        renderBorder(g2, x, y, width, height);
    }

    public void render(Graphics2D g2, PositionableComponent target) {
        renderBackground(g2, target);
        renderBorder(g2, target);
    }

    public void renderBorder(Graphics2D g2, PositionableComponent target) {
        renderBorder(g2, target.getX(), target.getY(), target.getWidth(), target.getHeight());
    }

    public void renderBorder(Graphics2D g2, Visible target) {
        renderBorder(g2,
                     target.getPosition().getXi(),
                     target.getPosition().getYi(),
                     target.getSize().getXi(),
                     target.getSize().getYi());
    }

    public void renderBorder(Graphics2D g2, int x, int y, int width, int height) {
        if (borderWidth <= 0) {
            return;
        }


        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRect(x, y, width, height);
    }

    public void renderBackground(Graphics2D g2, PositionableComponent target) {
        renderBackground(g2, target.getX(), target.getY(), target.getWidth(), target.getHeight());
    }

    public void renderBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(backgroundColor);
        g2.fillRect(x, y, width, height);
    }


    public Color getBorderColor() { return borderColor; }

    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    public Color getBackgroundColor() { return backgroundColor; }

    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }

    public int getBorderWidth() { return borderWidth; }

    public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }
}
