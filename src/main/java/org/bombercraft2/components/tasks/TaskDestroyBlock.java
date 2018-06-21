package org.bombercraft2.components.tasks;

import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.map.SimpleTypedBlock;
import utils.math.GVector2f;

public class TaskDestroyBlock extends TaskType {
    @NotNull
    private SimpleTypedBlock block;

    public TaskDestroyBlock(@NotNull SimpleTypedBlock block) {
        this.type = Types.DESTROY;
        this.block = block;
    }

    @NotNull
    @Override
    public GVector2f getPosition() {
        return block.getPosition();
    }

    @Override
    public void finish() {
        block.remove();
    }
}
