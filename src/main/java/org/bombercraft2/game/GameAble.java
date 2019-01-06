package org.bombercraft2.game;

import org.bombercraft2.Profile;
import org.bombercraft2.core.Visible;
import org.bombercraft2.game.entity.Helper.Type;
import org.bombercraft2.game.entity.bullets.BulletManager;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.level.Level;
import org.bombercraft2.game.player.MyPlayer;
import org.bombercraft2.game.player.Player;
import org.bombercraft2.multiplayer.Connector;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;
import utils.math.BVector2f;

import java.awt.*;
import java.util.HashMap;

public interface GameAble extends InteractAbleG2, Visible, SimpleGameAble {

    Canvas getCanvas();

    @NotNull
    JSONObject getWeapon(@NotNull String weaponLaser);

    boolean getVisibleOption(@NotNull String key);

    @NotNull
    Player getPlayerByName(@NotNull String name);

    @NotNull
    String getLabelOf(@NotNull String key);

    @NotNull
    BVector2f getPlayerDirection();

    @NotNull
    SceneManager getSceneManager();

    @NotNull
    ToolManager getToolsManager();

    @NotNull
    BVector2f getPlayerTarget();

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

    void addBullet(@NotNull BulletManager.Types bulletType, @NotNull BVector2f angle, @NotNull BVector2f position);

    void addExplosion(@NotNull BVector2f position,
                      @NotNull BVector2f size,
                      @NotNull Color color,
                      int number,
                      boolean explosion,
                      boolean shockWave
                     );

    /**
     * @param pos         - scale of explosion
     * @param type        - type of explosion
     * @param createdTime - time when bomb was created by client - prevent network delay
     */
    void addHelper(@NotNull BVector2f pos, @NotNull Type type, long createdTime);

    void addEmitter(@NotNull BVector2f position, @NotNull EmitterTypes type);

    void addEnemy(@NotNull BVector2f position, @NotNull String type);

    void addPlayer(@NotNull String name, @NotNull String image);

    void explodeBombAt(@NotNull BVector2f globalPosToLocalPos);

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
