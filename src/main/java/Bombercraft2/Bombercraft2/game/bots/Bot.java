package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.game.misc.Direction;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotFactory.Types;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

public abstract class Bot extends Entity<GameAble> {
    protected     Direction    direction;
    private       int          health;
    private final BotPrototype prototype;

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
