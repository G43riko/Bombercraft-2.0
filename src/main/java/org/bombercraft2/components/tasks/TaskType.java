package org.bombercraft2.components.tasks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public abstract class TaskType {
    protected Types     type;
    @Nullable
    private   GVector2f position;

    @NotNull
    public abstract GVector2f getPosition();

    public abstract void finish();

    public enum Types {
        BUILD,
        DESTROY
    }
}
