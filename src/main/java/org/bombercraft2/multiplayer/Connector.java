package org.bombercraft2.multiplayer;

import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.ShootAble;
import org.bombercraft2.game.entity.bullets.Bullet;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.level.Block;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.player.MyPlayer;
import org.bombercraft2.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.BVector2f;

import java.util.List;

public interface Connector {
    void onPlayerChange(@NotNull JSONObject data);

    void setPlayerChange(@NotNull Player player);

    void onRemoveBlock(@NotNull JSONObject data);

    void setRemoveBlock(@NotNull BVector2f position);

    void onBuildBlock(@NotNull JSONObject data);

    void setBuildBlock(@NotNull BVector2f position, @NotNull BlockType type);

    void setBuildBlockArea(@NotNull BVector2f minPosition, @NotNull BVector2f maxPosition, @NotNull BlockType type);

    default void onBombExplode(@NotNull JSONObject data) {}

    default void setBombExplode(@NotNull BVector2f position,
                                @NotNull List<Block> blocks,
                                @NotNull List<BVector2f> damageAreas
                               ) {}

    void onPutHelper(@NotNull JSONObject data);

    void setPutHelper(@NotNull BVector2f pos, @NotNull Helper.Type type);

    //	void hitBlock(BVector2f scale, int damage);
    default void onHitPlayer(@NotNull String name, int damage) {}

    /**
     * Tato funkcia sa vola pri presusenie hry aby sa dalo ostatnym hracom vediet ze bola hra prerusena
     */
    void setCloseConnection();

    void cleanUp();

    void setPutEmitter(@NotNull EmitterTypes emitterOnHit, @NotNull BVector2f position);

    boolean bulletHitEnemy(@NotNull Bullet bulletInstance);

    void hitBlock(@NotNull BVector2f position, int damage);

    void onPutBullet(@NotNull JSONObject data);

    void setPutBullet(@NotNull MyPlayer myPlayer, @NotNull ShootAble shooter);
}
