package org.bombercraft2.game.player.placers;

import org.bombercraft2.game.GameAble;
import utils.math.BVector2f;

public class SimplePlacer extends Placer {

    public SimplePlacer(GameAble parent) {
        super(parent);
    }

    @Override
    public void useOnLocalPos(BVector2f pos) {
        parent.getConnector().setBuildBlock(pos, blockType);

    }

}
