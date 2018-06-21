package org.bombercraft2.gui2.core;

import java.awt.*;

public class TextAlignment {
    public static final byte HORIZONTAL_ALIGN_LEFT   = 0;
    public static final byte HORIZONTAL_ALIGN_CENTER = 1;
    public static final byte HORIZONTAL_ALIGN_RIGHT  = 2;
    public static final byte VERTICAL_ALIGN_TOP      = 3;
    public static final byte VERTICAL_ALIGN_MIDDLE   = 4;
    public static final byte VERTICAL_ALIGN_BOTTOM   = 5;

    private byte verticalAlign    = VERTICAL_ALIGN_MIDDLE;
    private byte horizontalAlign  = HORIZONTAL_ALIGN_CENTER;
    private int  verticalOffset   = 0;
    private int  horizontalOffset = 0;

    public TextAlignment() { }

    public TextAlignment(byte verticalAlign, byte horizontalAlign) {
        this.verticalAlign = verticalAlign;
        this.horizontalAlign = horizontalAlign;
    }

    public TextAlignment(byte verticalAlign, byte horizontalAlign, int verticalOffset, int horizontalOffset) {
        this.verticalAlign = verticalAlign;
        this.horizontalAlign = horizontalAlign;
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
    }

    public void renderText(Graphics2D g2, TextField text, PositionableComponent target) {
        renderText(g2, text, target, 0, 0);
    }

    public void renderText(Graphics2D g2,
                           TextField text,
                           PositionableComponent target,
                           int localVerticalOffset,
                           int localHorizontalOffset
                          ) {
        g2.setFont(text.getFont());
        g2.setColor(text.getColor());
        int x = target.getX() + localHorizontalOffset;
        int y = target.getY() + text.getSize() + localVerticalOffset;
        final int textWidth = g2.getFontMetrics().stringWidth(text.getText());

        switch (horizontalAlign) {
            case HORIZONTAL_ALIGN_CENTER:
                x += (target.getWidth() - textWidth) >> 1;
                x += horizontalOffset;
                break;
            case HORIZONTAL_ALIGN_LEFT:
                x += horizontalOffset;
                break;
            case HORIZONTAL_ALIGN_RIGHT:
                x += target.getWidth() - textWidth - horizontalOffset;
                break;
        }

        if (verticalAlign == VERTICAL_ALIGN_MIDDLE) {
            y += (target.getHeight() - text.getSize()) >> 1;
            y += verticalOffset;
        }
        if (verticalAlign == VERTICAL_ALIGN_TOP) {
            y += verticalOffset;
        }
        if (verticalAlign == VERTICAL_ALIGN_BOTTOM) {
            y += target.getHeight() - text.getSize() - verticalOffset;
        }
        g2.drawString(text.getText(), x, y);
    }

    public byte getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(byte verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public byte getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(byte horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public int getHorizontalOffset() {
        return horizontalOffset;
    }

    public void setHorizontalOffset(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }
}