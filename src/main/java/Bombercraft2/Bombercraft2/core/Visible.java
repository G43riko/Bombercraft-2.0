package Bombercraft2.Bombercraft2.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

public interface Visible {
    @NotNull
    @Contract(pure = true)
    GVector2f getPosition();

    @NotNull
    @Contract(pure = true)
    GVector2f getSize();
}
