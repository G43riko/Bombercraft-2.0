package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.playGround.Misc.SimpleGameAble;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import utils.math.GVector2f;

public class AbstractBlock extends Entity<SimpleGameAble> {

    @Nullable
    protected final GVector2f offset;

    public AbstractBlock(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
        this(position, parent, null);
    }

    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
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
        return offset == null ? position.mul(Config.BLOCK_SIZE) : position.mul(Config.BLOCK_SIZE).add(offset);
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public GVector2f getSize() {
        return Config.BLOCK_SIZE;
    }
}
