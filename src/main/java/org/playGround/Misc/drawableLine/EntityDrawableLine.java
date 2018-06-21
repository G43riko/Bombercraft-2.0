package org.playGround.Misc.drawableLine;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.SimpleGameAble;
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
