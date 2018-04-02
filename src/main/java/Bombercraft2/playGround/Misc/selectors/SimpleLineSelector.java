package Bombercraft2.playGround.Misc.selectors;

import Bombercraft2.engine.Input;
import utils.math.GVector2f;

public class SimpleLineSelector implements SelectAble{
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
