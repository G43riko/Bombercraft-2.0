package Bombercraft2.Bombercraft2.game.entity.bullets;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager.Types;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Block;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public abstract class Bullet extends Entity {
    protected     Emitter.Types emitterOnHit;// = Emitter.PARTICLE_EXPLOSION_TEST;
    private final GVector2f     direction;
    private final BulletModel   model;
    private       int           health;

    public Bullet(GVector2f position, GameAble parent, Types type, GVector2f direction) {
        super(position, parent);
        this.model = BulletManager.getBulletModel(type);
        this.direction = direction;
        health = model.getMaxHealth();
    }

    @Override
    public void update(float delta) {
        Block block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(Block.SIZE.div(2)));

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

    protected void hit() {
        health--;
        if (emitterOnHit != null) {
            getParent().addEmitter(position, emitterOnHit);
            getParent().getConnector().setPutEmitter(emitterOnHit, position/*.add(size.div(2))*/);
        }
    }

    protected void checkBorders() {
        GVector2f a = position.add(model.getSize());
        GVector2f b = getParent().getLevel().getMap().getNumberOfBlocks().mul(Block.SIZE).div(getParent().getZoom());
        if (a.getX() < 0 ||
                a.getY() < 0 ||
                position.getX() > b.getX() ||
                position.getY() > b.getY()) {
            alive = false;
        }
    }

    //GETTERS

    public int getMaxHealth() {return model.getMaxHealth();}

    @Override
    public GVector2f getSize() {return model.getSize();}

    public GVector2f getDirection() {return direction;}

    public int getDamage() {return model.getDamage();}

    public Color getColor() {return model.getColor();}

    public int getSpeed() {return model.getSpeed();}

    public int getHealth() {return health;}

    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }
}
