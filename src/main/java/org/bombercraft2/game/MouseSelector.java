package org.bombercraft2.game;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.misc.GCanvas;
import org.engine.Input;
import utils.math.BVector2f;

import java.awt.*;

class MouseSelector {
    private final int      selectorWidth = StaticConfig.PLAYER_SELECTOR_WIDTH;
    private final Color    selectorColor = StaticConfig.PLAYER_SELECTOR_COLOR;
    private final GameAble parent;

    public MouseSelector(GameAble parent) {
        this.parent = parent;
    }

    public void render(Graphics2D g2) {
        BVector2f pos = Input.getMousePosition().getDiv(StaticConfig.BLOCK_SIZE).toInt().getMul(StaticConfig.BLOCK_SIZE);
        pos = pos.getMul(parent.getZoom()).getSub(parent.getOffset().getMod(StaticConfig.BLOCK_SIZE));
        BVector2f size = StaticConfig.BLOCK_SIZE.getMul(parent.getZoom());

        GCanvas.drawRect(g2, pos, size, selectorColor, selectorWidth);
        GCanvas.fillRect(g2, pos.getSub(2), new BVector2f(4, 4));
        // g2.setStroke(new BasicStroke(selectorWidth));
        // g2.setColor(selectorColor);
        // g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
        // g2.fillRect(pos.getXi() - 2, pos.getYi() - 2, 4, 4);
    }
}
