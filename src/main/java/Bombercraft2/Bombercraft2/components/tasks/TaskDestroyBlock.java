package Bombercraft2.Bombercraft2.components.tasks;

import Bombercraft2.playGround.Misc.map.SimpleTypedBlock;
import org.jetbrains.annotations.NotNull;
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
