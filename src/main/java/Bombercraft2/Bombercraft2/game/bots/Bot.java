package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotFactory.Types;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

public abstract class Bot extends Entity<GameAble> {
    protected     Direction direction;
    private       int       health;
    private final BotModel  model;

    public Bot(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent);
        model = BotFactory.getBotModel(type);
        this.direction = direction;
        if (model != null) {
            health = model.getMaxHealth();
        }
    }

    public boolean isFree() {
        return true;
    }

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
        return model == null ? 1 : model.getSpeed();
    }

}
