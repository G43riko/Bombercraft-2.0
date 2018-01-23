package Bombercraft2.playGround.Misc;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.level.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public class SimpleTypedBlock extends AbstractBlock {

    @NotNull
    protected Block.Type type;

    public SimpleTypedBlock(@NotNull GVector2f position, int type, @NotNull SimpleGameAble parent) {
        this(position, type, parent, null);
    }

    public SimpleTypedBlock(@NotNull GVector2f position,
                            int type,
                            @NotNull SimpleGameAble parent,
                            @Nullable GVector2f offset
                           ) {
        super(position, parent, offset);
        this.type = Block.getTypeFromInt(type);
    }

    public void render(@NotNull Graphics2D g2) {
        final GVector2f size = Block.SIZE.mul(parent.getZoom());
        final GVector2f realPos = position.mul(size);
        GVector2f pos = (offset == null ? realPos : realPos.add(offset.mul(parent.getZoom()))).sub(parent.getOffset());

        g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi() + 1, size.getYi() + 1, null);
    }

    @Contract(pure = true)
    @NotNull
    public Block.Type getType() {
        return type;
    }
}
