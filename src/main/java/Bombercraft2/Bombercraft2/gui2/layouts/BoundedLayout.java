package Bombercraft2.Bombercraft2.gui2.layouts;

import Bombercraft2.Bombercraft2.gui2.core.Drawable;

/**
 * Tento layout roztiahne objekt do stredu panel a X pixelov od okraju obrazovky
 */
public class BoundedLayout extends Layout {
    private int southOffset;
    private int northOffset;
    private int eastOffset;
    private int westOffset;

    public BoundedLayout(int offset) {
        southOffset = northOffset = eastOffset = westOffset = offset;
    }

    public BoundedLayout(int verticalOffset, int horizontalOffset) {
        southOffset = northOffset = verticalOffset;
        eastOffset = westOffset = horizontalOffset;
    }

    public BoundedLayout(int southOffset, int northOffset, int eastOffset, int westOffset) {
        this.southOffset = southOffset;
        this.northOffset = northOffset;
        this.eastOffset = eastOffset;
        this.westOffset = westOffset;
    }

    @Override
    public void resize() {
        for (Drawable component : target.getComponents()) {
            component.setX(target.getX() + westOffset);
            component.setY(target.getY() + northOffset);
            component.setWidth(target.getWidth() - (westOffset + eastOffset));
            component.setWidth(target.getHeight() - (southOffset + northOffset));
        }
    }
}
