package org.play_ground;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.gui2.styles.ButtonStyles;
import org.glib2.interfaces.VoidCallback;
import org.gui.components.Button;
import org.gui.components.Panel;
import org.gui.components.VerticalScrollPanel;
import org.gui.layouts.VerticalLayout;
import org.gui.utils.ColorBox;

import java.awt.*;


public class MainMenu extends Panel {
    private final static int                 BUTTON_HEIGHT = 40;
    private final        ButtonStyles        style         = new ButtonStyles();
    private final        VerticalScrollPanel panel;

    public MainMenu() {
        VerticalLayout layout = new VerticalLayout();
        setLayout(layout);
        setHorizontalOffset(150);
        panel = new VerticalScrollPanel();
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
        addComponent(panel);
        style.backgroundColor = Color.GREEN;
        style.borderWidth = 5;
        style.fontColor = Color.BLACK;
        style.fontSize = 36;
        style.round = StaticConfig.DEFAULT_ROUND;
        style.fontName = "Monospaced";

        addButton("Test output", () -> System.out.println("Výpis funguje"));
    }

    @Override
    public void onResize() {
        panel.scrollToTop();
        panel.setHeight(this.getHeight());
        super.onResize();
    }

    public void addButton(String title, VoidCallback listener) {
        Button button = new Button(title);
        button.getStateHolder().getDefault().setBackgroundColor(Color.cyan);
        button.getStateHolder().setActive(new ColorBox(Color.RED, Color.blue, 5));
        button.setHeight(BUTTON_HEIGHT);
        button.setClickHandler(listener);
        style.setTo(button);
        // addComponent(button);

        panel.addComponent(button);
    }

}
