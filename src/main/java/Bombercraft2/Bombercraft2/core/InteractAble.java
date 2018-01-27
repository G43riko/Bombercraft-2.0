package Bombercraft2.Bombercraft2.core;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface InteractAble {
    default void render(@NotNull Graphics2D g2) {}

    default void input() {}

    default void update(float delta) {}

    default void cleanUp() {}
}
