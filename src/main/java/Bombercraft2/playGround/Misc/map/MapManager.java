package Bombercraft2.playGround.Misc.map;
import Bombercraft2.playGround.Misc.AbstractManager;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    @NotNull
    public List<String> getLogInfo() {
        return map.getLogInfo();
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

    @NotNull
    public GVector2f getMapSize() {
        return map.getMapSize();
    }

    public void render(@NotNull Graphics2D g2) {
        map.render(g2);
    }

}
