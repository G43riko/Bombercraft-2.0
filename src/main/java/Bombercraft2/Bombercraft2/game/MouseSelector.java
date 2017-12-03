package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

import java.awt.*;

public class MouseSelector {
    private final int      selectorWidth = Config.PLAYER_SELECTOR_WIDTH;
    private final Color    selectorColor = Config.PLAYER_SELECTOR_COLOR;
    private       GameAble parent        = null;

    public MouseSelector(GameAble parent) {
        this.parent = parent;
    }

    public void render(Graphics2D g2) {
        GVector2f pos = Input.getMousePosition().div(Block.SIZE).toInt().mul(Block.SIZE);
        pos = pos.mul(parent.getZoom()).sub(parent.getOffset().mod(Block.SIZE));
        GVector2f size = Block.SIZE.mul(parent.getZoom());
        g2.setStroke(new BasicStroke(selectorWidth));
        g2.setColor(selectorColor);
        g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
        g2.fillRect(pos.getXi() - 2, pos.getYi() - 2, 4, 4);
    }
}
