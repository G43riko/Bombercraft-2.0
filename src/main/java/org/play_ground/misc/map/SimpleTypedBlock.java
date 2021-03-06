package org.play_ground.misc.map;

import org.bombercraft2.core.Texts;
import org.bombercraft2.game.level.Block;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.misc.GCanvas;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;
import org.utils.logger.GError;
import org.utils.logger.GLogger;

import java.awt.*;

public class SimpleTypedBlock extends AbstractBlock {
    @NotNull
    protected BlockType type;

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
        final GVector2f size = getTransformedSize();
        final GVector2f realPos = position.getMul(size);
        GVector2f pos = (offset == null ? realPos : realPos.getAdd(offset.getMul(parent.getManager()
                                                                                 .getViewManager()
                                                                                         .getZoom()))).getSub(parent.getManager()
                                                                                                         .getViewManager()
                                                                                                         .getOffset());

        GCanvas.drawImage(g2, type.getImage(), pos, size.getAdd(1));
        // g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi() + 1, size.getYi() + 1, null);
    }


    @Override
    public void fromJSON(@NotNull JSONObject json) {
        super.fromJSON(json);
        jsonWrapper(() -> {
            String type = json.getString(Texts.TYPE);
            try {
                this.type = BlockType.valueOf(type);
            }
            catch (IllegalArgumentException e) {
                GLogger.error(GError.UNKNOWN_BLOCK_TYPE, e, type);
            }
        });
    }

    @Override
    public @NotNull JSONObject toJSON() {
        JSONObject result = super.toJSON();

        jsonWrapper(() -> {
            result.put(Texts.TYPE, type);
        });

        return result;
    }

    @Contract(pure = true)
    @NotNull
    public BlockType getType() {
        return type;
    }

    @Override
    @Contract(pure = true)
    public String toString() {
        return "(" + position + ") " + type;
    }

    public void remove() {
        type = BlockType.GRASS;
    }
}
