package utils.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LineLineIntersect {
    @Contract(pure = true)
    private boolean sameString(long a, long b) {
        return ((a ^ b) >= 0);
    }

    public static GVector2f linesIntersect(@NotNull GVector2f a,
                                           @NotNull GVector2f b,
                                           @NotNull GVector2f c,
                                           @NotNull GVector2f d
                                          ) {
        return new LineLineIntersect().linesIntersect((long) a.getX(), (long) a.getY(),
                                                      (long) b.getX(), (long) b.getY(),
                                                      (long) c.getX(), (long) c.getY(),
                                                      (long) d.getX(), (long) d.getY());
    }

    @Nullable
    private GVector2f linesIntersect(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        long a1, a2, b1, b2, c1, c2; /* Coefficients of line eqns. */
        long r1, r2, r3, r4;         /* 'Sign' values */
        long denom, offset, num;     /* Intermediate values */
        long x, y;

        a1 = y2 - y1;
        b1 = x1 - x2;
        c1 = x2 * y1 - x1 * y2;

        r3 = a1 * x3 + b1 * y3 + c1;
        r4 = a1 * x4 + b1 * y4 + c1;

        if (r3 != 0 && r4 != 0 && sameString(r3, r4)) { return null; }

        a2 = y4 - y3;
        b2 = x3 - x4;
        c2 = x4 * y3 - x3 * y4;

        r1 = a2 * x1 + b2 * y1 + c2;
        r2 = a2 * x2 + b2 * y2 + c2;

        if (r1 != 0 && r2 != 0 && sameString(r1, r2)) { return null; }

        denom = a1 * b2 - a2 * b1;
        if (denom == 0) { return null; }

        offset = denom < 0 ? -denom / 2 : denom / 2;

        num = b1 * c2 - b2 * c1;
        x = (num < 0 ? num - offset : num + offset) / denom;

        num = a2 * c1 - a1 * c2;
        y = (num < 0 ? num - offset : num + offset) / denom;

        return new GVector2f(x, y);
    }
}
