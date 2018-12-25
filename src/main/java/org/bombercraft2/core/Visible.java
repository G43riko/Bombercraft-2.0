package org.bombercraft2.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

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
