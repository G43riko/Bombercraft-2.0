package org.bombercraft2.components.tasks;

import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
