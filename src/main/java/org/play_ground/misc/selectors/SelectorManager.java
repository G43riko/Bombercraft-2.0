package org.play_ground.misc.selectors;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.misc.GCanvas;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.AbstractManager;
import org.play_ground.misc.PlayerManager;
import org.play_ground.misc.SimpleGameAble;
import org.play_ground.misc.bots.SimpleMyPlayer;
import org.play_ground.misc.map.SimpleTypedBlock;
import utils.math.GVector2f;

import java.awt.*;

public class SelectorManager extends AbstractManager {
    @NotNull
    private final SimpleGameAble parent;
    private       Color          selectorColor = StaticConfig.PLAYER_SELECTOR_COLOR;
    private       int            selectorWidth = StaticConfig.PLAYER_SELECTOR_WIDTH;
    @Nullable
    private       SelectAble     selector;

    public SelectorManager(@NotNull SimpleGameAble parent) {
        this(parent, null);
    }

    public SelectorManager(@NotNull SimpleGameAble parent, @Nullable SelectAble selector) {
        this.selector = selector;
        this.parent = parent;
    }

    public GVector2f getStartPosition() {
        PlayerManager manager = parent.getManager().getPlayerManager();
        if (manager == null) {
            return Input.getMousePosition();
        }
        SimpleMyPlayer player = manager.getMyPlayer();
        if (player == null) {
            return Input.getMousePosition();
        }

        return player.getCenter();
    }

    public void setSelector(SelectAble selector) {
        this.selector = selector;
    }

    public GVector2f getTargetPosition() {
        GVector2f startPosition = getStartPosition();
        if (selector == null) {
            return startPosition;
        }
        return selector.getPosition(startPosition);
    }

    public void render(@NotNull Graphics2D g2) {
        SimpleTypedBlock block = parent.getManager()
                                       .getMapManager()
                                       .getBlockOnAbsolutePos(getTargetPosition().sub(parent.getManager()
                                                                                            .getViewManager()
                                                                                            .getOffset()));
        GVector2f pos = block.getPosition()
                             .sub(parent.getManager().getViewManager().getOffset().mod(StaticConfig.BLOCK_SIZE));

        GCanvas.drawRect(g2, pos, new GVector2f(60, 60), selectorColor, selectorWidth);
        // g2.setColor(selectorColor);
        // g2.setStroke(new BasicStroke(selectorWidth));
        // g2.drawRect(pos.getXi(), pos.getYi(), 60, 60);
    }


}
