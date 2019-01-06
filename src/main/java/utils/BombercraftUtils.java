package utils;

import org.bombercraft2.game.misc.Direction;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

public class BombercraftUtils {
    @NotNull
    public static BVector2f getNormalMoveFromDir(@NotNull Direction dir) {
        switch (dir) {
            case LEFT:
                return new BVector2f(-1, 0);
            case RIGHT:
                return new BVector2f(1, 0);
            case UP:
                return new BVector2f(0, -1);
            case DOWN:
                return new BVector2f(0, 1);
            default:
                return new BVector2f();
        }
    }
}
