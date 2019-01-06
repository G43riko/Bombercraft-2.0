package org.play_ground.misc;

import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.misc.GCanvas;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

public class SimpleMissile extends Entity<SimpleGameAble> {
    private GVector2f direction = new GVector2f();
    private Entity    target;
    private float     speed     = 1;

    public SimpleMissile(@NotNull GVector2f position,
                         @NotNull SimpleGameAble parent
                        ) {
        super(position, parent);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void update(float delta) {
        if (target != null) {
            direction = direction.getAdd(target.getPosition().getSub(position).normalize()).getDiv(2).normalize();
        }
        direction = direction.getAdd(Input.getMousePosition().getSub(position));
        if (!direction.isNull()) {
            position = position.getAdd(direction.getMul(speed));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GCanvas.drawRect(g2, position, new GVector2f(10, 10), Color.BLUE);
        // g2.setColor(Color.BLUE);
        // g2.drawRect(scale.getXi(), scale.getYi(), 10, 10);
    }

    @NotNull
    @Override
    public JSONObject toJSON() {
        return new JSONObject();
    }
}
