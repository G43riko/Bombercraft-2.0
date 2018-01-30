package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

import java.awt.*;
import java.util.Map;

import Bombercraft2.playGround.Misc.SimpleGameAble;

public class SimpleChunkedMap extends AbstractMap<SimpleChunk> {
    public SimpleChunkedMap(SimpleGameAble parent, GVector2f numberOfChunks) {
        super(parent, numberOfChunks);
        createRandomMap();
    }

    public void render(@NotNull Graphics2D g2) {
        items.entrySet().stream().map(Map.Entry::getValue).filter(parent::isVisible).forEach(a -> a.render(g2));
    }

    protected void createRandomMap() {
        for (int i = 0; i < numberOfItems.getXi(); i++) {
            for (int j = 0; j < numberOfItems.getYi(); j++) {
                addItem(i, j, new SimpleChunk(this, new GVector2f(i, j)));
            }
        }
    }

    @Nullable
    @Override
    public SimpleTypedBlock getBlockOnAbsolutePos(GVector2f click) {
        final GVector2f pos = click.add(parent.getOffset());
        final SimpleChunk chunk = getItem(pos.div(SimpleChunk.SIZE).div(parent.getZoom()));
        return chunk == null ? null : chunk.getBlock(pos.div(parent.getZoom()).mod(SimpleChunk.SIZE).div(Config.BLOCK_SIZE));
    }
}