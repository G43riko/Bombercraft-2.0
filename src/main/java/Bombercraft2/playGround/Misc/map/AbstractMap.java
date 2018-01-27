package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.playGround.Misc.SimpleGameAble;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.util.HashMap;

public abstract class AbstractMap<T extends Visible> implements InteractAble {
    @NotNull
    protected final SimpleGameAble parent;

    @NotNull
    protected final GVector2f numberOfItems;

    protected final HashMap<String, T> items = new HashMap<>();

    public AbstractMap(@NotNull SimpleGameAble parent, @NotNull GVector2f numberOfItems) {
        this.numberOfItems = numberOfItems;
        this.parent = parent;
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
}