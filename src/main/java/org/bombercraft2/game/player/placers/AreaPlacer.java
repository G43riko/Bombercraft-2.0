package org.bombercraft2.game.player.placers;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
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

        GVector2f globalPosStart = starPos.mul(StaticConfig.BLOCK_SIZE).sub(parent.getOffset());
        GVector2f globalPosEnd = parent.getPlayerTarget().sub(parent.getOffset()).add(StaticConfig.BLOCK_SIZE);
        GVector2f size = globalPosEnd.sub(globalPosStart)
                                     .div(StaticConfig.BLOCK_SIZE)
                                     .toInt()
                                     .mul(StaticConfig.BLOCK_SIZE);


//		System.out.println("size: " + size + ", globalPosStart: " + globalPosStart + ", globalPosEnd: " + globalPosEnd);
//		
        if (size.getX() <= 0) {
            globalPosStart.addToX(size.getX() - StaticConfig.BLOCK_SIZE.getX());
            size.addToX(-StaticConfig.BLOCK_SIZE.getX() * 2);
        }
        if (size.getY() <= 0) {
            globalPosStart.addToY(size.getY() - StaticConfig.BLOCK_SIZE.getY());
            size.addToY(-StaticConfig.BLOCK_SIZE.getY() * 2);
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