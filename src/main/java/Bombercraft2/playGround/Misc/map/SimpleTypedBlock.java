package Bombercraft2.playGround.Misc.map;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.map.AbstractBlock;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
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
        final GVector2f size = Config.BLOCK_SIZE.mul(parent.getZoom());
        final GVector2f realPos = position.mul(size);
        GVector2f pos = (offset == null ? realPos : realPos.add(offset.mul(parent.getZoom()))).sub(parent.getOffset());

        g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi() + 1, size.getYi() + 1, null);
    }


    @Override
    public void fromJSON(@NotNull JSONObject json) {
        super.fromJSON(json);
        JSONWrapper(() -> {
            String type = json.getString(Texts.TYPE);
            try {
                this.type = Block.Type.valueOf(type);
            } catch (IllegalArgumentException e) {
                GLogger.error(GLogger.GError.UNKNOWN_BLOCK_TYPE, e, type);
            }
        });
    }

    @Override
    public @NotNull JSONObject toJSON() {
        JSONObject result = super.toJSON();

        JSONWrapper(() -> {
            result.put(Texts.TYPE, type);
        });

        return result;
    }

    @Contract(pure = true)
    @NotNull
    public Block.Type getType() {
        return type;
    }

    @Override
    @Contract(pure = true)
    public String toString() {
        return "(" + position + ") " +type;
    }

    public void remove() {
        type = Block.Type.GRASS;
    }
}
