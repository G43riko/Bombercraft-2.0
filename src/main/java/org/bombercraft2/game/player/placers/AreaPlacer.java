package org.bombercraft2.game.player.placers;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.glib2.math.vectors.GVector2f;

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

        GVector2f globalPosStart = starPos.getMul(StaticConfig.BLOCK_SIZE).getSub(parent.getOffset());
        GVector2f globalPosEnd = parent.getPlayerTarget().getSub(parent.getOffset()).getAdd(StaticConfig.BLOCK_SIZE);
        GVector2f size = globalPosEnd.getSub(globalPosStart)
                .getDiv(StaticConfig.BLOCK_SIZE)
                                     .toInt()
                .getMul(StaticConfig.BLOCK_SIZE);


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
        globalPosStart = globalPosStart.getAdd(AREA_PLACER_OFFSET);
        size = size.getAbs().getSub(AREA_PLACER_OFFSET * 2);

        g2.fillRoundRect(globalPosStart.getXi(),
                         globalPosStart.getYi(),
                         size.getXi(),
                         size.getYi(),
                         AREA_PLACER_ROUND,
                         AREA_PLACER_ROUND);
    }
}
