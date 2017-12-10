package Bombercraft2.Bombercraft2.gui2.core;

import java.awt.*;

public class ColorBox {
    Color borderColor     = Color.BLACK;
    Color backgroundColor = Color.WHITE;
    int   borderWidth     = 0;

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

    public  void render(Graphics2D g2, int x, int y, int width, int height) {
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
    private void renderBorder(Graphics2D g2, int x, int y, int width, int height) {
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
    private void renderBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(backgroundColor);
        g2.fillRect(x, y, width, height);
    }


    public Color getBorderColor() { return borderColor; }

    public Color getBackgroundColor() { return backgroundColor; }

    public int getBorderWidth() { return borderWidth; }

    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }

    public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }
}
