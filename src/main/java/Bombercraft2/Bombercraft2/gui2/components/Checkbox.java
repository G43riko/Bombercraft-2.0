package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.TextAlignment;

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
    private boolean  wasDown      = false;
    public Checkbox(String text) {
        super(text, TextAlignment.HORIZONTAL_ALIGN_LEFT);
    }

    protected void renderBackground(Graphics2D g2) {
        if(disabled && disabledColorBox != null) {
            disabledColorBox.render(g2, this);
            getManager().setDisabledCursor();
        }
        else if(!GuiConnector.isMouseOn(this)) {
            colorBox.render(g2, this);
        }
        else {
            getManager().setHoverCursor();
            if (GuiConnector.isButtonDown()) {
                getManager().setActiveCursor();
                if( activeColorBox != null) {
                    activeColorBox.render(g2, this);
                }
                else if (hoverColorBox != null) {
                    hoverColorBox.render(g2, this);
                }
                else {
                    colorBox.render(g2, this);
                }
                wasDown = true;
            }
            else {
                if(wasDown){
                    value = !value;
                    wasDown = false;
                }
                if (hoverColorBox != null) {
                    hoverColorBox.render(g2, this);
                }
                else{
                    colorBox.render(g2, this);
                }
            }

        }
    }
    @Override
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        renderBackground(g2);
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
