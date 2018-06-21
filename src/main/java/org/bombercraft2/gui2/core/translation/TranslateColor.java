package org.bombercraft2.gui2.core.translation;

import org.glib2.math.GMath;

import java.awt.*;

public class TranslateColor extends AbstractTranslate<Color> {
    public TranslateColor(Color startColor, Color endColor, long duration) {
        super(startColor, endColor, duration);
    }

    @Override
    protected Color linerInterpolation(Color startValue, Color endValue, float ratio) {
        return GMath.lerpColor(startValue, endValue, ratio);
    }

    public Color getActualColor() {
        return getActualValue();
    }

    public Color getStartColor() {return startValue;}

    public void setStartColor(Color startColor) {startValue = startColor;}

    public Color getEndColor() {return endValue;}

    public void setEndColor(Color endColor) {endValue = endColor;}
}