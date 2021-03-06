package org.bombercraft2.components.tasks;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.components.health_bar.HealthBar;
import org.glib2.interfaces.HealthAble;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Task implements InteractAbleG2, HealthAble {
    private long            created  = System.currentTimeMillis();
    private HealthBar       healthBar;
    private List<BotWorker> bots;
    private TaskType        taskType;
    private float           workCount;
    private int             maxBots;
    private float           workTarget;
    private float           expRatio = 1f;
    private boolean         finished;

    public Task(@NotNull SimpleGameAble parent, int maxBots, float workTarget, @NotNull TaskType taskType) {
        this.maxBots = maxBots;
        this.taskType = taskType;
        healthBar = new HealthBar(this, parent);
        this.workTarget = workTarget;
        this.bots = new ArrayList<>(maxBots);
    }


    @Override
    public void update(float delta) {
        if (workCount >= workTarget) {
            finish();
        }
        System.out.println("aktualny stav po je " + workCount + " / " + workTarget);
    }

    public void render(@NotNull Graphics2D g2) {
        healthBar.render(g2);
    }

    private void finish() {
        finished = true;
        bots.forEach(BotWorker::finish);
        taskType.finish();
    }

    /**
     * @param work - work value of worker
     * @return - number of received experiences
     */
    public float work(float work) {
        workCount += work;
        return work * expRatio;
    }

    public void startWork(@NotNull BotWorker bot) {
        if (needMoreBots()) {
            bot.setTask(this);
            bots.add(bot);
        }
        else {
            System.err.println("V tasku je priradený maximálny počet botov (" + maxBots + ")");
        }
    }

    public boolean needMoreBots() {
        return bots.size() < maxBots && isNotFinished();
    }

    @Override
    public int getMaxHealth() {
        return (int) (workTarget * 100);
    }

    @Override
    public int getHealth() {
        return (int) (workCount * 100);
    }

    @NotNull
    @Override
    public GVector2f getPosition() {
        return taskType.getPosition();
    }

    @Override
    public @NotNull GVector2f getSize() {
        return StaticConfig.BLOCK_SIZE;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isNotFinished() {
        return !finished;
    }
}
