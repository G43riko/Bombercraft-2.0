package org.bombercraft2.gui2;

import org.gui.components.Panel;
import org.gui.core.GuiConnector;
import org.gui.core.GuiManager;
import org.gui.core.components.Drawable;
import org.gui.utils.UpdateData;
import org.play_ground.misc.AbstractManager;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GuiManagerImpl extends AbstractManager implements GuiManager {
    private final List<Drawable> components    = new LinkedList<>();
    private final List<Drawable> postRender    = new LinkedList<>();
    private final int            defaultCursor = Cursor.DEFAULT_CURSOR;
    private       int            cursor        = defaultCursor;
    private       UpdateData     updateData;
    private       Panel          mainPanel;
    private       Canvas         canvas;

    @Override
    public void createMainPanel(Canvas canvas) {
        mainPanel = new Panel();
        add(mainPanel);
        this.canvas = canvas;
        onResize();
    }

    @Override
    public void onResize() {
        if (mainPanel != null && canvas != null) {
            mainPanel.setX(canvas.getX());
            mainPanel.setY(canvas.getY());
            mainPanel.setWidth(canvas.getWidth());
            mainPanel.setHeight(canvas.getHeight());
            mainPanel.onResize();
        }
        components.forEach(Drawable::onResize);
    }

    @Override
    public void render(Graphics2D g2) {
        //TODO toto sa bude volať až pri zmene rozlýšenia
        // onResize();
        cursor = defaultCursor;
        components.forEach((component) -> component.render(g2));
        postRender.forEach((item) -> item.render(g2));
        postRender.clear();
    }

    @Override
    public Panel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void setHoverCursor() {
        this.cursor = Cursor.HAND_CURSOR;
    }

    @Override
    public void setActiveCursor() {
        // this.cursor = Cursor.WAIT_CURSOR;
    }

    @Override
    public void setDisabledCursor() {
        // this.cursor = Cursor.WAIT_CURSOR;
    }

    @Override
    public void setDefaultCursor() {
        this.cursor = defaultCursor;
    }

    @Override
    public void add(Drawable component) {
        component.setManager(this);
        components.add(component);
    }

    @Override
    public Cursor getCursor() {
        return new Cursor(cursor);
    }

    @Override
    public boolean isMouseOn() {
        return GuiConnector.isMouseOn(mainPanel);
    }

    @Override
    public void update() {
        cursor = defaultCursor;
        updateData = new UpdateData(GuiConnector.getMouseX(),
                                    GuiConnector.getMouseY(),
                                    GuiConnector.isButtonDown());
        components.forEach((component) -> component.update(updateData));
    }

    @Override
    public void addToPostRender(Drawable item) {
        postRender.add(item);
    }
}
