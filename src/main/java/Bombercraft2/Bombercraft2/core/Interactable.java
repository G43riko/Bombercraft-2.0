package Bombercraft2.Bombercraft2.core;

import java.awt.*;

public interface Interactable {
    default void render(Graphics2D g2) {}

    default void input() {}

    default void update(float delta) {}

    default void cleanUp() {}
}
