package Bombercraft2.Bombercraft2.gui2.layouts;

import Bombercraft2.Bombercraft2.gui2.core.Drawable;

import java.util.List;

public class VerticalLayout extends Layout {
    public static byte ALIGN_UP     = 0;
    public static byte ALIGN_MIDDLE = 1;
    public static byte ALIGN_BOTTOM = 2;

    private boolean changeHorizontallyToo  = true;
    private boolean changeComponentsHeight = false;
    private int     componentHeight        = 40;

    public VerticalLayout() {
        this.align = ALIGN_UP;
    }
    public VerticalLayout(byte align) {
        this.align = align;
    }

    @Override
    public void resize() {
        if (align == ALIGN_UP) {
            int y = target.getY() + target.getVerticalOffset();
            final int targetAvailableWidth = target.getWidth() - (target.getHorizontalOffset() << 1);
            final int targetStartPositionX = target.getX() + target.getHorizontalOffset();
            List<Drawable> components = target.getComponents();

            for (Drawable component : components) {
                component.setY(y);
                if (changeComponentsHeight) {
                    component.setHeight(componentHeight);
                }
                if (changeHorizontallyToo) {
                    component.setX(targetStartPositionX);
                    component.setWidth(targetAvailableWidth);
                }
                y += component.getHeight() + gap;
            }
        }
    }

    public boolean isChangeHorizontallyToo() {return changeHorizontallyToo;}

    public boolean isChangeComponentsHeight() { return changeComponentsHeight;}

    public int getComponentHeight() {return componentHeight;}

    public void setChangeHorizontallyToo(boolean changeHorizontallyToo) {this.changeHorizontallyToo = changeHorizontallyToo;}

    public void setChangeComponentsHeight(boolean changeComponentsHeight) {this.changeComponentsHeight = changeComponentsHeight;}

    public void setComponentHeight(int componentHeight) {this.componentHeight = componentHeight;}
}
