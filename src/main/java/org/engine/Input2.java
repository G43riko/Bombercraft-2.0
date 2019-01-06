package org.engine;

import org.glib2.interfaces.ClickAble;
import org.glib2.math.vectors.GVector2f;
import org.glib2.math.vectors.SimpleVector2f;
import org.prototypes.IDisplay;
import org.prototypes.IInput;
import org.utils.enums.Buttons;
import org.utils.enums.Keys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Input2 implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, IInput {
    private static final int    NUM_KEY_CODES     = 256;
    private static final int    NUM_MOUSE_BUTTONS = 5;
    private final static Cursor blankCursor       = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,
                                                                                                                     16,
                                                                                                                     BufferedImage.TYPE_INT_ARGB),
                                                                                                   new Point(0, 0),
                                                                                                   "blank cursor");

    private final boolean[]             lastKeys      = new boolean[NUM_KEY_CODES];
    private final boolean[]             lastMouse     = new boolean[NUM_MOUSE_BUTTONS];
    private final Map<Buttons, Boolean> buttons       = new EnumMap<>(Buttons.class);
    private final Map<Keys, Boolean>    keys          = new EnumMap<>(Keys.class);
    private final JFrame                parent;
    private       ClickAble             actMenu       = null;
    private       boolean               locked        = false;
    private       SimpleVector2f        mousePosition = new SimpleVector2f();
    private       int                   scroll        = 0;
    private       int                   lastScroll    = scroll;
    private       Cursor                oldCursor;

    public Input2(JFrame parent) {this.parent = parent;}

    // TODO: was getScroll
    @Override
    public float getZoom() {
        return lastScroll - scroll;
    }

    @Override
    public void update() {
        for (Keys key : Keys.values()) {
            lastKeys[key.ascii] = isKeyDown(key);
        }
        for (Buttons button : Buttons.values()) {
            lastMouse[button.ascii] = isButtonDown(button);
        }

        lastScroll = scroll;
    }

    @Override
    public void init(IDisplay display) {

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
    public void setMousePos(SimpleVector2f position) {

        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SimpleVector2f getMousePosition() {
        return mousePosition;
    }

    @Override
    public boolean getMouseLocked() {
        return locked;
    }

    @Override
    public void setMouseLocked(boolean mouseLocked) {
        if (mouseLocked) {
            oldCursor = parent.getContentPane().getCursor();
            parent.getContentPane().setCursor(blankCursor);
        } else {
            parent.getContentPane().setCursor(oldCursor);
        }

        locked = mouseLocked;
    }

    public boolean isButtonDown(Buttons button) {
        if (buttons.containsKey(button)) {
            return buttons.get(button);
        }
        return false;
    }

    public boolean isKeyDown(Keys key) {
        if (keys.containsKey(key)) {
            return keys.get(key);
        }
        return false;
    }

    public boolean isKeyUp(Keys key) {
        return !isKeyDown(key);
    }

    @Override
    public boolean isButtonUp(Buttons button) {
        return isButtonDown(button);
    }

    public void cleanUp() {

    }

    public void setTarget(ClickAble menu) {
        actMenu = menu;
    }

    public void removeTarget() {
        actMenu = null;
    }

    public void mouseDragged(MouseEvent e) {
        mousePosition = new SimpleVector2f(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        mousePosition = new SimpleVector2f(e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        if (actMenu != null) {
            actMenu.doAct(new GVector2f(e.getX(), e.getY()));
        }
    }

    public void mouseEntered(MouseEvent arg0) { }

    public void mouseExited(MouseEvent arg0) { }

    public void mousePressed(MouseEvent e) {
        buttons.put(Buttons.byAscii(e.getButton()), true);
    }

    public void mouseReleased(MouseEvent e) {
        buttons.put(Buttons.byAscii(e.getButton()), false);
    }

    public void keyPressed(KeyEvent e) { keys.put(Keys.byAscii(e.getKeyCode()), true); }

    public void keyReleased(KeyEvent e) {
        keys.put(Keys.byAscii(e.getKeyCode()), false);
    }

    public void keyTyped(KeyEvent arg0) { }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll += e.getWheelRotation();
    }
}
