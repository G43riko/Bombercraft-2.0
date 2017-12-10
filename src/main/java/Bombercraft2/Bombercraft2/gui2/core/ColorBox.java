package Bombercraft2.Bombercraft2.gui2.core;

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
        g2.drawRect(target.getX(), target.getY(), target.getWidth(), target.getHeight());
    }

    public void renderBackground(Graphics2D g2, PositionableComponent target) {
        g2.setColor(backgroundColor);
        g2.fillRect(target.getX(), target.getY(), target.getWidth(), target.getHeight());
    }


    public Color getBorderColor() { return borderColor; }

    public Color getBackgroundColor() { return backgroundColor; }

    public int getBorderWidth() { return borderWidth; }

    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }

    public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }
}
