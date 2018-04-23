package Bombercraft2.Bombercraft2.game.player.placers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.BlockType;
import Bombercraft2.Bombercraft2.game.player.ToolAble;

import java.awt.*;

public abstract class Placer implements ToolAble {
    public final GameAble   parent;
    public BlockType blockType;

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
