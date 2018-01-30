package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

public class EntityDrawableLine extends BasicDrawableLine {
    @NotNull
    private Entity startTarget;
    @NotNull
    private Entity endTarget;

    public EntityDrawableLine(@NotNull SimpleGameAble parent,
                              @NotNull Entity startTarget,
                              @NotNull Entity endTarget
                             ) {
        super(parent);
        this.startTarget = startTarget;
        this.endTarget = endTarget;
    }

    @NotNull
    protected GVector2f getStartPos() {
        return startTarget.getPosition().add(startTarget.getSize().div(2));
    }

    @NotNull
    protected GVector2f getEndPos() {
        return endTarget.getPosition().add(endTarget.getSize().div(2));
    }
}
