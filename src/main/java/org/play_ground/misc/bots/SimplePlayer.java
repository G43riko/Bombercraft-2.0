package org.play_ground.misc.bots;


import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.misc.Direction;
import org.bombercraft2.game.player.PlayerSprite;
import org.glib2.interfaces.HealthAble;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.SimpleGameAble;

import java.awt.*;

public class SimplePlayer extends Entity<SimpleGameAble> implements HealthAble {
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

        GVector2f t = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX(),
                                                    StaticConfig.BLOCK_SIZE.getY() - topOffset).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f b = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX(),
                                                    StaticConfig.BLOCK_SIZE.getY() + bottomOffset).getDiv(
                2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f r = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX() - rightOffset,
                                                    StaticConfig.BLOCK_SIZE.getY()).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f l = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX() + leftOffset,
                                                    StaticConfig.BLOCK_SIZE.getY()).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
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
        position = position.getAdd(move.getMul(speed));
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

        PlayerSprite.drawPlayer(getTransformedPosition(),
                                getTransformedSize(),
                                g2,
                                getDirection(),
                                getImage() + getName(),
                                isMoving());
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        JSONObject result = super.toJSON();
        jsonWrapper(() -> {
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
    public int getHealth() {
        return health;
    }

    public void hit(int damage) {
        health -= damage;
    }


    protected boolean isOnEdge() {
        GVector2f zoomedPosition = position.getMul(parent.getManager().getViewManager().getZoom());
        GVector2f zoomedMapSize = parent.getManager()
                                        .getMapManager()
                                        .getMapSize()
                .getMul(parent.getManager().getViewManager().getZoom());

        if (zoomedPosition.getX() < 0) {
            position.setX(0);
            return true;
        }

        if (zoomedPosition.getY() < 0) {
            position.setY(0);
            return true;
        }

        if (zoomedPosition.getX() + StaticConfig.DEFAULT_BLOCK_WIDTH * parent.getManager()
                                                                             .getViewManager()
                                                                             .getZoom() > zoomedMapSize.getX()) {
            position.setX((zoomedMapSize.getX() - StaticConfig.DEFAULT_BLOCK_WIDTH * parent.getManager()
                                                                                           .getViewManager()
                                                                                           .getZoom()) / parent.getManager()
                                                                                                               .getViewManager()
                                                                                                               .getZoom());
            return true;
        }

        if (zoomedPosition.getY() + StaticConfig.DEFAULT_BLOCK_HEIGHT * parent.getManager()
                                                                              .getViewManager()
                                                                              .getZoom() > zoomedMapSize.getY()) {
            position.setY((zoomedMapSize.getY() - StaticConfig.DEFAULT_BLOCK_HEIGHT * parent.getManager()
                                                                                            .getViewManager()
                                                                                            .getZoom()) / parent.getManager()
                                                                                                                .getViewManager()
                                                                                                                .getZoom());
            return true;
        }
        return false;
    }
}
