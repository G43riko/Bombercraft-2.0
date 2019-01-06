package org.play_ground.misc.drawable_line;

import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

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
        return endTarget.getPosition().getAdd(endTarget.getSize().getDiv(2));
    }
}
