package org.play_ground;

import org.gui.components.Button;
import org.gui.components.Checkbox;
import org.gui.core.text.TextAlignment;
import org.gui.utils.RoundedColorBox;

import java.awt.*;

public class GuiDemo extends CoreGuiDemo {
    public GuiDemo() {
        super(60, 60, false);
    }

    @Override
    protected void initComponents() {
        addButton("cent-cent", 20, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addButton("right-cent", 80, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addButton("left-cent", 140, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_MIDDLE);

        addButton("cent-top", 200, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_TOP);
        addButton("right-top", 260, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_TOP);
        addButton("left-top", 320, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_TOP);

        addButton("cent-bott", 380, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addButton("right-bott", 440, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addButton("left-bott", 500, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_BOTTOM);

        addCheckbox1("cent-cent", 20, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addCheckbox1("right-cent", 80, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addCheckbox1("left-cent", 140, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_MIDDLE);

        addCheckbox1("cent-top", 200, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_TOP);
        addCheckbox1("right-top", 260, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_TOP);
        addCheckbox1("left-top", 320, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_TOP);

        addCheckbox1("cent-bott", 380, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addCheckbox1("right-bott", 440, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addCheckbox1("left-bott", 500, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_BOTTOM);

        addCheckbox2("cent-cent", 20, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addCheckbox2("right-cent", 80, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_MIDDLE);
        addCheckbox2("left-cent", 140, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_MIDDLE);

        addCheckbox2("cent-top", 200, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_TOP);
        addCheckbox2("right-top", 260, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_TOP);
        addCheckbox2("left-top", 320, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_TOP);

        addCheckbox2("cent-bott", 380, TextAlignment.HORIZONTAL_ALIGN_CENTER, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addCheckbox2("right-bott", 440, TextAlignment.HORIZONTAL_ALIGN_RIGHT, TextAlignment.VERTICAL_ALIGN_BOTTOM);
        addCheckbox2("left-bott", 500, TextAlignment.HORIZONTAL_ALIGN_LEFT, TextAlignment.VERTICAL_ALIGN_BOTTOM);

    }


    private void addCheckbox1(String text, int y, byte horizontalTextAlignment, byte verticalTextAlignment) {
        Checkbox button = new Checkbox(text);
        button.setHeight(40);
        button.setWidth(200);
        button.setX(240);
        button.setY(y);
        button.setTextAlignment(new TextAlignment(verticalTextAlignment, horizontalTextAlignment));
        button.setButtonAlign(Checkbox.LEFT);
        button.getStateHolder().setDefault(new RoundedColorBox(new Color(55, 196, 111), Color.WHITE, 2, 5));
        button.getStateHolder().setHover(new RoundedColorBox(new Color(35, 176, 91), Color.WHITE, 2, 5));
        button.getStateHolder().setActive(new RoundedColorBox(new Color(25, 166, 81), Color.WHITE, 2, 5));

        guiManager.getMainPanel().addComponent(button);
    }

    private void addCheckbox2(String text, int y, byte horizontalTextAlignment, byte verticalTextAlignment) {
        Checkbox button = new Checkbox(text);
        button.setHeight(40);
        button.setWidth(200);
        button.setX(460);
        button.setY(y);
        button.setTextAlignment(new TextAlignment(verticalTextAlignment, horizontalTextAlignment));
        button.setButtonAlign(Checkbox.RIGHT);
        button.getStateHolder().setDefault(new RoundedColorBox(new Color(196, 55, 111), Color.WHITE, 2, 5));
        button.getStateHolder().setHover(new RoundedColorBox(new Color(176, 35, 91), Color.WHITE, 2, 5));
        button.getStateHolder().setActive(new RoundedColorBox(new Color(166, 25, 81), Color.WHITE, 2, 5));

        guiManager.getMainPanel().addComponent(button);
    }

    private void addButton(String text, int y, byte horizontalTextAlignment, byte verticalTextAlignment) {
        Button button = new Button(text);
        button.setSize(200, 40);
        button.setPosition(20, y);
        button.setTextAlignment(new TextAlignment(verticalTextAlignment, horizontalTextAlignment));
        button.getStateHolder().getDefault().setBackgroundColor(Color.cyan);
        button.getStateHolder().setDefault(new RoundedColorBox(new Color(55, 111, 196), Color.WHITE, 2, 5));
        button.getStateHolder().setHover(new RoundedColorBox(new Color(35, 91, 176), Color.WHITE, 2, 5));
        button.getStateHolder().setActive(new RoundedColorBox(new Color(25, 81, 166), Color.WHITE, 2, 5));
//        button.getTextField().setSize(36);
        button.setClickHandler(() -> {});

        guiManager.getMainPanel().addComponent(button);
    }
}
