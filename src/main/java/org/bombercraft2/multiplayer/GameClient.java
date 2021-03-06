package org.bombercraft2.multiplayer;

import org.bombercraft2.core.MenuAble;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.entity.Bomb;
import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.ShootAble;
import org.bombercraft2.game.entity.bullets.Bullet;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.level.Block;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.player.MyPlayer;
import org.bombercraft2.game.player.Player;
import org.bombercraft2.multiplayer.core.Client;
import org.bombercraft2.multiplayer.core.Server;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.utils.logger.GLogger;

import java.util.List;

public class GameClient extends Client implements Connector {
    @NotNull
    private final MenuAble      parent;
    @Nullable
    private       CommonMethods methods = null;


    public GameClient(@NotNull MenuAble coreGame, @NotNull String ip) {
        super(ip);
        this.parent = coreGame;
    }

    //PUTTERS


    @Override
    public void setBombExplode(@NotNull GVector2f position,
                               @NotNull List<Block> blocks,
                               @NotNull List<GVector2f> damageAreas
                              ) {
        parent.getGame().explodeBombAt(position);
    }

    @Override
    public void onBombExplode(@NotNull JSONObject data) {
        try {
            int damage = data.getInt(Texts.DAMAGE);
            JSONArray blocks = data.getJSONArray(Texts.HIT_BLOCKS);
            for (int i = 0; i < blocks.length(); i++) {
                GVector2f position = new GVector2f(blocks.getString(i));
                parent.getGame().getLevel().getMap().getBlock(position.getXi(), position.getYi()).remove();
            }
            JSONArray players = data.getJSONArray(Texts.HIT_PLAYERS);
            for (int i = 0; i < players.length(); i++) {
                parent.getGame().getPlayerByName(players.getString(i)).hit(damage);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getNumberPlayersInGame() {
        throw GLogger.Utils.notImplemented();
    }

    public GVector2f getMyPosition() {
        throw GLogger.Utils.notImplemented();
    }

    public boolean isOnline() {
        throw GLogger.Utils.notImplemented();
    }

    //OTHERS


    public void eatItem(GVector2f sur, int type) {
        GLogger.Utils.notImplementedThrow();
    }

    public void bombExplode(Bomb bomb) {
        GLogger.Utils.notImplementedThrow();
    }

    private void connectToGame() {
        sendPlayerInfo();
    }

    public void onPutHelper(@NotNull JSONObject data) {
        methods.onPutHelper(data);
    }

    protected void processMessage(String data) {
        try {
            JSONObject txt = new JSONObject(data);
            JSONObject msg = new JSONObject(txt.getString(Texts.MESSAGE));

            switch (txt.getString(Texts.TYPE)) {
                case Server.BOMB_EXPLODE:
                    onBombExplode(msg);
                    break;
                case Server.PUT_BULLET:
                    onPutBullet(msg);
                    break;
                case Server.GAME_INFO:
                    parent.createGame(msg);
                    methods = new CommonMethods(parent.getGame(), this);
                    break;
                case Server.BASIC_INFO:
//					actLevel = new Level(new JSONObject(msg.getString("level")));
                    connectToGame();
                    break;
                case Server.CLOSE_CONNECTION:
                    onCloseConnection();
                    break;
                case Server.PLAYER_CHANGE:
                    onPlayerChange(msg);
                    break;
                case Server.BUILD_BLOCK:
                    onBuildBlock(msg);
                    break;
                case Server.REMOVE_BLOCK:
                    onRemoveBlock(msg);
                    break;
                case Server.PUT_HELPER:
                    onPutHelper(msg);
                    break;
                case Server.HIT_BLOCK:
                    //				GVector2f pos = new GVector2f(msg.getString("scale"));
                    //				actLevel.getParent().getLevel().getHashMap().getBlockOnPosition(pos).hit(msg.getInt("damage"));
                    break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendPlayerInfo() {
        write(parent.getPlayerInfo().toString(), Server.PLAYER_DATA);
    }

    @Override
    public void setRemoveBlock(@NotNull GVector2f position) {
        methods.setRemoveBlock(position);
    }

    @Override
    public void setBuildBlock(@NotNull GVector2f position, @NotNull BlockType type) {
        methods.setBuildBlock(position, type);
    }

    @Override
    public void setBuildBlockArea(@NotNull GVector2f minPos, @NotNull GVector2f maxPos, @NotNull BlockType blockType) {
        methods.setBuildBlockArea(minPos, maxPos, blockType);
    }

    @Override
    public void setPutHelper(@NotNull GVector2f position, @NotNull Helper.Type type) {
        methods.setPutHelper(position, type);
    }

    @Override
    public void setPlayerChange(@NotNull Player player) {
        methods.setPlayerChange(player);
    }

    @Override
    protected void onConnectionBroken() {
        parent.showMessage(Texts.BROKEN_CONNECTION);
        parent.stopGame();
    }

    private void onCloseConnection() {
        parent.showMessage(Texts.GAME_CLOSED);
        parent.stopGame();
    }

    @Override
    public void setCloseConnection() {
        write("{}", Server.CLOSE_CONNECTION);
    }

    @Override
    public void onRemoveBlock(@NotNull JSONObject data) {
        methods.onRemoveBlock(data);
    }

    @Override
    public void onBuildBlock(@NotNull JSONObject data) {
        methods.onBuildBlock(data);
    }

    @Override
    public void onPlayerChange(@NotNull JSONObject data) {
        methods.onPlayerChange(data);
    }

    @Override
    public void setPutEmitter(@NotNull EmitterTypes emitterOnHit, @NotNull GVector2f position) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean bulletHitEnemy(@NotNull Bullet bulletInstance) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void hitBlock(@NotNull GVector2f position, int damage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPutBullet(@NotNull MyPlayer myPlayer, @NotNull ShootAble shooter) {
        methods.setPutBullet(myPlayer, shooter);
    }

    @Override
    public void onPutBullet(@NotNull JSONObject data) {
        methods.onPutBullet(data);
    }


}
