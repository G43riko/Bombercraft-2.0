package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotManager.Types;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import org.json.JSONObject;
import utils.math.GVector2f;

public abstract class Bot extends Entity {
    Direction direction;
    private int      health;
    private BotModel model;

    Bot(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent);
        model = BotManager.getBotModel(type);
        this.direction = direction;
        health = model.getMaxHealth();
    }

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

    int getSpeed() {return model.getSpeed();}

}
