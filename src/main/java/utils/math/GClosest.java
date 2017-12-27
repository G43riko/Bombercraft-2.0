package utils.math;

public class GClosest {
    public static GVector2f getClosestPointOnLine(float sx1, float sy1, float sx2, float sy2, float px, float py) {
        double xDelta = sx2 - sx1;
        double yDelta = sy2 - sy1;

        double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final GVector2f closestPoint;
        if (u < 0) {
            closestPoint = new GVector2f(sx1, sy1);
        }
        else if (u > 1) {
            closestPoint = new GVector2f(sx2, sy2);
        }
        else {
            closestPoint = new GVector2f(sx1 + u * xDelta, sy1 + u * yDelta);
        }

        return closestPoint;
    }
}
