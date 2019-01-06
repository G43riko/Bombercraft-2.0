package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

public class AbstractBlock extends Entity<SimpleGameAble> {

    @Nullable
    protected final BVector2f offset;

    public AbstractBlock(@NotNull BVector2f position, @NotNull SimpleGameAble parent) {
        this(position, parent, null);
    }

    public AbstractBlock(@NotNull BVector2f position,
                         @NotNull SimpleGameAble parent,
                         @Nullable BVector2f offset
                        ) {
        super(position, parent);
        this.offset = offset;
    }


    @NotNull
    @Override
    @Contract(pure = true)
    public BVector2f getPosition() {
        return offset == null ? position.getMul(StaticConfig.BLOCK_SIZE) : position.getMul(StaticConfig.BLOCK_SIZE)
                .getAdd(offset);
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public BVector2f getSize() {
        return StaticConfig.BLOCK_SIZE;
    }
}
