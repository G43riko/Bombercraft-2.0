package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.game.misc.GCanvas;
import utils.BombercraftUtils;
import utils.math.GVector2f;

import java.awt.*;

public class PlayerSelector {
    private final MyPlayer parent;
    private final int      selectorWidth = StaticConfig.PLAYER_SELECTOR_WIDTH;
    private final Color    selectorColor = StaticConfig.PLAYER_SELECTOR_COLOR;

    public PlayerSelector(MyPlayer parent) {
        this.parent = parent;
    }

    public void render(Graphics2D g2) {
        GVector2f pos = getSelectorPos().mul(parent.getParent().getZoom()).sub(parent.getParent().getOffset());
        GVector2f size = StaticConfig.BLOCK_SIZE.mul(parent.getParent().getZoom());
        GCanvas.drawRect(g2, pos, size, selectorColor, selectorWidth);
    }

    private GVector2f getSelectorPos() {
        GVector2f pos = parent.getPosition().add(StaticConfig.BLOCK_SIZE_HALF).div(StaticConfig.BLOCK_SIZE).toInt();
        pos = pos.add(BombercraftUtils.getNormalMoveFromDir(parent.getDirection())).mul(StaticConfig.BLOCK_SIZE);
        return pos;
    }
}
