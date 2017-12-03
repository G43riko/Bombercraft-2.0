package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameAble extends Interactable, Visible {
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

    GVector2f getOffset();

    Canvas getCanvas();

    Profile getProfile();

    Level getLevel();

    float getZoom();

    void addBullet(BulletManager.Types bulletType, GVector2f angle, GVector2f position);

    void addExplosion(GVector2f position,
                             GVector2f size,
                             Color color,
                             int number,
                             boolean explosion,
                             boolean shockwave
                            );

    /**
     * @param pos - pozicia explozie
     * @param type - typ explozie
     * @param createdTime - cas vytvorenie objektu u klienta pre pripadny delay siete
     */
    void addHelper(GVector2f pos, Type type, long createdTime);

    void addEmitter(GVector2f position, Emitter.Types type);

    void addEnemy(GVector2f position, String type);

    void addPlayer(String name, String image);

    void explodeBombAt(GVector2f globalPosToLocalPos);

    void switchVisibleOption(String key);

    boolean hasWall(float i, float j);

    void removePlayer(String name);

    boolean isVisible(Visible b);

    void changeZoom(float f);

    void calcPosition();

    void resetGame();

    void newGame();

    JSONObject toJSON();

    void endGame();

    HashMap<String, String> getStats();


}
