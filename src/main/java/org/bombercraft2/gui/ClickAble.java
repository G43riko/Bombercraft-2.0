package org.bombercraft2.gui;

import utils.math.GVector2f;

public interface ClickAble {
    default void doAct(GVector2f click) {}
}
