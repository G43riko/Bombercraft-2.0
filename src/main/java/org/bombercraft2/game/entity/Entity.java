package org.bombercraft2.game.entity;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.bombercraft2.core.Visible;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.interfaces.JSONAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public abstract class Entity<T extends SimpleGameAble> implements Visible, InteractAbleG2, JSONAble {
    protected final T parent;
    protected       GVector2f position = null;
    protected       boolean alive      = true;
    private         int id             = 1;

    public Entity(@NotNull JSONObject json, @NotNull T parent) {
        this.parent = parent;
        fromJSON(json);
    }

    protected Entity(@NotNull GVector2f position, @NotNull T parent) {
        this.position = position;
        this.parent = parent;
    }


    @Override
    public void fromJSON(@NotNull JSONObject json) {
        jsonWrapper(() -> {
            id = json.getInt(Texts.ID);
            alive = json.getBoolean(Texts.ALIVE);
            position = new GVector2f(json.getString(Texts.POSITION));
        });
    }

    @NotNull
    @Contract(pure = true)
    public JSONObject toJSON() {
        final JSONObject result = new JSONObject();
        jsonWrapper(() -> {
            result.put(Texts.ID, id);
            result.put(Texts.POSITION, position);
            result.put(Texts.ALIVE, alive);
        });
        return result;
    }

    public GVector2f getSur() {return position.div(StaticConfig.BLOCK_SIZE).toInt();}

    @NotNull
    @Contract(pure = true)
    public GVector2f getSize() {return StaticConfig.BLOCK_SIZE; }


    @Contract(pure = true)
    public final int getID() {return id;}

    @Contract(pure = true)
    public final T getParent() {return parent;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position;}

    //SETTERS
    public void setPosition(GVector2f position) {this.position = position;}

    @NotNull
    protected final GVector2f getTransformedPosition() {
        return parent.getManager()
                     .getViewManager()
                     .transform(position);
    }

    @NotNull
    protected final GVector2f getTransformedSize() {return getSize().mul(parent.getZoom()); }

    @NotNull
    public final GVector2f getCenter() {return getPosition().add(getSize().div(2)); }

    public boolean isAlive() {return alive;}

    public boolean isDeath() {return !alive;}
}
