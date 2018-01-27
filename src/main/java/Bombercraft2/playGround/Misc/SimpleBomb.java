package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.function.Consumer;

public class SimpleBomb extends Entity<SimpleGameAble> {
    @Nullable
    public        Consumer<SimpleBomb> callback;
    private final long                 addedAt;
    private final   int range          = 3;
    protected final int detonationTime = 2000;

    public SimpleBomb(@NotNull GVector2f position, @NotNull SimpleGameAble parent) {
        this(position, parent, System.currentTimeMillis());
    }

    public SimpleBomb(@NotNull GVector2f position, @NotNull SimpleGameAble parent, long addedAt) {
        super(position, parent);
        this.addedAt = addedAt;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!alive) {
            return;
        }
        GVector2f actPos = position.sub(getParent().getOffset());

        g2.setColor(Color.BLACK);
        g2.fillArc(actPos.getXi(), actPos.getYi(), Config.BOMB_WIDTH, Config.BOMB_HEIGHT, 0, 360);
    }

    @Override
    public void update(float delta) {
        if (addedAt + detonationTime < System.currentTimeMillis()) {
            explode();
        }
    }

    private void explode() {
        alive = false;
        if (callback != null) {
            callback.accept(this);
        }
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }


}
