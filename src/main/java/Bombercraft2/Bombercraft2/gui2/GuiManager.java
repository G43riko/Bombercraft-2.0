package Bombercraft2.Bombercraft2.gui2;

import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.core.Drawable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GuiManager {
    private List<Drawable> components = new LinkedList<>();
    private int defaultCursor = Cursor.DEFAULT_CURSOR;
    private int cursor = defaultCursor;
    private Panel mainPanel;

    public void createMainPanel(Canvas canvas) {
        mainPanel = new Panel();
        add(mainPanel);
        mainPanel.setX(canvas.getX());
        mainPanel.setY(canvas.getY());
        mainPanel.setWidth(canvas.getWidth());
        mainPanel.setHeight(canvas.getHeight());
    }


    public void render(Graphics2D g2) {
        cursor = defaultCursor;
        components.forEach((component) -> component.render(g2));
    }

    public Panel getMainPanel() {
        return mainPanel;
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
