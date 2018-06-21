package org.bombercraft2.game.player;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.Iconable;
import org.bombercraft2.game.entity.Helper;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class Shovel implements ToolAble, Iconable {
    private final GameAble parent;

    public Shovel(GameAble parent) {
        this.parent = parent;
    }

    @NotNull
    @Override
    public Image getImage() {
        return Helper.Type.BOMB_NORMAL.getImage();
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        parent.getConnector().setRemoveBlock(pos);
    }


}
