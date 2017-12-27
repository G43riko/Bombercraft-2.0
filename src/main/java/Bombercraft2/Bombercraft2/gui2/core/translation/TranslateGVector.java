package Bombercraft2.Bombercraft2.gui2.core.translation;

import utils.math.GVector2f;

public class TranslateGVector extends AbstractTranslate<GVector2f> {
    public TranslateGVector(GVector2f startColor, GVector2f endColor, long duration) {
        super(startColor, endColor, duration);
    }

    @Override
    protected GVector2f linerInterpolation(GVector2f startValue, GVector2f endValue, float ratio) {
        final float x = (ratio * startValue.getX()) + ((1 - ratio) * endValue.getX());
        final float y = (ratio * startValue.getY()) + ((1 - ratio) * endValue.getY());
        return new GVector2f(x, y);
    }

    public GVector2f getActualColor() {
        return getActualValue();
    }

    public GVector2f getStartColor() {return startValue;}

    public GVector2f getEndColor() {return endValue;}

    public void setEndColor(GVector2f endColor) {endValue = endColor;}

    public void setStartColor(GVector2f startColor) {startValue = startColor;}
}
