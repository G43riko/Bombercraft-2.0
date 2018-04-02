package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

import java.awt.*;
import java.util.List;
import java.util.Map;

import Bombercraft2.playGround.Misc.SimpleGameAble;

public class SimpleChunkedMap extends AbstractMap<SimpleChunk> {
    private int renderedChunks = 0;

    public SimpleChunkedMap(SimpleGameAble parent, GVector2f numberOfChunks) {
        super(parent, numberOfChunks, numberOfChunks.mul(Config.BLOCK_SIZE).mul(Config.CHUNK_SIZE));
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
                addItem(i, j, new SimpleChunk(this, new GVector2f(i, j)));
            }
        }
    }


    @Nullable
    @Override
    public SimpleTypedBlock getBlockOnAbsolutePos(GVector2f click) {
        final GVector2f transformedPosition = parent.getManager().getViewManager().transformInvert(click);
        final SimpleChunk chunk = getItem(transformedPosition.div(SimpleChunk.SIZE));
        return chunk == null ? null : chunk.getBlock(transformedPosition.mod(SimpleChunk.SIZE).div(Config.BLOCK_SIZE));
    }

    @Override
    public AbstractBlock getBlockOnPos(GVector2f click) {
        final SimpleChunk chunk = getItem(click.div(Config.CHUNK_SIZE));
        return chunk == null ? null : chunk.getBlock(click.mod(Config.CHUNK_SIZE));
    }
}