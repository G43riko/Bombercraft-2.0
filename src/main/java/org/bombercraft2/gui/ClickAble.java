package org.bombercraft2.gui;

import org.glib2.math.vectors.GVector2f;

/**
 * @deprecated use {@link org.glib2.interfaces.Clicable} instead
 */
@Deprecated
public interface ClickAble {
    default void doAct(GVector2f click) {}
}
