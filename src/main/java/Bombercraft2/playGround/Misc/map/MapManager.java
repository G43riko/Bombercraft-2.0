package Bombercraft2.playGround.Misc.map;
import Bombercraft2.playGround.Misc.AbstractManager;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class MapManager extends AbstractManager{
    private final AbstractMap map;

    public MapManager(AbstractMap map) {
        this.map = map;
    }

    public SimpleTypedBlock getTypedBlock(int i, int j) {
        return (SimpleTypedBlock)map.getBlockOnPos(new GVector2f(i, j));
    }

    public SimpleTypedBlock getBlockOnAbsolutePos(GVector2f click) {
        return (SimpleTypedBlock)map.getBlockOnAbsolutePos(click);
    }


    public GVector2f getFreePosition() {
        final GVector2f mapSize = map.getMapSize();
        while(true) {
            GVector2f result = new GVector2f(Math.random() * mapSize.getX(), Math.random() * mapSize.getY());
            SimpleTypedBlock block = getBlockOnAbsolutePos(result);
            if (block != null && block.getType().isWalkable()) {
                return result;
            }
        }
    }

    public void render(@NotNull Graphics2D g2) {
        map.render(g2);
    }

}
