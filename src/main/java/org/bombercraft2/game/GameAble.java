package org.bombercraft2.game;

import org.bombercraft2.Profile;
import org.bombercraft2.game.entity.Helper.Type;
import org.bombercraft2.game.entity.bullets.BulletManager;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.level.Level;
import org.bombercraft2.game.player.MyPlayer;
import org.bombercraft2.game.player.Player;
import org.bombercraft2.multiplayer.Connector;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.interfaces.Visible;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;

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
     * @param pos         - scale of explosion
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
