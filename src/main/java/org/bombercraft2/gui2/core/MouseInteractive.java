package org.bombercraft2.gui2.core;

import org.glib2.interfaces.VoidCallback;
import utils.math.GVector2f;

public class MouseInteractive {
    private final PositionableComponent parent;
    private final GVector2f             startDraggedPosition = new GVector2f();
    private       boolean               hover                = false;
    private       boolean               dragged              = false;
    private       boolean               active               = false;
    private       VoidCallback          clickHandler;

    public MouseInteractive(PositionableComponent parent) {
        this.parent = parent;
    }

    public void onClick() {
        if (clickHandler != null) {
            clickHandler.accept();
        }
    }

    public void update(UpdateData data) {
        hover = GuiConnector.isMouseOn(parent, data);
        if (hover) {
            if (data.isMouseDown) {
                if (!dragged) {
                    dragged = true;
                    startDraggedPosition.set(data.mouseX, data.mouseY);
                }
                active = true;
            }
            else {
                if (active) {
                    onClick();
                }
                dragged = false;
                active = false;
            }
        }
        else {
            active = false;
            if (dragged && !data.isMouseDown) {
                dragged = false;
            }
        }
    }

    public boolean isHover() {return hover;}

    public boolean isDragged() {return dragged;}

    public boolean isActive() {return active;}

    public void setClickHandler(VoidCallback clickHandler) { this.clickHandler = clickHandler; }
}
