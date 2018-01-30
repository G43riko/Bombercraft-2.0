package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.IDGenerator;
import utils.math.GVector2f;

public abstract class Entity<T extends SimpleGameAble> implements Visible, InteractAble {
    private int id = IDGenerator.getId();
    protected final T parent;
    protected GVector2f position = null;
    protected boolean   alive    = true;

    public Entity(@NotNull JSONObject json, @NotNull T parent) {
        this.parent = parent;
        try {
            id = json.getInt("id");
            alive = json.getBoolean("alive");
            position = new GVector2f(json.getString("position"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected Entity(@NotNull GVector2f position, @NotNull T parent) {
        this.position = position;
        this.parent = parent;
    }

    //ABSTRACT
    @NotNull
    @Contract(pure = true)
    public abstract JSONObject toJSON();

    public GVector2f getSur() {return position.div(Config.BLOCK_SIZE).toInt();}

    @NotNull
    @Contract(pure = true)
    public GVector2f getSize() {return Config.BLOCK_SIZE; }


    @Contract(pure = true)
    public final int getID() {return id;}

    @Contract(pure = true)
    public final T getParent() {return parent;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position;}

    @NotNull
    protected final GVector2f getTotalPosition() {return position.mul(parent.getZoom()).sub(getParent().getOffset());}

    @NotNull
    protected final GVector2f getTotalSize() {return getSize().mul(parent.getZoom()); }

    @NotNull
    public final GVector2f getCenter() {return getPosition().add(getSize().div(2)); }

    public boolean isAlive() {return alive;}

    //SETTERS
    public void setPosition(GVector2f position) {this.position = position;}
}
