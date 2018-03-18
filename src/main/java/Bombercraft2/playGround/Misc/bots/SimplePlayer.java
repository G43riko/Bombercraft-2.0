package Bombercraft2.playGround.Misc.bots;


import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.components.healthBar.HealthAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.misc.Direction;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.player.PlayerSprite;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public class SimplePlayer extends Entity<SimpleGameAble> implements HealthAble {
    private int speed;
    private int health;
    private int range;
    private Direction direction = Direction.DOWN;
    private int     damage;
    private String  name;
    private String  image;
    private boolean moving;
    private       boolean wasMoving     = false;
    private final int     movingLimit   = 1;
    private       int     movingCounter = 0;


    public SimplePlayer(SimpleGameAble parent, JSONObject object) {
        super(new GVector2f(), parent);
        try {
            position = new GVector2f(object.getString(Texts.POSITION));
            speed = object.getInt(Texts.SPEED);
            health = object.getInt(Texts.HEALTH);
            range = object.getInt(Texts.RANGE);
            name = object.getString(Texts.NAME);
            damage = 1;
            setImage(object.getString(Texts.IMAGE));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimplePlayer(SimpleGameAble parent,
                        GVector2f position,
                        String name,
                        int speed,
                        int health,
                        String image,
                        int range
                       ) {
        super(position, parent);
        this.speed = speed;
        this.health = health;
        this.range = range;
        this.name = name;
        this.damage = 1;
        setImage(image);
    }


    protected boolean isOnWalkableBlock() {
        //pri 60x60
        final float topOffset = 35;
        final float bottomOffset = 65;
        final float rightOffset = 21;
        final float leftOffset = 19;

        GVector2f t = position.add(new GVector2f(Config.BLOCK_SIZE.getX(), Config.BLOCK_SIZE.getY() - topOffset).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f b = position.add(new GVector2f(Config.BLOCK_SIZE.getX(), Config.BLOCK_SIZE.getY() + bottomOffset).div(
                2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f r = position.add(new GVector2f(Config.BLOCK_SIZE.getX() - rightOffset,
                                                 Config.BLOCK_SIZE.getY()).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f l = position.add(new GVector2f(Config.BLOCK_SIZE.getX() + leftOffset,
                                                 Config.BLOCK_SIZE.getY()).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();

        try {
            return parent.getManager().getMapManager().getTypedBlock(t.getXi(), t.getYi()).getType().isWalkable() &&
                    parent.getManager().getMapManager().getTypedBlock(b.getXi(), b.getYi()).getType().isWalkable() &&
                    parent.getManager().getMapManager().getTypedBlock(r.getXi(), r.getYi()).getType().isWalkable() &&
                    parent.getManager().getMapManager().getTypedBlock(l.getXi(), l.getYi()).getType().isWalkable();
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public void move(GVector2f move) {
        position = position.add(move.mul(speed));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (getParent() == null || position == null) {
            return;
        }

        if (moving) {
            if (movingCounter++ >= movingLimit) {
                moving = false;
            }
        }

        GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

        GVector2f size = Config.BLOCK_SIZE.mul(getParent().getZoom());

        PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage() + getName(), isMoving());
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        JSONObject result = super.toJSON();
        JSONWrapper(() -> {
            result.put(Texts.SPEED, speed);
            result.put(Texts.HEALTH, health);
            result.put(Texts.IMAGE, image);
            result.put(Texts.RANGE, range);
            result.put(Texts.DAMAGE, damage);
            result.put(Texts.NAME, name);
        });
        return result;
    }

    @Override
    public String toString() {
        return name + ": [image: " + image + ", pos: " + position + "]";
    }

    private boolean isMoving() {return moving;}

    private String getImage() {return image;}

    public String getName() {return name;}

    public int getSpeed() {return speed;}

    public int getDamage() {return damage;}

    public Direction getDirection() {return direction;}

    public void setDirection(Direction direction) {this.direction = direction;}

    public void setMoving(boolean moving) {
        this.moving = moving;
        movingCounter = 0;
    }

    private void setImage(String image) {
        this.image = image;
        PlayerSprite.setSprite(image + getName(), image, 5, 5, 4, 6);
    }

    @Override
    public int getMaxHealth() {
        return Config.PLAYER_MAX_HEALTH;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public int getActHealth() {
        return health;
    }

    public void hit(int damage) {
        health -= damage;
    }


    protected boolean checkBorders() {
        boolean touchBorders = false;
        GVector2f zoomedPosition = position.mul(parent.getManager().getViewManager().getZoom());
        GVector2f zoomedMapSize = parent.getManager().getMapManager().getMapSize().mul(parent.getZoom());

        if (zoomedPosition.getX() < 0) {
            position.setX(0);
            touchBorders = true;
        }

        if (zoomedPosition.getY() < 0) {
            position.setY(0);
            touchBorders = true;
        }

        if (zoomedPosition.getX() + Config.DEFAULT_BLOCK_WIDTH * parent.getZoom() > zoomedMapSize.getX()) {
            position.setX((zoomedMapSize.getX() - Config.DEFAULT_BLOCK_WIDTH * parent.getZoom()) / parent.getZoom());
            touchBorders = true;
        }

        if (zoomedPosition.getY() + Config.DEFAULT_BLOCK_HEIGHT * parent.getZoom() > zoomedMapSize.getY()) {
            position.setY((zoomedMapSize.getY() - Config.DEFAULT_BLOCK_HEIGHT * parent.getZoom()) / parent.getZoom());
            touchBorders = true;
        }
        return touchBorders;
    }
}
