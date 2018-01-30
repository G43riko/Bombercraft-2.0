package Bombercraft2.playGround.Misc.map;

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
        return null;
    }
}

/*
public class SimpleChunkedMap {
    private HashMap<String, SimpleChunk> chunks = null;
    private final GVector2f      numberOfChunks;
    private final SimpleGameAble parent;

    public SimpleChunkedMap(SimpleGameAble parent, GVector2f numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
        this.parent = parent;
        createMap();
        // createRandomMap();
    }

    public SimpleGameAble getParent() {
        return parent;
    }

    public void render(Graphics2D g2) {
        chunks.entrySet().stream().map(Map.Entry::getValue).filter(parent::isVisible).forEach(a -> a.render(g2));
    }

    private void createMap() {
        chunks = new HashMap<>();
        for (int i = 0; i < numberOfChunks.getXi(); i++) {
            for (int j = 0; j < numberOfChunks.getYi(); j++) {
                addChunks(i, j, new SimpleChunk(this, new GVector2f(i, j)));
            }
        }
    }

    private void addChunks(int i, int j, SimpleChunk chunks) {
        this.chunks.put(i + "_" + j, chunks);
    }
}
*/
