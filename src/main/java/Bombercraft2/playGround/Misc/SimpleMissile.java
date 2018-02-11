package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public class SimpleMissile extends Entity<SimpleGameAble> {
    private GVector2f direction = new GVector2f();
    private Entity target;
    private float speed = 1;

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
            direction = direction.add(target.getPosition().sub(position).Normalized()).div(2).Normalized();
        }
        direction = direction.add(Input.getMousePosition().sub(position));
        if (!direction.isNull()) {
            position = position.add(direction.mul(speed));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.drawRect(position.getXi(), position.getYi(), 10, 10);
    }

    @NotNull
    @Override
    public JSONObject toJSON() {
        return new JSONObject();
    }
}
