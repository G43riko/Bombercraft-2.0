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
import utils.math.GVector2f;

import java.util.List;

public interface Connector {
    void onPlayerChange(@NotNull JSONObject data);

    void setPlayerChange(@NotNull Player player);

    void onRemoveBlock(@NotNull JSONObject data);

    void setRemoveBlock(@NotNull GVector2f position);

    void onBuildBlock(@NotNull JSONObject data);

    void setBuildBlock(@NotNull GVector2f position, @NotNull BlockType type);

    void setBuildBlockArea(@NotNull GVector2f minPosition, @NotNull GVector2f maxPosition, @NotNull BlockType type);

    default void onBombExplode(@NotNull JSONObject data) {}

    default void setBombExplode(@NotNull GVector2f position,
                                @NotNull List<Block> blocks,
                                @NotNull List<GVector2f> damageAreas
                               ) {}

    void onPutHelper(@NotNull JSONObject data);

    void setPutHelper(@NotNull GVector2f pos, @NotNull Helper.Type type);

    //	void hitBlock(GVector2f position, int damage);
    default void onHitPlayer(@NotNull String name, int damage) {}

    /**
     * Tato funkcia sa vola pri presusenie hry aby sa dalo ostatnym hracom vediet ze bola hra prerusena
     */
    void setCloseConnection();

    void cleanUp();

    void setPutEmitter(@NotNull EmitterTypes emitterOnHit, @NotNull GVector2f position);

    boolean bulletHitEnemy(@NotNull Bullet bulletInstance);

    void hitBlock(@NotNull GVector2f position, int damage);

    void onPutBullet(@NotNull JSONObject data);

    void setPutBullet(@NotNull MyPlayer myPlayer, @NotNull ShootAble shooter);
}
