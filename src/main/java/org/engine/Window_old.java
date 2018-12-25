package org.engine;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window_old extends JFrame implements ComponentListener, WindowListener {
    @NotNull
    private final CoreEngine parent;
    private       boolean    focused = true;

    public Window_old(@NotNull CoreEngine parent, @NotNull String title, int width, int height) {
        this.parent = parent;
        setTitle(title);
        setResizable(true);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		setFullscreen(true);
        setVisible(true);

        addComponentListener(this);
        addWindowListener(this);
    }

    public void setFullscreen(boolean value) {
        setVisible(false);
        setExtendedState(value ? MAXIMIZED_BOTH : NORMAL);

        setUndecorated(value);
        setVisible(true);
    }

    public void componentHidden(ComponentEvent e) {}

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}

    public void windowDeactivated(WindowEvent e) {
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

    public void windowIconified(WindowEvent e) {
        parent.onBlur();
        focused = false;
    }

    public void windowClosing(WindowEvent e) {
        parent.onExit();
    }

    public void windowOpened(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {}

    public void componentResized(ComponentEvent e) {
        parent.onResize();
    }

    public boolean isFocused() {
        return this.focused;
    }
}
