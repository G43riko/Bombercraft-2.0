package Bombercraft2.Bombercraft2.gui2;

import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.core.Drawable;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.UpdateData;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GuiManager {
    private final List<Drawable> components    = new LinkedList<>();
    private final List<Drawable> postRender    = new LinkedList<>();
    private final int            defaultCursor = Cursor.DEFAULT_CURSOR;
    private       int            cursor        = defaultCursor;
    private Panel mainPanel;
    private Canvas canvas;

    public void createMainPanel(Canvas canvas) {
        mainPanel = new Panel();
        add(mainPanel);
        this.canvas = canvas;
        onResize();
    }

    public void onResize() {
        if(mainPanel != null && canvas != null) {
            mainPanel.setX(canvas.getX());
            mainPanel.setY(canvas.getY());
            mainPanel.setWidth(canvas.getWidth());
            mainPanel.setHeight(canvas.getHeight());
            mainPanel.onResize();
        }
        components.forEach(Drawable::onResize);
    }

    public void render(Graphics2D g2) {
        //TODO toto sa bude volať až pri zmene rozlýšenia
        onResize();
        cursor = defaultCursor;
        components.forEach((component) -> component.render(g2));
        postRender.forEach((item) -> item.render(g2));
        postRender.clear();
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
    public void setDefaultCursor() {
        this.cursor = defaultCursor;
    }
    public void add(Drawable component) {
        component.setManager(this);
        components.add(component);
    }
    public Cursor getCursor() {
        return new Cursor(cursor);
    }

    public void update() {
        cursor = defaultCursor;
        UpdateData data = new UpdateData(GuiConnector.getMouseX(),
                                         GuiConnector.getMouseY(),
                                         GuiConnector.isButtonDown());
        components.forEach((component) -> component.update(data));
    }

    public void addToPostRender(Drawable item) {
        postRender.add(item);
    }
}
