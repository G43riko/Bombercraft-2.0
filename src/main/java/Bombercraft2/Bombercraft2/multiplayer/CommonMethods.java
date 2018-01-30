package Bombercraft2.Bombercraft2.multiplayer;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.ShootAble;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Block.Type;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import Bombercraft2.Bombercraft2.multiplayer.core.Server;
import Bombercraft2.Bombercraft2.multiplayer.core.Writable;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.math.GVector2f;

class CommonMethods {
    @NotNull
    private final GameAble game;
    @NotNull
    private final Writable parent;

    public CommonMethods(@NotNull GameAble game, @NotNull Writable parent) {
        this.game = game;
        this.parent = parent;
    }

    public void onRemoveBlock(@NotNull JSONObject data) {
        try {
            game.getLevel().getMap().remove(new GVector2f(data.getString(Texts.POSITION)));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBuildBlock(@NotNull JSONObject data) {
        try {
            GVector2f position = new GVector2f(data.getString(Texts.POSITION));
            Block block = game.getLevel().getMap().getBlock(position.getXi(), position.getYi());
            if (block == null) {
                GLogger.printLine("ide sa postavit blok na neexistujucej pozicii: " + position);
                return;
            }
            block.build(Type.valueOf(data.getString(Texts.TYPE)));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onPlayerChange(@NotNull JSONObject data) {
        try {
            Player p = game.getPlayerByName(data.getString(Texts.PLAYER));
            p.setDirection(Direction.valueOf(data.getString(Texts.DIRECTION)));
            p.setPosition(new GVector2f(data.getString(Texts.POSITION)));
            p.setMoving(true);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPutHelper(@NotNull GVector2f position, @NotNull Helper.Type type) {
        long createdAt = System.currentTimeMillis();
        game.addHelper(position, type, createdAt);

        try {
            JSONObject result = new JSONObject();
            result.put(Texts.CREATED_AT, createdAt);
            result.put(Texts.POSITION, position);
            result.put(Texts.TYPE, type);
            parent.write(result.toString(), Server.PUT_HELPER);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onPutHelper(@NotNull JSONObject data) {
        try {
            game.addHelper(new GVector2f(data.getString(Texts.POSITION)),
                           Helper.Type.valueOf(data.getString(Texts.TYPE)),
                           data.getLong(Texts.CREATED_AT));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setRemoveBlock(@NotNull GVector2f position) {
        game.getLevel().getMap().remove(position);
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.POSITION, position);
            parent.write(result.toString(), Server.REMOVE_BLOCK);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBuildBlock(@NotNull GVector2f position, @NotNull Type type) {
        game.getLevel().getMap().getBlock(position.getXi(), position.getYi()).build(type);
        try {
            JSONObject result = new JSONObject();
            result.put(Texts.POSITION, position);
            result.put(Texts.TYPE, type);
            parent.write(result.toString(), Server.BUILD_BLOCK);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBuildBlockArea(@NotNull GVector2f minPos, @NotNull GVector2f maxPos, @NotNull Type blockType) {
        for (int i = minPos.getXi(); i <= maxPos.getX(); i++) {
            for (int j = minPos.getYi(); j <= maxPos.getY(); j++) {
                game.getLevel().getMap().getBlock(i, j).build(blockType);
            }
        }
    }

    public void setPutBullet(@NotNull MyPlayer myPlayer, @NotNull ShootAble shooter) {
        GVector2f angle = myPlayer.getTargetDirection();
        angle.normalize();
        //TODO tu treba spracovať už aj bonusy od hraca
        try {
            JSONObject object = new JSONObject();
            object.put(Texts.POSITION, myPlayer.getTargetLocation());
            object.put(Texts.DIRECTION, angle);
            object.put(Texts.TYPE, shooter.getBulletType());
            parent.write(object.toString(), Server.PUT_BULLET);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        game.addBullet(shooter.getBulletType(), angle, myPlayer.getTargetLocation());
    }

    //	public void setPutBullet(MyPlayer myPlayer, BulletManager.Types bulletType) {
//		GVector2f angle = myPlayer.getTargetDirection();
//		angle.normalize();
//		//TODO tu treba spracovať už aj bonusy od hraca
//		try {
//			JSONObject object = new JSONObject();
//			object.put(Texts.POSITION, myPlayer.getTargetLocation());
//			object.put(Texts.DIRECTION, angle);
//			object.put(Texts.TYPE, bulletType);
//			parent.write(object.toString(), Server.PUT_BULLET);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		game.addBullet(bulletType, angle, myPlayer.getTargetLocation());
//		
//	}
    public void onPutBullet(@NotNull JSONObject data) {
        try {
            game.addBullet(Types.valueOf(data.getString(Texts.TYPE)),
                           new GVector2f(data.getString(Texts.DIRECTION)),
                           new GVector2f(data.getString(Texts.POSITION)));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerChange(Player player) {
        try {
            JSONObject object = new JSONObject();
            object.put(Texts.POSITION, player.getPosition());
            object.put(Texts.DIRECTION, player.getDirection());
            object.put(Texts.PLAYER, player.getName());
            parent.write(object.toString(), Server.PLAYER_CHANGE);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
