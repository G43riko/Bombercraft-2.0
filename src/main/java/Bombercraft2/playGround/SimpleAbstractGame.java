package Bombercraft2.playGround;

import Bombercraft2.Bombercraft2.MainManager;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.ViewManager;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public abstract class SimpleAbstractGame<T extends CoreEngine> extends GameState implements SimpleGameAble {
    @NotNull
    protected final T parent;
    @NotNull
    protected final MainManager manager = new MainManager();

    protected SimpleAbstractGame(@NotNull T parent, @NotNull Type type) {
        super(type);
        this.parent = parent;
    }

    protected void setViewManager(@NotNull ViewManager viewManager) {
        manager.setManagers(viewManager);
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
