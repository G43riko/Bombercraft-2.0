package org.bombercraft2.core;

import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated use {@link org.glib2.interfaces.Visible} instead
 */
@Deprecated
public interface Visible {
    @NotNull
    @Contract(pure = true)
    GVector2f getPosition();

    @NotNull
    @Contract(pure = true)
    GVector2f getSize();
}
