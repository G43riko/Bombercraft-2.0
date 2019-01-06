package org.bombercraft2.game.bots;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.bots.BotFactory.Types;
import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.misc.Direction;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public abstract class Bot extends Entity<GameAble> {
    private final BotPrototype prototype;
    protected     Direction    direction;
    private       int          health;

    public Bot(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent);
        prototype = BotFactory.getBotPrototype(type);
        this.direction = direction;
        if (prototype != null) {
            health = prototype.getMaxHealth();
        }
    }

    /**
     * Return true if models hasn't work to do else return false
     *
     * @return
     */
    public boolean isFree() {
        return true;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    public int getSpeed() {
        return prototype == null ? 1 : prototype.getSpeed();
    }

}
