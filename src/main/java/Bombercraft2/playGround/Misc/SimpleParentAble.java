package Bombercraft2.playGround.Misc;

import java.awt.Canvas;

import Bombercraft2.Bombercraft2.core.Visible;
import utils.math.GVector2f;

public interface SimpleParentAble {
	float getZoom();
	GVector2f getOffset();
	boolean isVisible(Visible b);
	Canvas getCanvas();
}
