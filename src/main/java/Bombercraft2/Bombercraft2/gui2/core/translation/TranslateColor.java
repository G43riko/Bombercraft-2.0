package Bombercraft2.Bombercraft2.gui2.core.translation;

import utils.Utils;

import java.awt.*;

public class TranslateColor extends AbstractTranslate<Color> {
    public TranslateColor(Color startColor, Color endColor, long duration) {
        super(startColor, endColor, duration);
    }

    @Override
    protected Color linerInterpolation(Color startValue, Color endValue, float ratio) {
        return Utils.lerpColor(startValue, endValue, ratio);
    }

    public Color getActualColor() {
        return getActualValue();
    }

    public Color getStartColor() {return startValue;}

    public Color getEndColor() {return endValue;}

    public void setEndColor(Color endColor) {endValue = endColor;}

    public void setStartColor(Color startColor) {startValue = startColor;}
}
