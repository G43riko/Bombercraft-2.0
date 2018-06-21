package org.bombercraft2.game.entity.bullets;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.entity.bullets.BulletManager.Types;
import org.bombercraft2.game.entity.particles.EmitterTypes;
import org.bombercraft2.game.level.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public abstract class Bullet extends Entity<GameAble> {
    private final GVector2f    direction;
    private final BulletModel  model;
    private       EmitterTypes emitterOnHit;// = Emitter.PARTICLE_EXPLOSION_TEST;
    private       int          health;

    Bullet(GVector2f position, GameAble parent, Types type, GVector2f direction) {
        super(position, parent);
        this.model = BulletManager.getBulletModel(type);
        this.direction = direction;
        health = model.getMaxHealth();
    }

    @Override
    public void update(float delta) {
        Block block = getParent().getLevel()
                                 .getMap()
                                 .getBlockOnPosition(getPosition().add(StaticConfig.BLOCK_SIZE_HALF));

        if (block != null && !block.isWalkable()) {
            hit();
            block.hit(model.getDamage());
            getParent().getConnector().hitBlock(getPosition(), model.getDamage());
        }
        else if (getParent().getConnector().bulletHitEnemy(this)) {
            hit();
        }

        if (health <= 0) {
            alive = false;
        }

        checkBorders();

        if (alive) {
            position = position.add(direction.mul(model.getSpeed()));
        }

    }

    //OTHERS

    private void hit() {
        health--;
        if (emitterOnHit != null) {
            getParent().addEmitter(position, emitterOnHit);
            getParent().getConnector().setPutEmitter(emitterOnHit, position/*.add(size.div(2))*/);
        }
    }

    private void checkBorders() {
        GVector2f a = position.add(model.getSize());
        GVector2f b = getParent().getLevel()
                                 .getMap()
                                 .getNumberOfBlocks()
                                 .mul(StaticConfig.BLOCK_SIZE)
                                 .div(getParent().getZoom());
        if (a.getX() < 0 ||
                a.getY() < 0 ||
                position.getX() > b.getX() ||
                position.getY() > b.getY()) {
            alive = false;
        }
    }


    public int getMaxHealth() {return model.getMaxHealth();}

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {return model.getSize();}

    GVector2f getDirection() {return direction;}

    public int getDamage() {return model.getDamage();}

    Color getColor() {return model.getColor();}

    int getSpeed() {return model.getSpeed();}

    public int getHealth() {return health;}

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }
}