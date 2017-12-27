package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.TextAlignment;
import Bombercraft2.Bombercraft2.gui2.core.UpdateData;

import java.awt.*;

public class Checkbox extends Button {
    public static byte LEFT  = 1;
    public static byte RIGHT = 2;
    public static byte NONE  = 3;

    private boolean  value        = false;
    private int      buttonOffset = 5;
    private ColorBox trueBox      = new ColorBox(Color.green);
    private ColorBox falseBox     = new ColorBox(Color.red);
    private byte     buttonAlign  = LEFT;

    public Checkbox(String text) {
        super(text, TextAlignment.HORIZONTAL_ALIGN_LEFT);
        mouseInteractive.setClickHandler(() -> value = !value);
    }

    @Override
    public void update(UpdateData data) {
        super.update(data);
        if (disabled && disabledColorBox != null) {
            currentColor = disabledColorBox;
            getManager().setDisabledCursor();
        }
        else if (!mouseInteractive.isHover()) {
            currentColor = colorBox;
        }
        else {
            getManager().setHoverCursor();
            if (mouseInteractive.isActive()) {
                getManager().setActiveCursor();
                if (activeColorBox != null) {
                    currentColor = activeColorBox;
                }
                else if (hoverColorBox != null) {
                    currentColor = hoverColorBox;
                }
                else {
                    currentColor = colorBox;
                }
            }
            else {
                if (hoverColorBox != null) {
                    currentColor = hoverColorBox;
                }
                else {
                    currentColor = colorBox;
                }
            }

        }
    }

    @Override
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        currentColor.render(g2, this);
        final int buttonHeight = getHeight() - (buttonOffset << 1);
        if (buttonAlign == LEFT) {
            (value ? trueBox : falseBox).render(g2,
                                                getX() + buttonOffset,
                                                getY() + buttonOffset,
                                                buttonHeight, buttonHeight);
        }
        textAlignment.renderText(g2, textField, this, 0, getHeight());
    }


}
