package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.ShootAble;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
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

    void setBuildBlock(@NotNull GVector2f position, @NotNull Type type);

    void setBuildBlockArea(@NotNull GVector2f minPosition, @NotNull GVector2f maxPosition, @NotNull Type type);

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

    void setPutEmitter(@NotNull Types emitterOnHit, @NotNull GVector2f position);

    boolean bulletHitEnemy(@NotNull Bullet bulletInstance);

    void hitBlock(@NotNull GVector2f position, int damage);

    void onPutBullet(@NotNull JSONObject data);

    void setPutBullet(@NotNull MyPlayer myPlayer, @NotNull ShootAble shooter);
}
