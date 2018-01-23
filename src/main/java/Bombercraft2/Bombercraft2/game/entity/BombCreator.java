package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.level.Map;
import Bombercraft2.Bombercraft2.game.player.ToolAble;
import utils.math.GVector2f;

public class BombCreator implements ToolAble {
    private final GameAble parent;
    private final Type     type;

    public BombCreator(GameAble parent, Type type) {
        this.parent = parent;
        this.type = type;
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        useOnGlobalPos(Map.localPosToGlobalPos(pos));
    }

    @Override
    public void useOnGlobalPos(GVector2f pos) {
        //bombu chceme položiť iba tam kde sa da
        if (parent.getLevel().getMap().getBlockOnPosition(pos).isWalkable()) {
            parent.getConnector().setPutHelper(pos, type);
        }
    }
}
