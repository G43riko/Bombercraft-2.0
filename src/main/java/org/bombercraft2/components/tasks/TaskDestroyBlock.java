package org.bombercraft2.components.tasks;

import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.map.SimpleTypedBlock;
import utils.math.BVector2f;

public class TaskDestroyBlock extends TaskType {
    @NotNull
    private SimpleTypedBlock block;

    public TaskDestroyBlock(@NotNull SimpleTypedBlock block) {
        this.type = Types.DESTROY;
        this.block = block;
    }

    @NotNull
    @Override
    public BVector2f getPosition() {
        return block.getPosition();
    }

    @Override
    public void finish() {
        block.remove();
    }
}
