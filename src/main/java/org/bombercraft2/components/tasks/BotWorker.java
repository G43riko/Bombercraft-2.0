package org.bombercraft2.components.tasks;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.bots.Bot;
import org.bombercraft2.game.bots.BotFactory;
import org.bombercraft2.game.misc.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.drawable_line.BasicDrawableLine;
import org.play_ground.misc.drawable_line.EntityStartDrawableLine;
import utils.math.BVector2f;

import java.awt.*;

public class BotWorker extends Bot {
    public final float             usage       = 1f;
    private      BasicDrawableLine line;
    private      float             experiences = 0;
    @Nullable
    private      Task              actTask;

    public BotWorker(@NotNull BVector2f position, @NotNull GameAble parent) {
        super(position, parent, BotFactory.Types.WORKER, Direction.getRandomDirection());
    }

    public void finish() {
        direction = Direction.getRandomDirection();
        actTask = null;
    }

    @Override
    public boolean isFree() {
        return actTask == null;
    }

    public void setTask(Task task) {
        actTask = task;
        line = new EntityStartDrawableLine(parent, this, task.getPosition().getAdd(StaticConfig.BLOCK_SIZE_HALF));
    }

    private boolean isTooCloseToTask() {
        return actTask == null || actTask.getPosition().dist(getPosition()) < StaticConfig.BLOCK_SIZE.avg() / 10;
    }

    @Override
    public void update(float delta) {
        if (actTask != null) {
            if (isTooCloseToTask()) {
                experiences += actTask.work(delta * usage);
            }
            else {
                position = position.getAdd(actTask.getPosition().getSub(getPosition()).Normalized().getMul(getSpeed() * 5));
                line.update(delta);
            }
        }
        else {
            position = position.getAdd(direction.getDirection().getMul(getSpeed()));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        final BVector2f size = StaticConfig.BLOCK_SIZE.getMul(parent.getZoom());
        final BVector2f pos = position.getMul(parent.getZoom()).getSub(parent.getOffset());

        if (actTask != null && !isTooCloseToTask()) {
            line.render(g2);
        }

        g2.setColor(Color.BLUE);
        g2.fillArc(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }
}
