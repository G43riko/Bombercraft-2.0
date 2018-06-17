package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.playGround.Misc.SimpleGameAble;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public class AbstractBlock extends Entity<SimpleGameAble> {

    @Nullable
    protected final GVector2f offset;

    public AbstractBlock(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
        this(position, parent, null);
    }

    public AbstractBlock(@NotNull GVector2f position,
                         @NotNull SimpleGameAble parent,
                         @Nullable GVector2f offset
                        ) {
        super(position, parent);
        this.offset = offset;
    }


    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getPosition() {
        return offset == null ? position.mul(StaticConfig.BLOCK_SIZE) : position.mul(StaticConfig.BLOCK_SIZE).add(offset);
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getSize() {
        return StaticConfig.BLOCK_SIZE;
    }
}
