package org.playGround.Misc.drawableLine;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class EntityEndDrawableLine extends BasicDrawableLine {
    @NotNull
    private Entity endTarget;

    public EntityEndDrawableLine(@NotNull SimpleGameAble parent,
                                 @NotNull GVector2f start,
                                 @NotNull Entity endTarget
                                ) {
        super(parent);
        this.start = start;
        this.endTarget = endTarget;
    }

    @NotNull
    protected GVector2f getEndPos() {
        return endTarget.getPosition().add(endTarget.getSize().div(2));
    }
}
