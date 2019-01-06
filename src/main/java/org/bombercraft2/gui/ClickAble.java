package org.bombercraft2.gui;

import utils.math.BVector2f;

/**
 * @deprecated use {@link org.glib2.interfaces.Clicable} instead
 */
@Deprecated
public interface ClickAble {
    default void doAct(BVector2f click) {}
}
