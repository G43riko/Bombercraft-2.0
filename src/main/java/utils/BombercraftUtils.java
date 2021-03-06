package utils;

import org.bombercraft2.game.misc.Direction;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

public class BombercraftUtils {
    @NotNull
    public static GVector2f getNormalMoveFromDir(@NotNull Direction dir) {
        switch (dir) {
            case LEFT:
                return new GVector2f(-1, 0);
            case RIGHT:
                return new GVector2f(1, 0);
            case UP:
                return new GVector2f(0, -1);
            case DOWN:
                return new GVector2f(0, 1);
            default:
                return new GVector2f();
        }
    }
}
