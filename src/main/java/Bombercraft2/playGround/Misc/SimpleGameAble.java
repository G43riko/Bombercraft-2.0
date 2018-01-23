package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.core.Visible;
import utils.math.GVector2f;

public interface SimpleGameAble {
    float getZoom();

    GVector2f getOffset();

    boolean isVisible(Visible b);

    default GVector2f getCanvasSize() {
        return null;
    }
}
