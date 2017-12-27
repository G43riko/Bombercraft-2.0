package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.Drawable;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.translation.TranslateColor;
import Bombercraft2.Bombercraft2.gui2.layouts.VerticalLayout;

import java.awt.*;

public class VerticalScrollPanel extends Panel {
    protected int      scrollBarWidth         = 20;
    private   ColorBox scrollBarBackground    = new ColorBox(Color.YELLOW, Color.BLACK, 1);
    private   ColorBox scrollBarMovable       = new ColorBox(Color.GREEN, Color.BLACK, 1);
    private   int      componentsHeight       = 0;
    protected int      scrollBarHeight        = 0;
    private   int      initMousePosition      = 0;
    protected int      offset                 = 0;
    private   int      maxOffset              = 0;
    private   int      relativeVerticalOffset = super.getVerticalOffset();
    private   boolean  renderScrollBar        = false;
    // private TranslateColor scrollBarBackgroundColor = new TranslateColor(Color.WHITE, Color.BLACK, 1000);

    public VerticalScrollPanel() {
        setLayout(new VerticalLayout(VerticalLayout.ALIGN_UP));
    }

    @Override
    public int getWidth() {
        return renderScrollBar ? super.getWidth() - scrollBarWidth : super.getWidth();
    }

    public int getRealWidth() { return super.getWidth();}

    private void calcScrollBar() {
        componentsHeight = components.stream().mapToInt(a -> a.getHeight() + getLayout().getGap()).sum();
        scrollBarHeight = Math.min(getHeight(), (int) (((float) getHeight()) / componentsHeight * getHeight()));
        maxOffset = getHeight() - scrollBarHeight;
        renderScrollBar = componentsHeight > getHeight() - (getHorizontalOffset() << 1);
        getLayout().resize();
    }

    @Override
    public void addComponent(Drawable component) {
        super.addComponent(component);
        calcScrollBar();
    }

    @Override
    public void setManager(GuiManager manager) {
        super.setManager(manager);
        calcScrollBar();
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        calcScrollBar();
    }

    @Override
    public int getVerticalOffset() {
        return relativeVerticalOffset;
    }

    public boolean isMoving() {
        return initMousePosition != 0;
    }

    protected void updatePanel() {
        final boolean isOnScrollBar = GuiConnector.isMouseOn(getX() + getWidth(),
                                                             getY() + offset,
                                                             scrollBarWidth,
                                                             scrollBarHeight);
        if (GuiConnector.isMouseOn(this) && GuiConnector.getScroll() != 0) {
            offset = Math.min(Math.max(0, offset - GuiConnector.getScroll() * 10), maxOffset);
            initMousePosition = GuiConnector.getMouseY();
            relativeVerticalOffset = -(int) ((float) offset / maxOffset * (componentsHeight - getHeight() + super.getVerticalOffset()));
            relativeVerticalOffset += super.getVerticalOffset();

            getLayout().resize();
        }
        if (GuiConnector.isButtonDown()) {
            if (initMousePosition == 0) {
                if (isOnScrollBar) {
                    initMousePosition = GuiConnector.getMouseY();
                }
            }
            else {
                offset = Math.min(Math.max(0, offset + GuiConnector.getMouseY() - initMousePosition), maxOffset);
                initMousePosition = GuiConnector.getMouseY();
                relativeVerticalOffset = -(int) ((float) offset / maxOffset * (componentsHeight - getHeight() + super.getVerticalOffset()));
                relativeVerticalOffset += super.getVerticalOffset();

                getLayout().resize();
            }
        }
        else {
            initMousePosition = 0;
        }

        if (isOnScrollBar) {
            getManager().setHoverCursor();

        }
    }

    protected void renderPanel(Graphics2D g2) {
        updatePanel();

        final boolean isOnScrollBarBackground = GuiConnector.isMouseOn(getX() + getWidth(),
                                                                       getY(),
                                                                       scrollBarWidth,
                                                                       getHeight());

        /*
        if (scrollBarBackgroundColor.setValue(isOnScrollBarBackground)) {
            scrollBarBackground.setBackgroundColor(scrollBarBackgroundColor.getActualColor());
        }
        */

        scrollBarBackground.render(g2, getX() + getWidth(), getY(), scrollBarWidth, getHeight());


        scrollBarMovable.render(g2, getX() + getWidth(), getY() + offset, scrollBarWidth, scrollBarHeight);
    }

    @Override
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        if (colorBox != null) {
            colorBox.render(g2, getX(), getY(), super.getWidth(), getHeight());
        }
        if (renderScrollBar) {
            renderPanel(g2);
        }
        Shape clip = g2.getClip();
        g2.setClip(getX() + Math.max(1, (colorBox.getBorderWidth() >> 1)),
                   getY() + Math.max(1, (colorBox.getBorderWidth() >> 1)),
                   getWidth() - colorBox.getBorderWidth(),
                   getHeight() - colorBox.getBorderWidth());
        //TODO filter na viditelne
        components.forEach((component) -> component.render(g2));
        g2.setClip(clip);
    }

    public void scrollToTop() {
        offset = 0;
        relativeVerticalOffset = 0;
        getLayout().resize();
    }
}
