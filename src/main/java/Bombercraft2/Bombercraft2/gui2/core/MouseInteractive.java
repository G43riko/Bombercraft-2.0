package Bombercraft2.Bombercraft2.gui2.core;

import Bombercraft2.Bombercraft2.core.BasicListener;
import utils.math.GVector2f;

public class MouseInteractive {
    private final PositionableComponent parent;
    private       boolean   hover                = false;
    private       boolean   dragged              = false;
    private       boolean   active               = false;
    private final GVector2f startDraggedPosition = new GVector2f();
    private BasicListener clickHandler;

    public MouseInteractive(PositionableComponent parent) {
        this.parent = parent;
    }

    public void onClick() {
        if (clickHandler != null) {
            clickHandler.doAct();
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

    public void setClickHandler(BasicListener clickHandler) { this.clickHandler = clickHandler; }
}
