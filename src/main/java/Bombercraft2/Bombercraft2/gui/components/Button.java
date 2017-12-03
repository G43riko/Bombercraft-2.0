package Bombercraft2.Bombercraft2.gui.components;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Visible;
import utils.math.GVector2f;

import java.awt.*;

public class Button extends GuiComponent {
    public Button(Visible parent, String text) {
        super(parent);
        this.text = text;
        init();

        if (!buttons.containsKey(parent)) {
            buttons.put(parent, 0);
        }

        topCousePrevButtons = buttons.get(parent);
        buttons.put(parent, topCousePrevButtons + size.getYi() + offset.getYi());

        calcPosAndSize();
        textOffset = new GVector2f(CENTER_ALIGN, 0);
    }

    protected void init() {
        disabledColor = Color.LIGHT_GRAY;
        backgroundColor = Color.GREEN;
        hoverColor = Color.DARK_GRAY;
        borderColor = Color.black;
        borderWidth = 5;
        textColor = Color.BLACK;
        textOffset = new GVector2f();
        offset = new GVector2f(40, 5);
        textSize = 36;
        round = Config.DEFAULT_ROUND;
        font = "Monospaced";
        size = new GVector2f(600, 50);
    }


}
