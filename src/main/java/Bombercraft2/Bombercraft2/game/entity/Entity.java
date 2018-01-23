package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.IDGenerator;
import utils.math.GVector2f;

public abstract class Entity implements Visible, InteractAble {
    private   int       id       = IDGenerator.getId();
    private final GameAble parent;
    protected GVector2f position = null;
    protected boolean   alive    = true;
    //CONSTRUCTORS

    public Entity(@NotNull JSONObject json, @NotNull GameAble parent) {
        this.parent = parent;
        try {
            id = json.getInt("id");
            alive = json.getBoolean("alive");
            position = new GVector2f(json.getString("position"));
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected Entity(@NotNull GVector2f position, @NotNull GameAble parent) {
        this.position = position;
        this.parent = parent;
    }

    //ABSTRACT

    public abstract JSONObject toJSON();

    public GVector2f getSur() {return position.div(Block.SIZE).toInt();}

    public GVector2f getSize() {return Block.SIZE; }

    //GETTERS

    @Contract(pure = true)
    public final int getID() {return id;}

    @Contract(pure = true)
    public final GameAble getParent() {return parent;}

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
