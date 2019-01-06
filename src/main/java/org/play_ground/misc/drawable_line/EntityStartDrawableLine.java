package org.play_ground.misc.drawable_line;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

public class EntityStartDrawableLine extends BasicDrawableLine {
    @NotNull
    private Entity startTarget;

    public EntityStartDrawableLine(@NotNull SimpleGameAble parent,
                                   @NotNull Entity startTarget,
                                   @NotNull BVector2f end
                                  ) {
        super(parent);
        this.startTarget = startTarget;
        this.end = end;
    }

    @NotNull
    protected BVector2f getStartPos() {
        return startTarget.getPosition().getAdd(startTarget.getSize().getDiv(2));
    }
}
