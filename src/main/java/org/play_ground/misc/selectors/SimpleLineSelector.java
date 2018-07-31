package org.play_ground.misc.selectors;

import org.engine.Input;
import utils.math.GVector2f;

public class SimpleLineSelector implements SelectAble {
    private boolean showSelectedBlock = true;

    @Override
    public GVector2f getPosition(GVector2f startPosition) {
        return Input.getMousePosition();
    }

    @Override
    public void showSelectedBlock(boolean value) {
        showSelectedBlock = value;
    }
}
