package Bombercraft2.Bombercraft2.gui2.layouts;

import Bombercraft2.Bombercraft2.gui2.core.Drawable;

import java.util.List;

/**
 * Tento layout rozdelí obrazku vertikálne na X rovnakých časti podla počtu komponentov
 */
public class DividedVerticalLayout extends Layout{
    private boolean changeComponentsWidth = true;
    private int gap = 0;

    @Override
    public void resize() {
        List<Drawable> components = target.getComponents();

        final int targetAvailableWidth = target.getWidth() - (target.getHorizontalOffset() << 1);
        final int availableHeight = target.getHeight() - (target.getVerticalOffset() << 1 ) - gap * (components.size() - 1);
        final int componentsHeight = availableHeight / components.size();
        int y = target.getY() + target.getVerticalOffset();

        for (Drawable component : components) {
            component.setY(y);
            component.setX(target.getX() + target.getHorizontalOffset());
            if(changeComponentsWidth) {
                component.setWidth(targetAvailableWidth);
            }
            component.setHeight(componentsHeight);
            y += componentsHeight + gap;
        }
    }

    public boolean isChangeComponentsWidth() {return changeComponentsWidth;}
    @Override
    public int getGap() {return gap;}

    public void setChangeComponentsWidth(boolean changeComponentsWidth) {this.changeComponentsWidth = changeComponentsWidth;}
    @Override
    public void setGap(int gap) {this.gap = gap;}
}
