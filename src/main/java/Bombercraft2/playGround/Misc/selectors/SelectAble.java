package Bombercraft2.playGround.Misc.selectors;

import utils.math.GVector2f;

public interface SelectAble {
    GVector2f getPosition(GVector2f startPosition);

    void showSelectedBlock(boolean value);
}
