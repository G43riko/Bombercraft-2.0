package org.playGround.Misc.map;

import org.bombercraft2.StaticConfig;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.AbstractManager;
import utils.math.GVector2f;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class MapManager extends AbstractManager {
    @NotNull
    private final AbstractMap map;

    public MapManager(@NotNull AbstractMap map) {
        this.map = map;
    }

    public SimpleTypedBlock getTypedBlock(int i, int j) {
        return (SimpleTypedBlock) map.getBlockOnPos(new GVector2f(i, j));
    }

    public SimpleTypedBlock getBlockOnAbsolutePos(GVector2f click) {
        return (SimpleTypedBlock) map.getBlockOnAbsolutePos(click);
    }

    @Override
    @NotNull
    public List<String> getLogInfo() {
        return map.getLogInfo();
    }

    public GVector2f getFreePosition() {
        Function<SimpleTypedBlock, Boolean> testBlock = (block) -> block != null && block.getType().isWalkable();
        final GVector2f mapSize = map.getMapSize();
        float offset = StaticConfig.BLOCK_SIZE.average();

        while (true) {
            GVector2f result = new GVector2f(Math.random() * mapSize.getX(), Math.random() * mapSize.getY());
            if (testBlock.apply(getBlockOnAbsolutePos(result)) &&
                    testBlock.apply(getBlockOnAbsolutePos(result.add(new GVector2f(offset, offset)))) &&
                    testBlock.apply(getBlockOnAbsolutePos(result.add(new GVector2f(offset, -offset)))) &&
                    testBlock.apply(getBlockOnAbsolutePos(result.add(new GVector2f(-offset, offset)))) &&
                    testBlock.apply(getBlockOnAbsolutePos(result.add(new GVector2f(-offset, -offset))))) {
                return result;
            }
        }
    }

    @NotNull
    public GVector2f getAABBOnPosition(@NotNull GVector2f position) {
        return position.div(StaticConfig.BLOCK_SIZE).toInt().mul(StaticConfig.BLOCK_SIZE);
    }

    @NotNull
    public GVector2f getMapSize() {
        return map.getMapSize();
    }

    public void render(@NotNull Graphics2D g2) {
        map.render(g2);
    }

    @NotNull
    public AbstractMap getMap() {
        return map;
    }
}
