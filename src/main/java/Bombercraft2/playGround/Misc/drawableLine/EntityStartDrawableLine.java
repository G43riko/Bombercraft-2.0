package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
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
