package Bombercraft2.Bombercraft2.game.player.placers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.ToolAble;

import java.awt.*;

public abstract class Placer implements ToolAble {
    public enum Types {
        SIMPLE("Simple"),
        AREA("Area");

        private final String label;

        Types(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    final GameAble   parent;
    Block.Type blockType;

    Placer(GameAble parent) {
        this.parent = parent;
    }

    public void setBlockType(Type blockType) {
        this.blockType = blockType;
    }

    public void render(Graphics2D g2) {}

    public Type getType() {
        return blockType;
    }
}
