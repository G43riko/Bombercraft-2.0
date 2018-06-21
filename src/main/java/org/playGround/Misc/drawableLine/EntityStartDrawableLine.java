package org.playGround.Misc.drawableLine;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class EntityStartDrawableLine extends BasicDrawableLine {
    @NotNull
    private Entity startTarget;

    public EntityStartDrawableLine(@NotNull SimpleGameAble parent,
                                   @NotNull Entity startTarget,
                                   @NotNull GVector2f end
                                  ) {
        super(parent);
        this.startTarget = startTarget;
        this.end = end;
    }

    @NotNull
    protected GVector2f getStartPos() {
        return startTarget.getPosition().add(startTarget.getSize().div(2));
    }
}
