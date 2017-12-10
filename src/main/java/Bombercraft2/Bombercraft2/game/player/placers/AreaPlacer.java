package Bombercraft2.Bombercraft2.game.player.placers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

import java.awt.*;

public class AreaPlacer extends Placer {
    private final static Color     AREA_PLACER_FILL_COLOR = new Color(255, 173, 43, 150);
    private final static int       AREA_PLACER_OFFSET     = -5;
    private final static int       AREA_PLACER_ROUND      = 10;
    private              GVector2f starPos                = null;

    public AreaPlacer(GameAble parent) {
        super(parent);
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        if (starPos == null) {
            starPos = pos;
        }
        else {
            GVector2f minPos = pos.min(starPos);
            GVector2f maxPos = pos.max(starPos);

            parent.getConnector().setBuildBlockArea(minPos, maxPos, blockType);

            starPos = null;
        }

    }

    @Override
    public void render(Graphics2D g2) {
        if (starPos == null) {
            return;
        }

        GVector2f globalPosStart = starPos.mul(Block.SIZE).sub(parent.getOffset());
        GVector2f globalPosEnd = parent.getPlayerTarget().sub(parent.getOffset()).add(Block.SIZE);
        GVector2f size = globalPosEnd.sub(globalPosStart).div(Block.SIZE).toInt().mul(Block.SIZE);


//		System.out.println("size: " + size + ", globalPosStart: " + globalPosStart + ", globalPosEnd: " + globalPosEnd);
//		
        if (size.getX() <= 0) {
            globalPosStart.addToX(size.getX() - Block.SIZE.getX());
            size.addToX(-Block.SIZE.getX() * 2);
        }
        if (size.getY() <= 0) {
            globalPosStart.addToY(size.getY() - Block.SIZE.getY());
            size.addToY(-Block.SIZE.getY() * 2);
        }


        g2.setColor(AREA_PLACER_FILL_COLOR);
        globalPosStart = globalPosStart.add(AREA_PLACER_OFFSET);
        size = size.abs().sub(AREA_PLACER_OFFSET * 2);

        g2.fillRoundRect(globalPosStart.getXi(),
                         globalPosStart.getYi(),
                         size.getXi(),
                         size.getYi(),
                         AREA_PLACER_ROUND,
                         AREA_PLACER_ROUND);
    }
}
