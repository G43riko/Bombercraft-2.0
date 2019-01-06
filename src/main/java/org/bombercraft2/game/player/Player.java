package org.bombercraft2.game.player;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.components.health_bar.HealthAble;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.misc.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BombercraftUtils;
import utils.math.BVector2f;

import java.awt.*;

public class Player extends Entity<GameAble> implements HealthAble {
    private final int       movingLimit   = 1;
    private       int       speed;
    private       int       health;
    private       int       range;
    private       Direction direction     = Direction.DOWN;
    private       int       damage;
    private       String    name;
    private       String    image;
    private       boolean   moving;
    private       boolean   wasMoving     = false;
    private       int       movingCounter = 0;


    public Player(GameAble parent, JSONObject object) {
        super(new BVector2f(), parent);
        try {
            position = new BVector2f(object.getString(Texts.POSITION));
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

    public Player(GameAble parent, BVector2f position, String name, int speed, int health, String image, int range) {
        super(position, parent);
        this.speed = speed;
        this.health = health;
        this.range = range;
        this.name = name;
        this.damage = 1;
        setImage(image);
    }

    public void move(BVector2f move) {
        position = position.getAdd(move.getMul(speed));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (getParent() == null || position == null) { return; }


        if (moving) {
            if (movingCounter++ >= movingLimit) {
                moving = false;
            }
        }

        BVector2f pos = position.getMul(getParent().getZoom()).getSub(getParent().getOffset());

        BVector2f size = StaticConfig.BLOCK_SIZE.getMul(getParent().getZoom());

        PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage() + getName(), isMoving());


    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        JSONObject result = super.toJSON();
        try {
            result.put(Texts.POSITION, position);
            result.put(Texts.SPEED, speed);
            result.put(Texts.HEALTH, health);
            result.put(Texts.IMAGE, image);
            result.put(Texts.RANGE, range);
            result.put(Texts.NAME, name);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        return name + ": [image: " + image + ", pos: " + position + "]";
    }

    public void respawn() {
        position = getParent().getLevel().getRandomRespawnZone();
    }

    public BVector2f getSelectorPos() {
        BVector2f pos = getPosition().getAdd(StaticConfig.BLOCK_SIZE_HALF).getDiv(StaticConfig.BLOCK_SIZE).toInt();
        pos = pos.getAdd(BombercraftUtils.getNormalMoveFromDir(getDirection())).getMul(StaticConfig.BLOCK_SIZE);
        return pos;
    }


    private boolean isMoving() {return moving;}

    public void setMoving(boolean moving) {
        this.moving = moving;
        movingCounter = 0;
    }

    private String getImage() {return image;}

    private void setImage(String image) {
        this.image = image;
        PlayerSprite.setSprite(image + getName(), image, 5, 5, 4, 6);
    }

    public String getName() {return name;}

    public int getSpeed() {return speed;}

    public int getDamage() {return damage;}

    public Direction getDirection() {return direction;}

    public void setDirection(Direction direction) {this.direction = direction;}

    @Override
    public int getMaxHealth() {
        return StaticConfig.PLAYER_MAX_HEALTH;
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


}
