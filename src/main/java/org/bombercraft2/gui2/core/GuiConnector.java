package org.bombercraft2.gui2.core;

import java.util.LinkedHashSet;
import java.util.Set;

public final class GuiConnector {
    private static final Set<DrawableComponent> drawableComponents = new LinkedHashSet<>();
    private static       GuiConnectAble         connector;

    public static void register(DrawableComponent component) {
        drawableComponents.add(component);
    }

    public static void setConnector(GuiConnectAble connector) {
        GuiConnector.connector = connector;
    }


    public static boolean isMouseOn(int x, int y, int width, int height) {
        return connector.getMouseX() > x &&
                connector.getMouseX() < x + width &&
                connector.getMouseY() > y &&
                connector.getMouseY() < y + height;
    }

    public static boolean isMouseOn(PositionableComponent component) {
        return isMouseOn(component.getX(), component.getY(), component.getWidth(), component.getHeight());
    }

    public static int getScroll() {return connector == null ? 0 : connector.getScroll(); }

    public static boolean isSet() {
        return connector != null;
    }

    public static int getMouseX() {
        return connector == null ? -1 : connector.getMouseX();
    }

    public static int getMouseY() {
        return connector == null ? -1 : connector.getMouseY();
    }

    public static boolean isButtonDown() {
        return connector != null && connector.isButtonDown();
    }

    public static boolean isMouseOn(PositionableComponent component, UpdateData data) {
        return isMouseOn(component.getX(), component.getY(), component.getWidth(), component.getHeight(), data);
    }

    public static boolean isMouseOn(int x, int y, int width, int height, UpdateData data) {
        return data.mouseX > x &&
                data.mouseX < x + width &&
                data.mouseY > y &&
                data.mouseY < y + height;
    }
}
