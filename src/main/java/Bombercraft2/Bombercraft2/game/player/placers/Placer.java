package Bombercraft2.Bombercraft2.game.player.placers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.Toolable;

import java.awt.*;

public abstract class Placer implements Toolable {
    public enum Types {
        SIMPLE("Simple"),
        AREA("Area");

        private String label;

        Types(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    protected GameAble   parent;
    protected Block.Type blockType;

    public Placer(GameAble parent) {
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
