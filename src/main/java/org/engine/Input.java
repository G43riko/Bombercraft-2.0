package org.engine;

import org.bombercraft2.gui.ClickAble;
import org.jetbrains.annotations.Contract;
import org.utils.enums.Keys;
import utils.math.GVector2f;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    public final static  int                   BUTTON_LEFT       = 1;
    public final static  int                   BUTTON_RIGHT      = 3;
    private static final int                   NUM_KEY_CODES     = 256;
    private static final int                   NUM_MOUSE_BUTTONS = 5;
    private static final boolean[]             lastKeys          = new boolean[NUM_KEY_CODES];
    private static final boolean[]             lastMouse         = new boolean[NUM_MOUSE_BUTTONS];
    private static       Input                 input;
    private static       ClickAble             actMenu           = null;
    private static       GVector2f             mousePosition     = new GVector2f();
    private final        Map<Integer, Boolean> buttons           = new HashMap<>();
    private final        Map<Integer, Boolean> keys              = new HashMap<>();
    private              int                   scroll            = 0;
    private              int                   lastScroll        = scroll;

    public Input() {
        input = this;
    }

    @Contract(pure = true)
    public static int getScroll() {
        return input.lastScroll - input.scroll;
    }

    public static void update() {
        for (int i = 0; i < NUM_KEY_CODES; i++) {
            lastKeys[i] = isKeyDown(i);
        }

        for (int i = 0; i < NUM_MOUSE_BUTTONS; i++) {
            lastMouse[i] = isButtonDown(i);
        }

        input.lastScroll = input.scroll;
    }

    public static boolean getKeyDown(Keys key) {
        return isKeyDown(key) && !lastKeys[key.ascii];
    }

    public static boolean getKeyUp(Keys key) {
        return !isKeyDown(key) && lastKeys[key.ascii];
    }

    public static boolean getMouseDown(int mouseButton) {
        return isButtonDown(mouseButton) && !lastMouse[mouseButton];
    }

    public static boolean getMouseUp(int mouseButton) {
        return !isButtonDown(mouseButton) && lastMouse[mouseButton];
    }

    @Contract(pure = true)
    public static GVector2f getMousePosition() {
        return mousePosition;
    }

    public static boolean isButtonDown(int key) {
        if (input.buttons.containsKey(key)) {
            return input.buttons.get(key);
        }
        return false;
    }

    public static boolean isKeyDown(int key) {
        if (input.keys.containsKey(key)) {
            return input.keys.get(key);
        }
        return false;
    }

    public static boolean isKeyDown(Keys key) {
        if (input.keys.containsKey(key.ascii)) {
            return input.keys.get(key.ascii);
        }
        return false;
    }

    public static void cleanUp() { }

    public static void setTarget(ClickAble menu) {
        actMenu = menu;
    }

    public static void removeTarget() {
        actMenu = null;
    }

    public void mouseDragged(MouseEvent e) {
        mousePosition = new GVector2f(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        mousePosition = new GVector2f(e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        if (actMenu != null) {
            actMenu.doAct(new GVector2f(e.getX(), e.getY()));
        }
    }

    public void mouseEntered(MouseEvent arg0) { }

    public void mouseExited(MouseEvent arg0) { }

    public void mousePressed(MouseEvent e) {
        buttons.put(e.getButton(), true);
    }

    public void mouseReleased(MouseEvent e) {
        buttons.put(e.getButton(), false);
    }

    public void keyPressed(KeyEvent e) { keys.put(e.getKeyCode(), true); }

    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent arg0) { }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        input.scroll += e.getWheelRotation();
    }
}
