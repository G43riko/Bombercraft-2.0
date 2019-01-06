package org.bombercraft2.game.misc;

import org.glib2.math.vectors.GVector2f;

import java.awt.*;

public class GCanvas {
    public static void drawRect(Graphics2D g2, GVector2f pos, GVector2f size, Color borderColor, int borderWidth) {
        g2.setStroke(new BasicStroke(borderWidth));
        g2.setColor(borderColor);
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawRect(Graphics2D g2, GVector2f pos, GVector2f size, Color borderColor) {
        g2.setColor(borderColor);
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawRect(Graphics2D g2, GVector2f pos, GVector2f size, int borderWidth) {
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawRect(Graphics2D g2, GVector2f pos, GVector2f size) {
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void fillRect(Graphics2D g2, GVector2f pos, GVector2f size, Color fillColor) {
        g2.setColor(fillColor);
        g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void fillRect(Graphics2D g2, GVector2f pos, GVector2f size) {
        g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawImage(Graphics2D g2, Image image, GVector2f pos, GVector2f size) {
        g2.drawImage(image, pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
    }


}
