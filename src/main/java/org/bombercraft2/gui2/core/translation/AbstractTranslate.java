package org.bombercraft2.gui2.core.translation;

public abstract class AbstractTranslate<T> {
    protected T    startValue;
    protected T    endValue;
    private   long firstHover = 0;
    private   long lastHover;
    private   long duration;

    public AbstractTranslate(T startValue, T endValue, long duration) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        lastHover = System.currentTimeMillis() - duration;
    }

    protected abstract T linerInterpolation(T startValue, T endValue, float ratio);

    private long processValue(long last, long result) {
        final long diff = duration - (result - last);
        return diff > 0 ? result - diff : result;
    }

    public boolean setValue(boolean value) {
        final long time = System.currentTimeMillis();
        if (value) {
            if (firstHover == 0) {
                firstHover = processValue(lastHover, time);
                lastHover = 0;
                return true;
            }
        }
        else {
            if (lastHover == 0) {
                lastHover = processValue(firstHover, time);
                firstHover = 0;
                return true;
            }
        }
        return time - Math.max(lastHover, firstHover) < duration;
    }

    public T getActualValue() {
        if (this.lastHover == 0) { //je hover
            final float ratio = ((float) (System.currentTimeMillis() - this.firstHover)) / duration;
            return linerInterpolation(endValue, startValue, Math.max(0, Math.min(ratio, 1)));
        }
        else {
            final float ratio = ((float) (System.currentTimeMillis() - this.lastHover)) / duration;
            return linerInterpolation(startValue, endValue, Math.max(0, Math.min(ratio, 1)));
        }
    }


    public boolean isChanged() {
        return System.currentTimeMillis() - Math.max(lastHover, firstHover) < duration;
    }

    public T getStartValue() {return startValue;}

    public void setStartValue(T startValue) {this.startValue = startValue;}

    public T getEndValue() {return endValue;}

    public void setEndValue(T endValue) {this.endValue = endValue;}

    public long getDuration() {return duration;}

    public void setDuration(long duration) {this.duration = duration;}
}
