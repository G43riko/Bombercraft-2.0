package org.bombercraft2.gui2.layouts;

import org.bombercraft2.gui2.core.Drawable;

/**
 * Tento layout vkladá objekty do jedného riadku a keď sa nezmestía tak ich začne dávať do ďalšieho riadku
 */
public class VerticalFlowLayout extends Layout {
    public final static byte LEFT   = 1;
    public final static byte CENTER = 2;
    public final static byte RIGHT  = 3;

    public final static byte MAXIMALIZE = 4;
    public final static byte NOT_CHANGE = 5;
    public final static byte AVERAGE    = 6;
    public final static byte MINIMALIZE = 7;

    private boolean autoFixGaps    = false;
    private byte    changeHeight   = NOT_CHANGE;
    private int     verticalGap    = gap;
    private int     availableWidth = 0;

    public VerticalFlowLayout() {
        this.align = LEFT;
    }

    public VerticalFlowLayout(byte align) {
        this.align = align;
    }

    private void setItems(int lastChange,
                          int maxHeight,
                          int minHeight,
                          int sumHeight,
                          int sumWidth,
                          int i,
                          boolean last
                         ) {
        final int itemsInRow = i - lastChange;

        if (!last && autoFixGaps && itemsInRow > 1) {
            int normalizeGapSize = (availableWidth - sumWidth) / (itemsInRow - 1);

            int x = target.getX() + target.getHorizontalOffset();
            for (int j = lastChange; j < i; j++) {
                target.getComponents().get(j).setX(x);
                x += target.getComponents().get(j).getWidth() + normalizeGapSize;
            }
            target.getComponents()
                  .get(i - 1)
                  .setX(target.getX() + target.getHorizontalOffset() + availableWidth - target.getComponents()
                                                                                              .get(i - 1)
                                                                                              .getWidth());
        }

        switch (changeHeight) {
            case MAXIMALIZE:
                for (int j = lastChange; j < i; j++) {
                    target.getComponents().get(j).setHeight(maxHeight);
                }
                break;
            case MINIMALIZE:
                for (int j = lastChange; j < i; j++) {
                    target.getComponents().get(j).setHeight(minHeight);
                }
                break;
            case AVERAGE:
                for (int j = lastChange; j < i; j++) {
                    target.getComponents().get(j).setHeight(sumHeight / itemsInRow);
                }
                break;
        }
    }

    @Override
    public void resize() {
        if (target.getComponents().isEmpty()) {
            return;
        }
        availableWidth = target.getWidth() - (target.getHorizontalOffset() << 1);
        if (align == LEFT) {
            int x = target.getX() + target.getHorizontalOffset();
            int y = target.getY() + target.getVerticalOffset();
            int maxHeight = 0;
            int minHeight = target.getComponents().get(0).getHeight();
            int sumHeight = 0;
            int sumWidth = 0;
            int lastChange = 0;
            for (int i = 0; i < target.getComponents().size(); i++) {
                Drawable component = target.getComponents().get(i);

                if (availableWidth < x + component.getWidth()) {
                    y += maxHeight + verticalGap;
                    setItems(lastChange, maxHeight, minHeight, sumHeight, sumWidth, i, false);
                    lastChange = i;
                    sumHeight = 0;
                    minHeight = 0;
                    sumWidth = 0;
                    maxHeight = component.getHeight();
                    x = target.getX() + target.getHorizontalOffset();
                }
                maxHeight = Math.max(maxHeight, component.getHeight());
                minHeight = Math.min(minHeight, component.getHeight());
                sumHeight += component.getHeight();
                sumWidth += component.getWidth();
                component.setY(y);
                component.setX(x);
                x += component.getWidth() + gap;
            }
            setItems(lastChange, maxHeight, minHeight, sumHeight, sumWidth, target.getComponents().size(), true);
        }
    }

    public boolean isAutoFixGaps() {
        return autoFixGaps;
    }

    public void setAutoFixGaps(boolean autoFixGaps) {
        this.autoFixGaps = autoFixGaps;
    }

    public byte getChangeHeight() {
        return changeHeight;
    }

    public void setChangeHeight(byte changeHeight) {
        this.changeHeight = changeHeight;
    }

    public int getVerticalGap() {
        return verticalGap;
    }

    public void setVerticalGap(int verticalGap) {
        this.verticalGap = verticalGap;
    }
}
