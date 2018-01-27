package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Helper;
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
