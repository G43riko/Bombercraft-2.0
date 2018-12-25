package utils.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @deprecated use {@link org.glib2.math.vectors.GVector2f} instead
 */
@Deprecated
public final class GVector2f implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x, y;

    public GVector2f() {
        this(0, 0);
    }

    public GVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public GVector2f toInt() {
        return new GVector2f(Math.floor(x), Math.floor(y));
    }

    public GVector2f(@NotNull String s) {
        s = s.replace("[", "").replace("]", "").replace("x", "_");
        String[] strings = s.split("_");

        this.x = Float.parseFloat(strings[0]);
        this.y = Float.parseFloat(strings[1]);
    }

    public GVector2f(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public GVector2f(@NotNull GVector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    @NotNull
    public GVector2f min(@NotNull GVector2f v) {
        return new GVector2f(Math.min(x, v.x), Math.min(y, v.y));
    }

    @NotNull
    public GVector2f max(@NotNull GVector2f v) {
        return new GVector2f(Math.max(x, v.x), Math.max(y, v.y));
    }

    @Contract(pure = true)
    private float dot(@NotNull GVector2f v) {
        return x * v.x + y * v.y;
    }

    @Contract(pure = true)
    public boolean atLeastOneSame(@NotNull GVector2f v) {
        return v.x == x || v.y == y;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    @Contract(pure = true)
    public float max() {
        return Math.max(this.x, this.y);
    }

    @Contract(pure = true)
    public float min() {
        return Math.min(this.x, this.y);
    }

    public void normalize() {
        float length = this.getLength();
        this.x /= length;
        this.y /= length;
    }

    @NotNull
    public GVector2f Normalized() {
        float length = this.getLength();
        return new GVector2f(x / length, y / length);
    }

    @Contract(pure = true)
    public float cross(@NotNull GVector2f r) {
        return x * r.y - y * r.x;
    }

    public void rotate(float angle) {
        float rad = (float) Math.toRadians(angle);

        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        this.x = (x * cos - y * sin);
        this.y = (x * sin + y * cos);
    }

    @Contract(pure = true)
    public float dist(@NotNull GVector2f v) {
        //return distance between 2 point
        float dx = x - v.x;
        float dy = y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @NotNull
    @Contract(pure = true)
    public GVector2f Lerp(@NotNull GVector2f dest, float lerpFactor) {
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    @Contract(pure = true)
    public float distSQ(@NotNull GVector2f v) {
        float distX = x - v.x;
        float distY = y - v.y;
        return distX * distX + distY * distY;
    }

    @NotNull
    public GVector2f negate() {
        this.x *= -1;
        this.y *= -1;
        return this;
    }

    @Contract(pure = true)
    public float angleBetween(@NotNull GVector2f v) {
        float dotProduct = dot(v);
        return (float) Math.acos(dotProduct);
    }

    @NotNull
    public GVector2f add(@NotNull GVector2f v) {
        return new GVector2f(x + v.x, y + v.y);
    }

    @NotNull
    public GVector2f add(float num) {
        return new GVector2f(x + num, y + num);
    }

    @NotNull
    public GVector2f sub(@NotNull GVector2f v) {
        return new GVector2f(x - v.x, y - v.y);
    }

    @NotNull
    public GVector2f sub(float num) {
        return new GVector2f(x - num, y - num);
    }

    @NotNull
    public GVector2f mul(@NotNull GVector2f v) {
        return new GVector2f(x * v.x, y * v.y);
    }

    @NotNull
    public GVector2f mul(float num) {
        return new GVector2f(x * num, y * num);
    }

    @NotNull
    public GVector2f div(@NotNull GVector2f v) {
        return new GVector2f(x / v.x, y / v.y);
    }

    @NotNull
    public GVector2f div(float num) {
        return new GVector2f(x / num, y / num);
    }

    @NotNull
    public GVector2f mod(@NotNull GVector2f v) {
        return new GVector2f(x % v.x, y % v.y);
    }

    @NotNull
    public GVector2f mod(float num) {
        return new GVector2f(x % num, y % num);
    }

    @NotNull
    public GVector2f abs() {
        return new GVector2f(Math.abs(x), Math.abs(y));
    }

    @Contract(pure = true)
    public float average() {
        return (x + y) / 2;
    }

    @Contract(pure = true)
    public float sum() {
        return x + y;
    }

    @Contract(pure = true)
    public boolean isNull() {
        return x == 0 && y == 0;
    }

    @Contract(pure = true)
    public float mul() {return x * y;}

    @Contract(pure = true)
    public float add() {return x + y;}

    @Contract(pure = true)
    public float getX() {return x;}

    @Contract(pure = true)
    public float getY() {return y;}

    @Contract(pure = true)
    public int getXi() {return (int) x;}

    @Contract(pure = true)
    public int getYi() {return (int) y;}

    public void setX(float x) {this.x = x;}

    public void setY(float y) {this.y = y;}

    public void addToY(float y) {this.y += y;}

    public void addToX(float x) {this.x += x;}

    public void set(float x, float y) {this.x = x; this.y = y;}

    public void set(@NotNull GVector2f a) {set(a.x, a.y);}

    @NotNull
    @Contract(pure = true)
    public String toString() {
        return "[" + this.x + "x" + this.y + "]";
    }

    /*
        public static GVector2f interpolateLinear(float scale, GVector2f startValue, GVector2f endValue) {
            GVector2f result = new GVector2f();
    //	    result.setX(GMath.interpolateLinear(scale, startValue.x, endValue.x));
    //	    result.setY(GMath.interpolateLinear(scale, startValue.y, endValue.y));
            return result;
        }
    */
    @NotNull
    @Contract(pure = true)
    public GVector2f getInstance() {
        return new GVector2f(this);
    }

    @Contract(pure = true)
    public boolean equals(@Nullable GVector2f v) {
        return v != null && x == v.x && y == v.y;
    }

    @NotNull
    @Contract(pure = true)
    public String toDecimal(int i) {
        return "[" + String.format("%0" + i + "d ", (int) x) + "x" + String.format("%0" + i + "d ", (int) y) + "]";
    }

    @Contract(pure = true)
    public boolean isInRect(@NotNull GVector2f aPos, @NotNull GVector2f aSize) {
        return x > aPos.x && x < aPos.x + aSize.x && y > aPos.y && y < aPos.y + aSize.y;
    }
}
