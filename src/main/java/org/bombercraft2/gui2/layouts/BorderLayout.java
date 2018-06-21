package org.bombercraft2.gui2.layouts;

import org.bombercraft2.gui2.core.Drawable;

/**
 * Tento layout natlačí objekty na jednu stranu a vykresluje ich od okraja obrazovky smerom do stredu
 */
public class BorderLayout extends Layout {
    public final static byte SOUTH  = 1;
    public final static byte EAST   = 2;
    public final static byte WEST   = 3;
    public final static byte NORTH  = 4;
    public final static byte CENTER = 5;

    private final boolean changeAnotherDirection = true;

    public BorderLayout(byte align) {
        this.align = align;
    }

    @Override
    public void resize() {
        if (align == NORTH) {
            int actPosition = target.getY() + target.getVerticalOffset();
            for (Drawable component : target.getComponents()) {
                if (changeAnotherDirection) {
                    component.setWidth(target.getWidth() - (target.getHorizontalOffset() << 1));
                    component.setX(target.getX() + target.getVerticalOffset());
                }
                component.setY(actPosition);
                actPosition += component.getHeight() + gap;
            }
        }
        else if (align == WEST) {
            int actPosition = target.getX() + target.getHorizontalOffset();
            for (Drawable component : target.getComponents()) {
                if (changeAnotherDirection) {
                    component.setHeight(target.getHeight() - (target.getVerticalOffset() << 1));
                    component.setY(target.getY() + target.getHorizontalOffset());
                }
                component.setX(actPosition);
                actPosition += component.getWidth() + gap;
            }
        }
    }
}
