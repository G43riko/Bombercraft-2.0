package Bombercraft2.Bombercraft2;

import Bombercraft2.Bombercraft2.components.tasks.BotManager;
import Bombercraft2.Bombercraft2.components.tasks.TaskManager;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.playGround.Misc.AbstractManager;
import Bombercraft2.playGround.Misc.PostFxManager;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.MapManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MainManager implements InteractAble {
    private ViewManager   viewManager;
    private PostFxManager postFxManager;
    private TaskManager   taskManager;
    private MapManager    mapManager;
    private BotManager    botManager;
    private GuiManager    guiManager;

    public MainManager(AbstractManager... managers) {
        this.setManagers(managers);
    }

    public void setManagers(AbstractManager... managers) {
        for (AbstractManager manager : managers) {
            setManager(manager);
        }
    }

    private void setManager(AbstractManager manager) {
        if (manager instanceof PostFxManager) {
            this.postFxManager = (PostFxManager) manager;
        }
        else if (manager instanceof ViewManager) {
            this.viewManager = (ViewManager) manager;
        }
        else if (manager instanceof MapManager) {
            this.mapManager = (MapManager) manager;
        }
        else if (manager instanceof TaskManager) {
            this.taskManager = (TaskManager) manager;
        }
        else if (manager instanceof BotManager) {
            this.botManager = (BotManager) manager;
        }
        else if (manager instanceof GuiManager) {
            this.guiManager = (GuiManager) manager;
        }
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }


    public MapManager getMapManager() {
        return mapManager;
    }

    public BotManager getBotManager() {
        return botManager;
    }
    public ViewManager getViewManager() {
        return viewManager;
    }

    public PostFxManager getPostFxManager() {
        return postFxManager;
    }
    public TaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (mapManager != null) {
            mapManager.render(g2);
        }
        if (taskManager != null) {
            taskManager.render(g2);
        }
        if (botManager != null) {
            botManager.render(g2);
        }
        if (guiManager != null) {
            guiManager.render(g2);
        }
    }
    public void onResize(int width, int height) {
        if (viewManager != null) {
            viewManager.setCanvasSize(width, height);
        }
    }
    @Override
    public void update(float delta) {
        if (taskManager != null) {
            taskManager.update(delta);
        }
        if (botManager != null) {
            botManager.update(delta);
        }
        if (guiManager != null) {
            guiManager.update();
        }

        if (viewManager != null) {
            viewManager.update(delta);
        }
    }

    @Override
    public void input() {
        if (viewManager != null) {
            viewManager.input();
        }
    }
}
