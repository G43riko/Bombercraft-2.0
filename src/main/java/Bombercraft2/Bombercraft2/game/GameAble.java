package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameAble extends InteractAble, Visible, SimpleGameAble {

    Canvas getCanvas();

    JSONObject getWeapon(String weaponLaser);

    boolean getVisibleOption(String key);

    Player getPlayerByName(String name);

    String getLabelOf(String key);

    GVector2f getPlayerDirection();

    SceneManager getSceneManager();

    ToolManager getToolsManager();

    GVector2f getPlayerTarget();

    Connector getConnector();

    String getBasicInfo();

    ArrayList<String> getLogInfo();

    MyPlayer getMyPlayer();

    String getGameInfo();

    Profile getProfile();

    Level getLevel();

    void addBullet(BulletManager.Types bulletType, GVector2f angle, GVector2f position);

    void addExplosion(GVector2f position,
                      GVector2f size,
                      Color color,
                      int number,
                      boolean explosion,
                      boolean shockWave
                     );

    /**
     * @param pos         - position of explosion
     * @param type        - type of explosion
     * @param createdTime - time when bomb was created by client - prevent network delay
     */
    void addHelper(GVector2f pos, Type type, long createdTime);

    void addEmitter(GVector2f position, Emitter.Types type);

    void addEnemy(GVector2f position, String type);

    void addPlayer(String name, String image);

    void explodeBombAt(GVector2f globalPosToLocalPos);

    void switchVisibleOption(String key);

    boolean hasWall(float i, float j);

    void removePlayer(String name);

    void changeZoom(float f);

    void calcPosition();

    void resetGame();

    void newGame();

    JSONObject toJSON();

    void endGame();

    HashMap<String, String> getStats();


}
