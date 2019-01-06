package org.play_ground.misc.drawable_line;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

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
    protected BVector2f getStartPos() {
        return startTarget.getPosition().getAdd(startTarget.getSize().getDiv(2));
    }

    @NotNull
    protected BVector2f getEndPos() {
        return endTarget.getPosition().getAdd(endTarget.getSize().getDiv(2));
    }
}
