package org.bombercraft2.game.player;

import org.bombercraft2.game.level.Map;
import utils.math.BVector2f;

import java.awt.*;

public interface ToolAble {
    default void onSelect() {}

    default void onUnselect() {}

    default void render(Graphics2D g2) {}

    default void useOnLocalPos(BVector2f pos) {}

    default void useOnGlobalPos(BVector2f pos) {useOnLocalPos(Map.globalPosToLocalPos(pos));}

    default void useOnScreenPos(BVector2f pos, BVector2f offset) {useOnGlobalPos(pos.getAdd(offset));}
}
