package org.bombercraft2.components.tasks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.BVector2f;

public abstract class TaskType {
    protected Types     type;
    @Nullable
    private   BVector2f position;

    @NotNull
    public abstract BVector2f getPosition();

    public abstract void finish();

    public enum Types {
        BUILD,
        DESTROY
    }
}
