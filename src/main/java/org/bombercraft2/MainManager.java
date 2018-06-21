package org.bombercraft2;

import org.bombercraft2.components.tasks.BotManager;
import org.bombercraft2.components.tasks.TaskManager;
import org.bombercraft2.gui2.GuiManager;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.*;
import org.playGround.Misc.map.MapManager;
import org.playGround.Misc.selectors.SelectorManager;
import org.utils.MeasureUtils;
import utils.GLogger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainManager implements InteractAbleG2 {
    private ViewManager           viewManager;
    private PostFxManager         postFxManager;
    private TaskManager           taskManager;
    private MapManager            mapManager;
    private BotManager            botManager;
    private GuiManager            guiManager;
    private SelectorManager       selectorManager;
    private PlayerManager         playerManager;
    private SceneManager_         sceneManager;
    private List<AbstractManager> managers = new ArrayList<>();

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
        else if (manager instanceof SelectorManager) {
            this.selectorManager = (SelectorManager) manager;
        }
        else if (manager instanceof SceneManager_) {
            this.sceneManager = (SceneManager_) manager;
        }
        else if (manager instanceof PlayerManager) {
            this.playerManager = (PlayerManager) manager;
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
        else {
            GLogger.error(GLogger.GError.UNKNOWN_MANAGER, ": " + manager.getClass().getName());
        }
        managers.add(manager);
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }


    public MapManager getMapManager() {
        return mapManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public SceneManager_ getSceneManager() {
        return sceneManager;
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

            MeasureUtils.measureNano("---------Render - manager ---- map", () -> mapManager.render(g2));
        }
        if (postFxManager != null) {
            MeasureUtils.measureNano("---------Render - manager - postFx", () -> postFxManager.render(g2));
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
        if (selectorManager != null) {
            selectorManager.render(g2);
        }
        if (sceneManager != null) {
            MeasureUtils.measureNano("---------Render - manager -- scene", () -> sceneManager.render(g2));
        }
        if (playerManager != null) {
            MeasureUtils.measureNano("---------Render - manager - player", () -> playerManager.render(g2));
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
        if (sceneManager != null) {
            sceneManager.update(delta);
        }
        if (playerManager != null) {
            playerManager.update(delta);
        }
    }

    @Override
    public void input() {
        if (viewManager != null) {
            viewManager.input();
        }
        if (playerManager != null) {
            playerManager.input();
        }
    }

    @NotNull
    public List<String> getLogInfo() {
        return managers.stream()
                       .map(AbstractManager::getLogInfo)
                       .reduce(new ArrayList<>(), (p1, p2) -> {
                           p1.addAll(p2);
                           return p1;
                       });
    }
}
