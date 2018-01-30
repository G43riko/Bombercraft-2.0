package Bombercraft2.playGround.Misc.drawableLine;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
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
