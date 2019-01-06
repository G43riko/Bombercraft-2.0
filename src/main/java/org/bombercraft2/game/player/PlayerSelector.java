package org.bombercraft2.game.player;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.misc.GCanvas;
import utils.BombercraftUtils;
import utils.math.BVector2f;

import java.awt.*;

public class PlayerSelector {
    private final MyPlayer parent;
    private final int      selectorWidth = StaticConfig.PLAYER_SELECTOR_WIDTH;
    private final Color    selectorColor = StaticConfig.PLAYER_SELECTOR_COLOR;

    public PlayerSelector(MyPlayer parent) {
        this.parent = parent;
    }

    public void render(Graphics2D g2) {
        BVector2f pos = getSelectorPos().getMul(parent.getParent().getZoom()).getSub(parent.getParent().getOffset());
        BVector2f size = StaticConfig.BLOCK_SIZE.getMul(parent.getParent().getZoom());
        GCanvas.drawRect(g2, pos, size, selectorColor, selectorWidth);
    }

    private BVector2f getSelectorPos() {
        BVector2f pos = parent.getPosition().getAdd(StaticConfig.BLOCK_SIZE_HALF).getDiv(StaticConfig.BLOCK_SIZE).toInt();
        pos = pos.getAdd(BombercraftUtils.getNormalMoveFromDir(parent.getDirection())).getMul(StaticConfig.BLOCK_SIZE);
        return pos;
    }
}
