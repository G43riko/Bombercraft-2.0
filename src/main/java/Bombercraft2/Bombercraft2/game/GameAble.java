package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.entity.particles.EmitterTypes;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameAble extends InteractAble, Visible, SimpleGameAble {

    Canvas getCanvas();

    @NotNull
    JSONObject getWeapon(@NotNull String weaponLaser);

    boolean getVisibleOption(@NotNull String key);

    @NotNull
    Player getPlayerByName(@NotNull String name);

    @NotNull
    String getLabelOf(@NotNull String key);

    @NotNull
    GVector2f getPlayerDirection();

    @NotNull
    SceneManager getSceneManager();

    @NotNull
    ToolManager getToolsManager();

    @NotNull
    GVector2f getPlayerTarget();

    @NotNull
    Connector getConnector();

    @NotNull
    String getBasicInfo();

    @NotNull
    MyPlayer getMyPlayer();

    @NotNull
    String getGameInfo();

    @NotNull
    Profile getProfile();

    @NotNull
    Level getLevel();

    void addBullet(@NotNull BulletManager.Types bulletType, @NotNull GVector2f angle, @NotNull GVector2f position);

    void addExplosion(@NotNull GVector2f position,
                      @NotNull GVector2f size,
                      @NotNull Color color,
                      int number,
                      boolean explosion,
                      boolean shockWave
                     );

    /**
     * @param pos         - position of explosion
     * @param type        - type of explosion
     * @param createdTime - time when bomb was created by client - prevent network delay
     */
    void addHelper(@NotNull GVector2f pos, @NotNull Type type, long createdTime);

    void addEmitter(@NotNull GVector2f position, @NotNull EmitterTypes type);

    void addEnemy(@NotNull GVector2f position, @NotNull String type);

    void addPlayer(@NotNull String name, @NotNull String image);

    void explodeBombAt(@NotNull GVector2f globalPosToLocalPos);

    void switchVisibleOption(@NotNull String key);

    boolean hasWall(float i, float j);

    void removePlayer(@NotNull String name);

    void changeZoom(float f);

    void calcPosition();

    void resetGame();

    void newGame();

    @NotNull
    JSONObject toJSON();

    void endGame();

    @NotNull
    HashMap<String, String> getStats();


}
