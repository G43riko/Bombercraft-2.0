package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.Shootable;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.util.List;

public interface Connector {
    void onPlayerChange(JSONObject data);

    void setPlayerChange(Player player);

    void onRemoveBlock(JSONObject data);

    void setRemoveBlock(GVector2f position);

    void onBuildBlock(JSONObject data);

    void setBuildBlock(GVector2f position, Type type);

    void setBuildBlockArea(GVector2f minPosition, GVector2f maxPosition, Type type);

    default void onBombExplode(JSONObject data) {}

    default void setBombExplode(GVector2f position, List<Block> blocks, List<GVector2f> demageAreas) {}

    void onPutHelper(JSONObject data);

    void setPutHelper(GVector2f pos, Helper.Type type);

    //	void hitBlock(GVector2f position, int demage);
    default void onHitPlayer(String name, int demage) {}

    /**
     * Tato funkcia sa vola pri presusenie hry aby sa dalo ostatnym hracom vediet ze bola hra prerusena
     */
    void setCloseConnection();

    void cleanUp();

    void setPutEmitter(Types emitterOnHit, GVector2f position);

    boolean bulletHitEnemy(Bullet bulletInstance);

    void hitBlock(GVector2f position, int demage);

    void onPutBullet(JSONObject data);

    void setPutBullet(MyPlayer myPlayer, Shootable shooter);


}
