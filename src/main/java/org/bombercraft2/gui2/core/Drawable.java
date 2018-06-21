package org.bombercraft2.gui2.core;

import org.bombercraft2.gui2.GuiManager;

import java.awt.*;

public interface Drawable {
    void render(Graphics2D g2);

    GuiManager getManager();

    void setManager(GuiManager manager);

    void update(UpdateData data);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    boolean isVisible();

    void setVisible(boolean value);

    void onResize();
}
