package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.misc.GCanvas;
import Bombercraft2.engine.Input;
import sun.misc.GC;
import utils.math.GVector2f;

import java.awt.*;

class MouseSelector {
    private final int      selectorWidth = Config.PLAYER_SELECTOR_WIDTH;
    private final Color    selectorColor = Config.PLAYER_SELECTOR_COLOR;
    private final GameAble parent;

    public MouseSelector(GameAble parent) {
        this.parent = parent;
    }

    public void render(Graphics2D g2) {
        GVector2f pos = Input.getMousePosition().div(Config.BLOCK_SIZE).toInt().mul(Config.BLOCK_SIZE);
        pos = pos.mul(parent.getZoom()).sub(parent.getOffset().mod(Config.BLOCK_SIZE));
        GVector2f size = Config.BLOCK_SIZE.mul(parent.getZoom());

        GCanvas.drawRect(g2, pos, size, selectorColor, selectorWidth);
        GCanvas.fillRect(g2, pos.sub(2), new GVector2f(4, 4));
        // g2.setStroke(new BasicStroke(selectorWidth));
        // g2.setColor(selectorColor);
        // g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
        // g2.fillRect(pos.getXi() - 2, pos.getYi() - 2, 4, 4);
    }
}
