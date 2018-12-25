package org.engine;

import org.glib2.math.vectors.SimpleVector2f;
import org.prototypes.IDisplay;
import org.utils.events.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;

public final class Window extends JFrame implements IDisplay {
    private final Canvas canvas = new Canvas();

    private final Observable<SimpleVector2f> resizeHandlers = new Observable<>();
    private final CoreEngine                 parent;
    private       boolean                    focused        = true;
    private       boolean                    fullscreen     = false;

    public Window(CoreEngine parent, String title, int width, int height) {
        this.parent = parent;
        setTitle(title);
        setResizable(true);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		setFullscreen(true);
        setVisible(true);


        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                resizeHandlers.emit(new SimpleVector2f(getSize().width, getSize().height));
            }

            public void componentMoved(ComponentEvent e) { }

            public void componentShown(ComponentEvent e) { }

            public void componentHidden(ComponentEvent e) { }
        });
        addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) { }

            public void windowClosing(WindowEvent e) {
                parent.onExit();
            }

            public void windowClosed(WindowEvent e) { }

            public void windowIconified(WindowEvent e) {
                parent.onBlur();
                focused = false;
            }

            public void windowDeiconified(WindowEvent e) {
                parent.onFocus();
                focused = true;
            }

            public void windowActivated(WindowEvent e) {
                parent.onFocus();
                focused = true;
            }

            public void windowDeactivated(WindowEvent e) {
                parent.onBlur();
                focused = false;
            }
        });
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void registerInput(Input realInput) {
        Input input = realInput;
        canvas.addMouseListener(input);
        canvas.addKeyListener(input);
        canvas.addMouseWheelListener(input);
        canvas.addMouseMotionListener(input);
    }

    @Override
    public boolean isFullscreen() {
        return fullscreen;
    }

    private void setFullscreen(boolean value) {
        setVisible(false);
        setExtendedState(value ? MAXIMIZED_BOTH : NORMAL);

        setUndecorated(value);
        setVisible(true);
    }

    @Override
    public void toggleFullscreen() {
        fullscreen = !fullscreen;
        setFullscreen(fullscreen);
    }

    @Override
    public void updateDisplay() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void setClearColor(float r, float g, float b, float alpha) {

    }

    @Override
    public boolean shouldExit() {
        // TODO
        return false;
    }

    @Override
    public boolean isVSyncEnabled() {
        return false;
    }

    @Override
    public void onResize(Consumer<SimpleVector2f> event) {
        resizeHandlers.subscribe(event);
    }

    @Override
    public void stop() {
        dispose();
    }
}
