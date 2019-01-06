package org.play_ground.misc.selectors;

import org.glib2.math.vectors.GVector2f;

public interface SelectAble {
    GVector2f getPosition(GVector2f startPosition);

    void showSelectedBlock(boolean value);
}
