package utils.math;

import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @deprecated use {@link org.glib2.math.vectors.GVector2f} instead
 */
@Deprecated
public final class BVector2f implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x, y;

    public BVector2f() {
        this(0, 0);
    }

    public BVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public BVector2f(@NotNull String s) {
        s = s.replace("[", "").replace("]", "").replace("x", "_");
        String[] strings = s.split("_");

        this.x = Float.parseFloat(strings[0]);
        this.y = Float.parseFloat(strings[1]);
    }

    public BVector2f(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public BVector2f(@NotNull BVector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public static BVector2f fromGVector(GVector2f vec) {
        if (vec == null) {
            return null;
        }
        return new BVector2f(vec.getX(), vec.getY());
    }

    @NotNull
    public BVector2f toInt() {
        return new BVector2f(Math.floor(x), Math.floor(y));
    }

    public GVector2f toGVector() {
        return new GVector2f(x, y);
    }

    @NotNull
    public BVector2f min(@NotNull BVector2f v) {
        return new BVector2f(Math.min(x, v.x), Math.min(y, v.y));
    }

    @NotNull
    public BVector2f max(@NotNull BVector2f v) {
        return new BVector2f(Math.max(x, v.x), Math.max(y, v.y));
    }

    @Contract(pure = true)
    private float dot(@NotNull BVector2f v) {
        return x * v.x + y * v.y;
    }

    @Contract(pure = true)
    public boolean atLeastOneSame(@NotNull BVector2f v) {
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
    public BVector2f Normalized() {
        float length = this.getLength();
        return new BVector2f(x / length, y / length);
    }

    @Contract(pure = true)
    public float cross(@NotNull BVector2f r) {
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
    public float dist(@NotNull BVector2f v) {
        //return distance between 2 point
        float dx = x - v.x;
        float dy = y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @NotNull
    @Contract(pure = true)
    public BVector2f Lerp(@NotNull BVector2f dest, float lerpFactor) {
        return dest.getSub(this).getMul(lerpFactor).getAdd(this);
    }

    @Contract(pure = true)
    public float distSQ(@NotNull BVector2f v) {
        float distX = x - v.x;
        float distY = y - v.y;
        return distX * distX + distY * distY;
    }

    @NotNull
    public BVector2f negate() {
        this.x *= -1;
        this.y *= -1;
        return this;
    }

    @Contract(pure = true)
    public float angleBetween(@NotNull BVector2f v) {
        float dotProduct = dot(v);
        return (float) Math.acos(dotProduct);
    }

    @NotNull
    public BVector2f getAdd(@NotNull BVector2f v) {
        return new BVector2f(x + v.x, y + v.y);
    }

    @NotNull
    public BVector2f getAdd(float num) {
        return new BVector2f(x + num, y + num);
    }

    @NotNull
    public BVector2f getSub(@NotNull BVector2f v) {
        return new BVector2f(x - v.x, y - v.y);
    }

    @NotNull
    public BVector2f getSub(float num) {
        return new BVector2f(x - num, y - num);
    }

    @NotNull
    public BVector2f getMul(@NotNull BVector2f v) {
        return new BVector2f(x * v.x, y * v.y);
    }

    @NotNull
    public BVector2f getMul(float num) {
        return new BVector2f(x * num, y * num);
    }

    @NotNull
    public BVector2f getDiv(@NotNull BVector2f v) {
        return new BVector2f(x / v.x, y / v.y);
    }

    @NotNull
    public BVector2f getDiv(float num) {
        return new BVector2f(x / num, y / num);
    }

    @NotNull
    public BVector2f getMod(@NotNull BVector2f v) {
        return new BVector2f(x % v.x, y % v.y);
    }

    @NotNull
    public BVector2f getMod(float num) {
        return new BVector2f(x % num, y % num);
    }

    @NotNull
    public BVector2f getAbs() {
        return new BVector2f(Math.abs(x), Math.abs(y));
    }

    @Contract(pure = true)
    public float avg() {
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

    public void set(@NotNull BVector2f a) {set(a.x, a.y);}

    @NotNull
    @Contract(pure = true)
    public String toString() {
        return "[" + this.x + "x" + this.y + "]";
    }

    /*
        public static BVector2f interpolateLinear(float scale, BVector2f startValue, BVector2f endValue) {
            BVector2f result = new BVector2f();
    //	    result.setX(GMath.interpolateLinear(scale, startValue.x, endValue.x));
    //	    result.setY(GMath.interpolateLinear(scale, startValue.y, endValue.y));
            return result;
        }
    */
    @NotNull
    @Contract(pure = true)
    public BVector2f getInstance() {
        return new BVector2f(this);
    }

    @Contract(pure = true)
    public boolean equals(@Nullable BVector2f v) {
        return v != null && x == v.x && y == v.y;
    }

    @NotNull
    @Contract(pure = true)
    public String toDecimal(int i) {
        return "[" + String.format("%0" + i + "d ", (int) x) + "x" + String.format("%0" + i + "d ", (int) y) + "]";
    }
}
