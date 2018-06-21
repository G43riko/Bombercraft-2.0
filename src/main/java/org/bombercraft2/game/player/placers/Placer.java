package org.bombercraft2.game.player.placers;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.player.ToolAble;

import java.awt.*;

public abstract class Placer implements ToolAble {
    public final GameAble  parent;
    public       BlockType blockType;

    public Placer(GameAble parent) {
        this.parent = parent;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public void render(Graphics2D g2) {}

    public BlockType getType() {
        return blockType;
    }
}
