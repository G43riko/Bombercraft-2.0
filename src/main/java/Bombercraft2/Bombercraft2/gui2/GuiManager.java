package Bombercraft2.Bombercraft2.gui2;

import Bombercraft2.Bombercraft2.gui2.core.Drawable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GuiManager {
    private List<Drawable> components = new LinkedList<>();
    private int defaultCursor = Cursor.DEFAULT_CURSOR;
    private int cursor = defaultCursor;

    public void render(Graphics2D g2) {
        cursor = defaultCursor;
        components.forEach((component) -> component.render(g2));
    }

    public void setHoverCursor() {
        this.cursor = Cursor.HAND_CURSOR;
    }
    public void setActiveCursor() {
        // this.cursor = Cursor.WAIT_CURSOR;
    }
    public void setDisabledCursor() {
        // this.cursor = Cursor.WAIT_CURSOR;
    }
    public void add(Drawable component) {
        component.setManager(this);;
        components.add(component);
    }
    public Cursor getCursor() {
        return new Cursor(cursor);
    }
}
