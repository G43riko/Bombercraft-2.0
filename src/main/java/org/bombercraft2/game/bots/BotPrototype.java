package org.bombercraft2.game.bots;

import org.bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;

public class BotPrototype {
    private int              speed;
    private int              maxHealth;
    private int              damage;
    private BotFactory.Types type;

    public BotPrototype(JSONObject data) {
        try {
            this.speed = data.getInt(Texts.SPEED);
            this.damage = data.getInt(Texts.DAMAGE);
            this.maxHealth = data.getInt(Texts.HEALTH);
            this.type = BotFactory.Types.valueOf(data.getString(Texts.TYPE));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getSpeed() {return speed;}

    public int getMaxHealth() {return maxHealth;}

    public int getDamage() {return damage;}

    public BotFactory.Types getType() {return type;}


}
