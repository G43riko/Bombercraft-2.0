package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class SimpleChunkedMap extends AbstractMap<SimpleChunk> {
    private int renderedChunks = 0;

    public SimpleChunkedMap(SimpleGameAble parent, BVector2f numberOfChunks) {
        super(parent, numberOfChunks, numberOfChunks.getMul(StaticConfig.BLOCK_SIZE).getMul(StaticConfig.CHUNK_SIZE));
        createRandomMap();
    }

    public void render(@NotNull Graphics2D g2) {
        renderedBlocks = 0;
        renderedChunks = 0;
        items.entrySet().stream().map(Map.Entry::getValue).filter(parent::isVisible).forEach(a -> {
            a.render(g2);
            renderedChunks++;
            renderedBlocks += a.getRenderedBlocks();
        });
    }


    @Override
    @NotNull
    public List<String> getLogInfo() {
        List<String> result = super.getLogInfo();
        result.add("Rendered chunks: " + renderedChunks);
        return result;
    }

    protected void createRandomMap() {
        for (int i = 0; i < numberOfItems.getXi(); i++) {
            for (int j = 0; j < numberOfItems.getYi(); j++) {
                addItem(i, j, new SimpleChunk(this, new BVector2f(i, j)));
            }
        }
    }


    @Nullable
    @Override
    public SimpleTypedBlock getBlockOnAbsolutePos(BVector2f click) {
        final BVector2f transformedPosition = parent.getManager().getViewManager().transformInvert(click);
        final SimpleChunk chunk = getItem(transformedPosition.getDiv(SimpleChunk.SIZE));
        return chunk == null ? null : chunk.getBlock(transformedPosition.getMod(SimpleChunk.SIZE)
                                                             .getDiv(StaticConfig.BLOCK_SIZE));
    }

    @Override
    public AbstractBlock getBlockOnPos(BVector2f click) {
        final SimpleChunk chunk = getItem(click.getDiv(StaticConfig.CHUNK_SIZE));
        return chunk == null ? null : chunk.getBlock(click.getMod(StaticConfig.CHUNK_SIZE));
    }
}