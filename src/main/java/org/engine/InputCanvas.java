package org.engine;

import org.bombercraft2.gui.ClickAble;
import org.glib2.math.vectors.SimpleVector2f;
import org.jetbrains.annotations.Contract;
import org.prototypes.input.IInput;
import org.utils.enums.Buttons;
import org.utils.enums.Keys;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.math.GVector2f;

import java.awt.event.*;
import java.util.EnumMap;
import java.util.Map;

public final class InputCanvas implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, IInput {
    private static final int NUM_KEY_CODES     = 256;
    private static final int NUM_MOUSE_BUTTONS = 5;

    public static final InputCanvas instance = new InputCanvas();

    private final boolean[]             lastKeys      = new boolean[NUM_KEY_CODES];
    private final boolean[]             lastMouse     = new boolean[NUM_MOUSE_BUTTONS];
    private final SimpleVector2f        mousePosition = new SimpleVector2f();
    private final Map<Buttons, Boolean> buttons       = new EnumMap<>(Buttons.class);
    private final Map<Keys, Boolean>    keys          = new EnumMap<>(Keys.class);
    private       ClickAble             actMenu       = null;
    private       float                 scroll        = 0;
    private       float                 lastScroll    = scroll;

    private InputCanvas() {}

    @Contract(pure = true)
    public float getZoom() {
        return lastScroll - scroll;
    }

    public void update() {
        for (int i = 0; i < NUM_KEY_CODES; i++) {
            lastKeys[i] = isKeyDown(Keys.byAscii(i));
        }

        for (int i = 0; i < NUM_MOUSE_BUTTONS; i++) {
            lastMouse[i] = isButtonDown(Buttons.byAscii(i));
        }

        lastScroll = scroll;
    }

    @Override
    public boolean getKeyDown(Keys key) {
        return isKeyDown(key) && !lastKeys[key.ascii];
    }

    @Override
    public boolean getKeyUp(Keys key) {
        return !isKeyDown(key) && lastKeys[key.ascii];
    }

    @Override
    public boolean getButtonDown(Buttons mouseButton) {
        return isButtonDown(mouseButton) && !lastMouse[mouseButton.ascii];
    }

    @Override
    public boolean getButtonUp(Buttons mouseButton) {
        return !isButtonDown(mouseButton) && lastMouse[mouseButton.ascii];
    }

    @Override
    public SimpleVector2f getMousePosition() {
        return mousePosition;
    }

    @Override
    public boolean isButtonDown(Buttons key) {
        if (buttons.containsKey(key)) {
            return buttons.get(key);
        }
        return false;
    }

    @Override
    public boolean getMouseLocked() {
        throw new NotImplementedException();
    }

    @Override
    public void setMouseLocked(boolean mouseLocked) {
        throw new NotImplementedException();
    }

    @Override
    public void setMousePos(SimpleVector2f position) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isButtonUp(Buttons key) {
        if (buttons.containsKey(key)) {
            return !buttons.get(key);
        }
        return true;
    }

    @Override
    public boolean isKeyDown(Keys key) {
        if (keys.containsKey(key)) {
            return keys.get(key);
        }
        return false;
    }

    @Override
    public boolean isKeyUp(Keys key) {
        if (keys.containsKey(key)) {
            return !keys.get(key);
        }
        return true;
    }

    public void setTarget(ClickAble menu) {
        actMenu = menu;
    }

    public void removeTarget() {
        actMenu = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (actMenu != null) {
            actMenu.doAct(new GVector2f(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons.put(Buttons.byAscii(e.getButton()), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons.put(Buttons.byAscii(e.getButton()), false);
    }

    @Override
    public void keyPressed(KeyEvent e) { keys.put(Keys.byAscii(e.getKeyCode()), true); }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(Keys.byAscii(e.getKeyCode()), false);
    }

    @Override
    public void keyTyped(KeyEvent arg0) { }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll += e.getWheelRotation();
    }
}
