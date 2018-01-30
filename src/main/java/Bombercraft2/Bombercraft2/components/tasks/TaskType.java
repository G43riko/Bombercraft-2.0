package Bombercraft2.Bombercraft2.components.tasks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public abstract class TaskType {
    @Nullable
    private GVector2f position;

    @NotNull
    public abstract GVector2f getPosition();

    public enum Types {
        BUILD,
        DESTROY
    }
    protected Types type;
    public abstract void finish();
}
