package org.play_ground.misc.drawable_line;

import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;

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
        return startTarget.getPosition().getAdd(startTarget.getSize().getDiv(2));
    }
}
