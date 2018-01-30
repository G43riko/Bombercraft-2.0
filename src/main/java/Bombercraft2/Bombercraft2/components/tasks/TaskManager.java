package Bombercraft2.Bombercraft2.components.tasks;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager implements InteractAble {
    private final List<Task> tasks = new ArrayList<>();
    @NotNull
    private SimpleGameAble parent;
    @NotNull
    private BotManager     bots;

    public TaskManager(@NotNull BotManager bots, @NotNull SimpleGameAble parent) {
        this.bots = bots;
        this.parent = parent;
    }


    public void addTask(@NotNull Task task) {
        tasks.add(task);
    }

    @Override
    public void update(float delta) {
        tasks.stream()
             .filter(Task::isNotFinished)
             .peek(task -> task.update(delta))
             .filter(Task::needMoreBots)
             .forEach(task -> {
                 BotWorker bot = (BotWorker) bots.getFreeBotNearestTo(task.getPosition());
                 if (bot != null) {
                     task.startWork(bot);
                 }
             });
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        tasks.stream().filter(parent::isVisible).filter(Task::isNotFinished).forEach(task -> task.render(g2));
    }
}
