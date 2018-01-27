package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.HealthAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.level.Block;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Utils;
import utils.math.GVector2f;

import java.awt.*;

public class Player extends Entity<GameAble> implements HealthAble {
    public enum Direction {
        UP(2, new GVector2f(0, -1)),
        DOWN(3, new GVector2f(0, 1)),
        LEFT(0, new GVector2f(-1, 0)),
        RIGHT(1, new GVector2f(1, 0));

        private final int       id;
        private final GVector2f direction;

        Direction(int id, GVector2f direction) {
            this.id = id;
            this.direction = direction;
        }

        public int getID() {return id;}

        public GVector2f getDirection() {return direction;}

        public static Direction getRandomDirection() {return Direction.values()[(int) (Math.random() * 4)];}
    }

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


    public Player(GameAble parent, JSONObject object) {
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

    public Player(GameAble parent, GVector2f position, String name, int speed, int health, String image, int range) {
        super(position, parent);
        this.speed = speed;
        this.health = health;
        this.range = range;
        this.name = name;
        this.damage = 1;
        setImage(image);
    }

    public void move(GVector2f move) {
        position = position.add(move.mul(speed));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (getParent() == null || position == null) { return; }


        if (moving) {
            if (movingCounter++ >= movingLimit) {
                moving = false;
            }
        }

        GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

        GVector2f size = Config.BLOCK_SIZE.mul(getParent().getZoom());

        PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage(), isMoving());


    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
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

    public GVector2f getSelectorPos() {
        GVector2f pos = getPosition().add(Config.BLOCK_SIZE.div(2)).div(Config.BLOCK_SIZE).toInt();
        pos = pos.add(Utils.getNormalMoveFromDir(getDirection())).mul(Config.BLOCK_SIZE);
        return pos;
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
        PlayerSprite.setSprite(image, 5, 5, 4, 6);
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


}
