package org.bombercraft2.gui2.core.translation;

public class TranslateInt extends AbstractTranslate<Integer> {
    public TranslateInt(Integer startValue, Integer endValue, long duration) {
        super(startValue, endValue, duration);
    }

    @Override
    protected Integer linerInterpolation(Integer startValue, Integer endValue, float ratio) {
        return (int) ((ratio * startValue) + ((1 - ratio) * endValue));
    }

}
