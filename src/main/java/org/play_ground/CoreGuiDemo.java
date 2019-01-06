package org.play_ground;

import org.bombercraft2.gui2.GuiManagerImpl;
import org.engine.CoreEngine;
import org.gui.components.Panel;
import org.jetbrains.annotations.NotNull;
import org.utils.logger.GLogger;

import java.awt.*;

public abstract class CoreGuiDemo extends CoreEngine {
    protected final GuiManagerImpl guiManager = new GuiManagerImpl();
    private final   Panel          mainMenu   = new Panel();

    public CoreGuiDemo(int fps, int ups, boolean renderTime) {
        super(fps, ups, renderTime);
        GLogger.setStreams();

        guiManager.createMainPanel(getCanvas());
        getWindow().setVisible(false);

        guiManager.getMainPanel().addComponent(mainMenu);

        initComponents();
        getWindow().setVisible(true);
    }

    protected abstract void initComponents();

    @Override
    protected void render(@NotNull Graphics2D g2) {
        guiManager.render(g2);
    }

    @Override
    protected void update(float delta) {
        guiManager.update();
        getCanvas().setCursor(guiManager.getCursor());
    }

    @Override
    public void onResize() {
        guiManager.onResize();
    }
}
