package org.bombercraft2.gui2.components;

import org.bombercraft2.gui2.core.GuiConnector;
import org.bombercraft2.gui2.core.TranslateColorBox;
import org.bombercraft2.gui2.core.translation.TranslateColor;

import java.awt.*;

public class TransparentVerticalScrollPanel extends VerticalScrollPanel {
    private final TranslateColorBox scrollBarMovable    = new TranslateColorBox(new TranslateColor(new Color(0,
                                                                                                             0,
                                                                                                             0,
                                                                                                             100),
                                                                                                   new Color(0,
                                                                                                             0,
                                                                                                             0,
                                                                                                             150),
                                                                                                   200));
    private final TranslateColorBox scrollBarBackground = new TranslateColorBox(new TranslateColor(new Color(0,
                                                                                                             0,
                                                                                                             0,
                                                                                                             0),
                                                                                                   new Color(0,
                                                                                                             0,
                                                                                                             0,
                                                                                                             50),
                                                                                                   200));


    protected void renderPanel(Graphics2D g2) {
        updatePanel();

        final boolean isOnScrollBarBackground = GuiConnector.isMouseOn(getX() + super.getWidth(),
                                                                       getY(),
                                                                       scrollBarWidth,
                                                                       getHeight());

        scrollBarMovable.setHover(isOnScrollBarBackground, isMoving());
        scrollBarBackground.setHover(isOnScrollBarBackground, isMoving());

        scrollBarBackground.render(g2, getX() + super.getWidth(), getY(), scrollBarWidth, getHeight());

        scrollBarMovable.render(g2, getX() + super.getWidth(), getY() + offset, scrollBarWidth, scrollBarHeight);
    }

    @Override
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        if (colorBox != null) {
            colorBox.render(g2, getX(), getY(), super.getRealWidth(), getHeight());
        }
        renderPanel(g2);
        Shape clip = g2.getClip();
        g2.setClip(getX() + Math.max(1, (colorBox.getBorderWidth() >> 1)),
                   getY() + Math.max(1, (colorBox.getBorderWidth() >> 1)),
                   super.getWidth() - colorBox.getBorderWidth(),
                   getHeight() - colorBox.getBorderWidth());
        //TODO filter na viditelne
        components.forEach((component) -> component.render(g2));
        g2.setClip(clip);
    }
}
