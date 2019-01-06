package org.bombercraft2.game.player.placers;

import org.bombercraft2.game.GameAble;
import org.glib2.math.vectors.GVector2f;

public class SimplePlacer extends Placer {

    public SimplePlacer(GameAble parent) {
        super(parent);
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        parent.getConnector().setBuildBlock(pos, blockType);

    }

}
