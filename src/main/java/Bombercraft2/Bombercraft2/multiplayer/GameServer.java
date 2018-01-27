package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.ShootAble;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.core.ClientPlayer;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.util.List;
import java.util.stream.Collectors;

public class GameServer extends Server implements Connector {
    @NotNull
    private final MenuAble      parent;
    @NotNull
    private final CommonMethods methods;

    public GameServer(@NotNull MenuAble parent) {
        this.parent = parent;

        parent.createGame(null);
        methods = new CommonMethods(parent.getGame(), this);
    }

    @Override
    public void setRemoveBlock(@NotNull GVector2f position) {
        methods.setRemoveBlock(position);
    }

    @Override
    public void setBuildBlock(@NotNull GVector2f position, @NotNull Type type) {
        methods.setBuildBlock(position, type);
    }

    @Override
    public void setBuildBlockArea(@NotNull GVector2f minPos, @NotNull GVector2f maxPos, @NotNull Type type) {
        methods.setBuildBlockArea(minPos, maxPos, type);
    }

    @Override
    public void setBombExplode(@NotNull GVector2f position, @NotNull List<Block> blocks, @NotNull List<GVector2f> damageAreas) {
        final int damage = 1;
        parent.getGame().explodeBombAt(position);
        List<String> hitBlocks = blocks.stream()
                                       .filter(a -> !a.isWalkable())
                                       .peek(Block::remove)
                                       .map(a -> a.getSur().toString())
                                       .collect(Collectors.toList());
        int i = 0;
        JSONArray hitPlayers = new JSONArray();
        while (i < damageAreas.size()) {
            parent.getGame()
                  .getSceneManager()
                  .getPlayersInArea(damageAreas.get(i++), damageAreas.get(i++))
                  .stream()
                  .peek(a -> a.hit(damage))
                  .map(Player::getName)
                  .forEach(hitPlayers::put);
        }
        try {
            JSONObject object = new JSONObject();
            object.put(Texts.POSITION, position);
            object.put(Texts.HIT_BLOCKS, new JSONArray(hitBlocks));
            object.put(Texts.HIT_PLAYERS, hitPlayers);
            object.put(Texts.DAMAGE, damage);
            write(object.toString(), Server.BOMB_EXPLODE);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPutHelper(@NotNull GVector2f position, @NotNull Helper.Type type) {
        methods.setPutHelper(position, type);
    }

    public void onPutHelper(@NotNull JSONObject data) {
        methods.onPutHelper(data);
    }

    @Override
    protected void processMessage(String data, ClientPlayer client) {
        try {
            JSONObject txt = new JSONObject(data);
            JSONObject o = new JSONObject(txt.getString(Texts.MESSAGE));
            String type = txt.getString(Texts.TYPE);
            switch (type) {
                case PUT_HELPER:
                    writeExcept(o.toString(), type, client);
                    onPutHelper(o);
                    break;
                case PUT_BULLET:
                    writeExcept(o.toString(), type, client);
                    onPutBullet(o);
                    break;
                case PLAYER_DATA:
                    renameClient(client.getId() + "", o.getString(Texts.NAME));
                    parent.getGame().addPlayer(o.getString(Texts.NAME), o.getString(Texts.IMAGE));
                    client.write(getGameInfo(), GAME_INFO);
                    parent.showMessage(Texts.PLAYER_CONNECTED, client.getName());
                    break;
                case Server.CLOSE_CONNECTION:
                    onClientDisconnected(client);
                    break;
                case Server.BUILD_BLOCK:
                    writeExcept(txt.getString(Texts.MESSAGE), type, client);
                    onBuildBlock(o);
                    break;
                case Server.REMOVE_BLOCK:
                    writeExcept(txt.getString(Texts.MESSAGE), type, client);
                    onRemoveBlock(o);
                    break;
                case PLAYER_CHANGE:
                    writeExcept(txt.getString(Texts.MESSAGE), type, client);
                    onPlayerChange(o);
                    break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void onClientDisconnected(ClientPlayer client) {
        removeClient(client.getName());
        parent.getGame().removePlayer(client.getName());
        parent.showMessage(Texts.PLAYER_DISCONNECTED, client.getName());
    }

    @Override
    public void setPlayerChange(@NotNull Player player) {
        methods.setPlayerChange(player);
    }

    private String getGameInfo() {
        return parent.getGame().getGameInfo();
    }

    @Override
    protected String getBasicInfo() {
        return parent.getGame().getBasicInfo();
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
    public void onHitPlayer(@NotNull String name, int damage) {
        parent.getGame().getSceneManager().getPlayerByName(name).hit(damage);
    }

    @Override
    public void setPutEmitter(@NotNull Types emitterOnHit, @NotNull GVector2f position) {
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
