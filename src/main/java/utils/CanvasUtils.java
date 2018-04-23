package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.misc.GCanvas;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public class CanvasUtils {
    private final static int   DEFAULT_BORDER_WIDTH = 0;
    private final static Color DEFAULT_COLOR        = null;

    private enum Position {
        CENTERED, UP_LEFT, DOWN_RIGHT
    }

    private static GVector2f calcPosition(@NotNull GVector2f position,
                                          @NotNull GVector2f size,
                                          @NotNull Position type
                                         ) {
        switch (type) {
            case CENTERED:
                return position.sub(size.div(2));
            case DOWN_RIGHT:
                return position.sub(size);
            default:
                return position;
        }
    }

    public static void fillArcCentered(@NotNull Graphics2D g2, @NotNull GVector2f pos, @NotNull GVector2f size) {
        fillArcAround(g2, pos, size, DEFAULT_COLOR);
    }

    private static void fillArcAround(@NotNull Graphics2D g2,
                                      @NotNull GVector2f pos,
                                      @NotNull GVector2f size,
                                      @Nullable Color color
                                     ) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        g2.fillArc(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public static void drawArcCentered(@NotNull Graphics2D g2, @NotNull GVector2f pos, @NotNull GVector2f size) {
        drawArcCentered(g2, pos, size, null, DEFAULT_BORDER_WIDTH);
    }

    public static void drawArcCentered(@NotNull Graphics2D g2,
                                       GVector2f pos,
                                       @NotNull GVector2f size,
                                       @NotNull Color color
                                      ) {
        drawArcCentered(g2, pos, size, color, DEFAULT_BORDER_WIDTH);
    }

    private static void drawArcCentered(@NotNull Graphics2D g2,
                                        @NotNull GVector2f pos,
                                        @NotNull GVector2f size,
                                        @Nullable Color color,
                                        float width
                                       ) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (width != DEFAULT_BORDER_WIDTH) {
            g2.setStroke(new BasicStroke(width));
        }
        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        g2.drawArc(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public static void fillRectCentered(@NotNull Graphics2D g2, @NotNull GVector2f pos, @NotNull GVector2f size) {
        fillRectCentered(g2, pos, size, DEFAULT_COLOR);
    }

    private static void fillRectCentered(@NotNull Graphics2D g2,
                                         @NotNull GVector2f pos,
                                         @NotNull GVector2f size,
                                         @Nullable Color color
                                        ) {
        GVector2f realPos = calcPosition(pos, size, Position.CENTERED);

        if (color != DEFAULT_COLOR) {
            g2.setColor(color);
        }
        GCanvas.fillRect(g2, pos, size);
        // g2.fillRect(realPos.getXi(), realPos.getYi(), size.getXi(), size.getYi());
    }

    public static void drawRectCentered(@NotNull Graphics2D g2, @NotNull GVector2f pos, @NotNull GVector2f size) {
        drawRectCentered(g2, pos, size, DEFAULT_COLOR, DEFAULT_BORDER_WIDTH);
    }

    public static void drawRectCentered(@NotNull Graphics2D g2,
                                        @NotNull GVector2f pos,
                                        @NotNull GVector2f size,
                                        @Nullable Color color
                                       ) {
        drawRectCentered(g2, pos, size, color, DEFAULT_BORDER_WIDTH);
    }

    private static void drawRectCentered(@NotNull Graphics2D g2,
                                         @NotNull GVector2f pos,
                                         @NotNull GVector2f size,
                                         @Nullable Color color,
                                         float width
                                        ) {
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
