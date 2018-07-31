package org.bombercraft2.components;

import com.badlogic.ashley.core.Component;
import org.engine.Input;
import org.glib2.math.vectors.SimpleVector3f;
import org.utils.enums.Keys;

public class ControlComponent implements Component {
    private float speed;

    public ControlComponent(float speed) {
        this.speed = speed;
    }

    public void update(float delta, float componentSpeed, SimpleVector3f position) {
        float movement = speed * delta * componentSpeed;
        if (Input.isKeyDown(Keys.W)) {
            position.add(0, -movement, 0);
        }

        if (Input.isKeyDown(Keys.S)) {
            position.add(0, movement, 0);
        }

        if (Input.isKeyDown(Keys.A)) {
            position.add(-movement, 0, 0);
        }

        if (Input.isKeyDown(Keys.D)) {
            position.add(movement, 0, 0);
        }
    }
}
