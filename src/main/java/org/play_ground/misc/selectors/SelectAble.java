package org.play_ground.misc.selectors;

import utils.math.BVector2f;

public interface SelectAble {
    BVector2f getPosition(BVector2f startPosition);

    void showSelectedBlock(boolean value);
}
