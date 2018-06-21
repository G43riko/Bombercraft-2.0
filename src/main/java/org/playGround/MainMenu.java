package org.playGround;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.gui2.components.Button;
import org.bombercraft2.gui2.components.Panel;
import org.bombercraft2.gui2.components.VerticalScrollPanel;
import org.bombercraft2.gui2.layouts.VerticalLayout;
import org.bombercraft2.gui2.styles.ButtonStyles;
import org.bombercraft2.gui2.utils.ColorBox;
import org.glib2.interfaces.VoidCallback;

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
        button.getColorBox().setBackgroundColor(Color.cyan);
        button.setActiveColorBox(new ColorBox(Color.RED, Color.blue, 5));
        button.setHeight(BUTTON_HEIGHT);
        button.setClickHandler(listener);
        style.setTo(button);
        // addComponent(button);

        panel.addComponent(button);
    }

}
