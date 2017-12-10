package Bombercraft2.Bombercraft2.gui2.core;

import java.awt.*;

public interface Drawable {
    void render(Graphics2D g2);
    default void update(float delta) {}
    void setX(int x);

    void setY(int y);

    int getX();

    int getY();

    void setWidth(int width);

    void setHeight(int height);

    int getWidth();

    int getHeight();

    boolean isVisible();

    void setVisible(boolean value);
}
