package org.bombercraft2.game.player;

import org.bombercraft2.game.level.Map;
import org.glib2.math.vectors.GVector2f;

import java.awt.*;

public interface ToolAble {
    default void onSelect() {}

    default void onUnselect() {}

    default void render(Graphics2D g2) {}

    default void useOnLocalPos(GVector2f pos) {}

    default void useOnGlobalPos(GVector2f pos) {useOnLocalPos(Map.globalPosToLocalPos(pos));}

    default void useOnScreenPos(GVector2f pos, GVector2f offset) {useOnGlobalPos(pos.getAdd(offset));}
}
