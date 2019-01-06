package org.play_ground.misc.drawable_line;

import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

public class EntityEndDrawableLine extends BasicDrawableLine {
    @NotNull
    private Entity endTarget;

    public EntityEndDrawableLine(@NotNull SimpleGameAble parent,
                                 @NotNull BVector2f start,
                                 @NotNull Entity endTarget
                                ) {
        super(parent);
        this.start = start;
        this.endTarget = endTarget;
    }

    @NotNull
    protected BVector2f getEndPos() {
        return endTarget.getPosition().getAdd(endTarget.getSize().getDiv(2));
    }
}
