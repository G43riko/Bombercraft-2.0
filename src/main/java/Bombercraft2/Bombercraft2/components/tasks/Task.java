package Bombercraft2.Bombercraft2.components.tasks;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.components.healthBar.HealthAble;
import Bombercraft2.Bombercraft2.components.healthBar.HealthBar;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Task implements InteractAble, HealthAble {
    private long created = System.currentTimeMillis();
    private HealthBar       healthBar;
    private List<BotWorker> bots;
    private TaskType        taskType;
    private float           workCount;
    private int             maxBots;
    private float           workTarget;
    private float expRatio = 1f;
    private boolean finished;

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
    public int getActHealth() {
        return (int) (workCount * 100);
    }

    @NotNull
    @Override
    public GVector2f getPosition() {
        return taskType.getPosition();
    }

    @Override
    public @NotNull GVector2f getSize() {
        return Config.BLOCK_SIZE;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isNotFinished() {
        return !finished;
    }
}
