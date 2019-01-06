package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.SimpleGameAble;

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
        return offset == null ? position.getMul(StaticConfig.BLOCK_SIZE) : position.getMul(StaticConfig.BLOCK_SIZE)
                .getAdd(offset);
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getSize() {
        return StaticConfig.BLOCK_SIZE;
    }
}
