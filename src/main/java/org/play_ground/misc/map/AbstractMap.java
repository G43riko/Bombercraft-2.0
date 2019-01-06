package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Visible;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Components
 * - ChunkAble
 * - PostEffects
 *
 * @param <T>
 */
public abstract class AbstractMap<T extends Visible> implements InteractAbleG2 {
    @NotNull
    protected final HashMap<String, T> items = new HashMap<>();

    @NotNull
    protected final SimpleGameAble parent;

    @NotNull
    protected final BVector2f numberOfItems;

    @NotNull
    protected final BVector2f mapSize;


    protected int renderedBlocks = 0;


    public AbstractMap(@NotNull SimpleGameAble parent, @NotNull BVector2f numberOfItems, @NotNull BVector2f mapSize) {
        this.numberOfItems = numberOfItems;
        this.parent = parent;
        this.mapSize = mapSize;
    }

    public BVector2f getBlockSize() {
        return StaticConfig.BLOCK_SIZE;
    }

    @NotNull
    @Contract(pure = true)
    public SimpleGameAble getParent() {
        return parent;
    }

    protected abstract void createRandomMap();

    protected void addItem(int i, int j, @NotNull T block) {
        items.put(i + "_" + j, block);
    }

    @Nullable
    protected T getItem(int i, int j) {
        return items.get(i + "_" + j);
    }

    public BVector2f getMapSize() {
        return mapSize;
    }

    public int getRenderedBlocks() {
        return renderedBlocks;
    }

    @Nullable
    protected T getItem(BVector2f pos) {
        return items.get(pos.getXi() + "_" + pos.getYi());
    }

    @NotNull
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("map size: " + getMapSize().toDecimal(3));
        result.add("rendered blocks: " + getRenderedBlocks());
        return result;
    }

    public abstract <S extends AbstractBlock> S getBlockOnAbsolutePos(BVector2f click);

    public abstract <S extends AbstractBlock> S getBlockOnPos(BVector2f click);
}
