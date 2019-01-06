package org.bombercraft2.components.tasks;

import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.map.SimpleTypedBlock;

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
