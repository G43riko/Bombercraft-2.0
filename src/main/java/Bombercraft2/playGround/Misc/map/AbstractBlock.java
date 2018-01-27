package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.playGround.Misc.SimpleGameAble;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public class AbstractBlock implements Visible {
    @NotNull
    protected final SimpleGameAble parent;

    @NotNull
    protected GVector2f position;

    @Nullable
    protected final GVector2f offset;

    public AbstractBlock(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
        this(position, parent, null);
    }

    public AbstractBlock(@NotNull GVector2f position,
                         @NotNull SimpleGameAble parent,
                         @Nullable GVector2f offset
                        ) {
        this.parent = parent;
        this.position = position;
        this.offset = offset;
    }


    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getPosition() {
        return offset == null ? position.mul(Block.SIZE) : position.mul(Block.SIZE).add(offset);
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getSize() {
        return Block.SIZE;
    }
}
