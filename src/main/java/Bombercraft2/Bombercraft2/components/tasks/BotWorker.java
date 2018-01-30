package Bombercraft2.Bombercraft2.components.tasks;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.Bot;
import Bombercraft2.Bombercraft2.game.bots.BotFactory;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.playGround.Misc.drawableLine.BasicDrawableLine;
import Bombercraft2.playGround.Misc.drawableLine.EntityStartDrawableLine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

import java.awt.*;

public class BotWorker extends Bot {
    private BasicDrawableLine line;
    public final float usage       = 1f;
    private      float experiences = 0;
    @Nullable
    private Task actTask;

    public BotWorker(@NotNull GVector2f position, @NotNull GameAble parent) {
        super(position, parent, BotFactory.Types.WORKER, Player.Direction.getRandomDirection());
    }

    public void finish() {
        direction = Player.Direction.getRandomDirection();
        actTask = null;
    }

    @Override
    public boolean isFree() {
        return actTask == null;
    }

    public void setTask(Task task) {
        actTask = task;
        line = new EntityStartDrawableLine(parent, this, task.getPosition().add(Config.BLOCK_SIZE_HALF));
    }

    private boolean isTooCloseToTask() {
        return actTask == null || actTask.getPosition().dist(getPosition()) < Config.BLOCK_SIZE.average() / 10;
    }

    @Override
    public void update(float delta) {
        if (actTask != null) {
            if (isTooCloseToTask()) {
                experiences += actTask.work(delta * usage);
            }
            else {
                position = position.add(actTask.getPosition().sub(getPosition()).Normalized().mul(getSpeed() * 5));
                line.update(delta);
            }
        }
        else {
            position = position.add(direction.getDirection().mul(getSpeed()));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        final GVector2f size = Config.BLOCK_SIZE.mul(parent.getZoom());
        final GVector2f pos = position.mul(parent.getZoom()).sub(parent.getOffset());

        if (actTask != null && !isTooCloseToTask()) {
            line.render(g2, parent.getZoom());
        }

        g2.setColor(Color.BLUE);
        g2.fillArc(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), 0, 360);
    }
}
