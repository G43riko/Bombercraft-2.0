package org.play_ground;

import org.bombercraft2.MainManager;
import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.glib2.system_analytics.SystemInfoGrabber;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;
import org.play_ground.misc.ViewManager;
import utils.math.GVector2f;

import java.awt.*;
import java.util.List;

public abstract class SimpleAbstractGame<T extends CoreEngine> extends GameState implements SimpleGameAble {
    @NotNull
    protected final T           parent;
    @NotNull
    protected final MainManager manager = new MainManager();

    protected SimpleAbstractGame(@NotNull T parent, @NotNull GameStateType type) {
        super(type);
        this.parent = parent;
    }

    @Override
    @NotNull
    public List<String> getLogInfo() {
        List<String> result = SimpleGameAble.super.getLogInfo();
        result.add("FPS: " + parent.getFPS());
        result.add("UPS: " + parent.getUPS());
        result.add("Loops: " + parent.getLoops());
        result.addAll(SystemInfoGrabber.getMemoryInfo());
        return result;
    }

    @Override
    public void update(float delta) {
        manager.update(delta);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        manager.render(g2);
    }

    @Override
    public void onResize() {
        manager.onResize(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
    }

    @Override
    public float getZoom() {
        ViewManager viewManager = manager.getViewManager();
        return viewManager == null ? 1 : viewManager.getZoom();
    }

    @Override
    public @NotNull GVector2f getOffset() {
        ViewManager viewManager = manager.getViewManager();
        return viewManager == null ? new GVector2f() : viewManager.getOffset();
    }

    @Override
    public @NotNull GVector2f getCanvasSize() {
        ViewManager viewManager = manager.getViewManager();
        return viewManager == null ? new GVector2f() : viewManager.getCanvasSize();
    }

    @Override
    public MainManager getManager() {
        return manager;
    }

    public Canvas getCanvas() {
        return parent.getCanvas();
    }

    protected void doInput() {
        ViewManager viewManager = manager.getViewManager();
        if (viewManager != null) {
            viewManager.input();
        }
    }
}
