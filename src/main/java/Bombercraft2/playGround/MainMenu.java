package Bombercraft2.playGround;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.BasicListener;
import Bombercraft2.Bombercraft2.gui2.components.Button;
import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.utils.ColorBox;
import Bombercraft2.Bombercraft2.gui2.layouts.VerticalLayout;
import Bombercraft2.Bombercraft2.gui2.styles.ButtonStyles;

import java.awt.*;


public class MainMenu extends Panel {
    private final static int          BUTTON_HEIGHT = 40;
    private final        ButtonStyles style         = new ButtonStyles();

    public MainMenu() {
        setLayout(new VerticalLayout());
        setHorizontalOffset(150);
        style.backgroundColor = Color.GREEN;
        style.borderWidth = 5;
        style.fontColor = Color.BLACK;
        style.fontSize = 36;
        style.round = Config.DEFAULT_ROUND;
        style.fontName = "Monospaced";

        addButton("Test output", () -> System.out.println("Výpis funguje"));
    }

    public void addButton(String title, BasicListener listener) {
        Button button = new Button(title);
        button.getColorBox().setBackgroundColor(Color.cyan);
        button.setActiveColorBox(new ColorBox(Color.RED, Color.blue, 5));
        button.setHeight(BUTTON_HEIGHT);
        button.setClickHandler(listener);
        style.setTo(button);
        addComponent(button);
    }
}
