package org.bombercraft2.game.misc;

import org.bombercraft2.core.Texts;
import org.glib2.interfaces.JSONAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.BasicEntity;
import utils.math.GVector2f;

import java.awt.*;

public class PosAndSize implements JSONAble {
    public final GVector2f position;
    public final GVector2f size;

    public PosAndSize(@NotNull GVector2f position, @NotNull GVector2f size) {
        this.position = position;
        this.size = size;
    }


    public void fillRect(Graphics2D g2) {
        GCanvas.fillRect(g2, position, size);
    }

    public void setColorAndfillRect(Graphics2D g2, Color color) {
        GCanvas.fillRect(g2, position, size, color);
    }

    public void drawRect(Graphics2D g2) {
        GCanvas.drawRect(g2, position, size);
    }

    public void setColorAndDrawRect(Graphics2D g2, Color color) {
        GCanvas.drawRect(g2, position, size, color);
    }


    public boolean isIn(BasicEntity entity) {
        return entity.position.getX() > position.getX() &&
                entity.position.getY() > position.getY() &&
                entity.position.getX() + entity.size.getX() < position.getX() + size.getX() &&
                entity.position.getY() + entity.size.getY() < position.getY() + size.getY();
    }


    @Override
    public void fromJSON(@NotNull JSONObject json) {
        jsonWrapper(() -> {
            position.set(new GVector2f(json.getString(Texts.POSITION)));
            size.set(new GVector2f(json.getString(Texts.SIZE)));
        });
    }

    @NotNull
    @Contract(pure = true)
    public JSONObject toJSON() {
        final JSONObject result = new JSONObject();
        jsonWrapper(() -> {
            result.put(Texts.POSITION, position);
            result.put(Texts.SIZE, size);
        });
        return result;
    }
}
