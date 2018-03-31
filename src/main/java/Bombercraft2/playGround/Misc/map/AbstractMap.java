package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.playGround.Misc.SimpleGameAble;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Components
 *  - ChunkAble
 *  - PostEffects
 * @param <T>
 */
public abstract class AbstractMap<T extends Visible> implements InteractAble {
    @NotNull
    protected final HashMap<String, T> items = new HashMap<>();

    @NotNull
    protected final SimpleGameAble parent;

    @NotNull
    protected final GVector2f numberOfItems;

    @NotNull
    protected final GVector2f mapSize;


    protected int renderedBlocks = 0;


    public AbstractMap(@NotNull SimpleGameAble parent, @NotNull GVector2f numberOfItems, @NotNull GVector2f mapSize) {
        this.numberOfItems = numberOfItems;
        this.parent = parent;
        this.mapSize = mapSize;
    }
    public GVector2f getBlockSize() {
        return Config.BLOCK_SIZE;
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

    public GVector2f getMapSize() {
        return mapSize;
    }
    public int getRenderedBlocks() {
        return renderedBlocks;
    }
    @Nullable
    protected T getItem(GVector2f pos) {
        return items.get(pos.getXi() + "_" + pos.getYi());
    }

    @NotNull
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("map size: " + getMapSize().toDecimal(3));
        result.add("rendered blocks: " + getRenderedBlocks());
        return result;
    }

    public abstract <S extends AbstractBlock> S getBlockOnAbsolutePos(GVector2f click);
    public abstract <S extends AbstractBlock> S getBlockOnPos(GVector2f click);
}
