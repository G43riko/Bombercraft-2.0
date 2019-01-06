package org.play_ground.misc;

import org.bombercraft2.game.entity.Entity;
import org.bombercraft2.game.misc.GCanvas;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.BVector2f;

import java.awt.*;

public class SimpleMissile extends Entity<SimpleGameAble> {
    private BVector2f direction = new BVector2f();
    private Entity    target;
    private float     speed     = 1;

    public SimpleMissile(@NotNull BVector2f position,
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
            direction = direction.getAdd(target.getPosition().getSub(position).Normalized()).getDiv(2).Normalized();
        }
        direction = direction.getAdd(Input.getMousePosition().getSub(position));
        if (!direction.isNull()) {
            position = position.getAdd(direction.getMul(speed));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GCanvas.drawRect(g2, position, new BVector2f(10, 10), Color.BLUE);
        // g2.setColor(Color.BLUE);
        // g2.drawRect(scale.getXi(), scale.getYi(), 10, 10);
    }

    @NotNull
    @Override
    public JSONObject toJSON() {
        return new JSONObject();
    }
}
