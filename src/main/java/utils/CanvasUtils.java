package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import utils.math.GVector2f;

public class CanvasUtils {
    private final static int   DEFAULT_BORDER_WIDTH = 0;
    private final static Color DEFAULT_COLOR        = null;

    private enum Position {
        CENTERED, UP_LEFT, DOWN_RIGHT
    }

    private static GVector2f calcPosition(GVector2f position, GVector2f size, Position type) {
        switch (type) {
            case CENTERED:
                return position.sub(size.div(2));
            case DOWN_RIGHT:
                return position.sub(size);
            default:
                return position;
        }
    }

    public static void fillArcCentered(Graphics2D g2, GVector2f pos, GVector2f size) {
        fillArcAround(g2, pos, size, DEFAULT_COLOR);
    }

    public static void fillArcAround(Graphics2D g2, GVector2f pos, GVector2f size, Color color) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        g2.fillArc(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public static void drawArcCentered(Graphics2D g2, GVector2f pos, GVector2f size) {
        drawArcCenterd(g2, pos, size, null, DEFAULT_BORDER_WIDTH);
    }

    public static void drawArcCentered(Graphics2D g2, GVector2f pos, GVector2f size, Color color) {
        drawArcCenterd(g2, pos, size, color, DEFAULT_BORDER_WIDTH);
    }

    public static void drawArcCenterd(Graphics2D g2, GVector2f pos, GVector2f size, Color color, float width) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (width != DEFAULT_BORDER_WIDTH) {
            g2.setStroke(new BasicStroke(width));
        }
        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        g2.drawArc(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public static void fillRectCentered(Graphics2D g2, GVector2f pos, GVector2f size) {
        fillRectCentered(g2, pos, size, DEFAULT_COLOR);
    }

    public static void fillRectCentered(Graphics2D g2, GVector2f pos, GVector2f size, Color color) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (color != null) {
            g2.setColor(color);
        }
        g2.fillRect(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawRectCentered(Graphics2D g2, GVector2f pos, GVector2f size) {
        drawRectCentered(g2, pos, size, DEFAULT_COLOR, DEFAULT_BORDER_WIDTH);
    }

    public static void drawRectCentered(Graphics2D g2, GVector2f pos, GVector2f size, Color color) {
        drawRectCentered(g2, pos, size, color, DEFAULT_BORDER_WIDTH);
    }

    public static void drawRectCentered(Graphics2D g2, GVector2f pos, GVector2f size, Color color, float width) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (width != DEFAULT_BORDER_WIDTH) {
            g2.setStroke(new BasicStroke(width));
        }
        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        g2.drawRect(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi());
    }
}
