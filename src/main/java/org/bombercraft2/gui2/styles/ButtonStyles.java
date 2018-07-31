package org.bombercraft2.gui2.styles;

import org.gui.components.Button;
import org.gui.core.text.TextAlignment;
import org.gui.core.text.TextField;

import java.awt.*;

public class ButtonStyles {
    public final int    fontStyle            = Font.BOLD | Font.ITALIC;
    public       String fontName             = "Garamond";
    public       int    fontSize             = 20;
    public       Color  fontColor            = Color.BLACK;
    public       Color  backgroundColor      = Color.WHITE;
    public       Color  borderColor          = Color.BLACK;
    public       int    borderWidth          = 1;
    public       int    round                = 2;
    public       byte   verticalTextAlign    = TextAlignment.VERTICAL_ALIGN_MIDDLE;
    public       byte   horizontalTextAlign  = TextAlignment.HORIZONTAL_ALIGN_CENTER;
    public       int    verticalTextOffset   = 0;
    public       int    horizontalTextOffset = 0;

    public void setTo(Button button) {
        if (button.getTextField() == null) {
            button.setTextField(new TextField("", fontName, fontSize, fontStyle));
        }
        else {
            button.getTextField().setColor(fontColor);
            button.getTextField().setName(fontName);
            button.getTextField().setSize(fontSize);
        }

        button.getStateHolder().getDefault().setBackgroundColor(backgroundColor);
        button.getStateHolder().getDefault().setBorderColor(borderColor);
        button.getStateHolder().getDefault().setBorderWidth(borderWidth);

        if (button.getTextAlignment() == null) {
            button.setTextAlignment(new TextAlignment(verticalTextAlign,
                                                      horizontalTextAlign,
                                                      verticalTextOffset,
                                                      horizontalTextOffset));
        }
        else {
            button.getTextAlignment().setHorizontalAlign(horizontalTextAlign);
            button.getTextAlignment().setHorizontalOffset(horizontalTextOffset);
            button.getTextAlignment().setVerticalAlign(verticalTextAlign);
            button.getTextAlignment().setVerticalOffset(verticalTextOffset);
        }
    }

    public void loadFrom(Button button) {
        if (button.getTextField() != null) {
            fontColor = button.getTextField().getColor();
            fontName = button.getTextField().getName();
            fontSize = button.getTextField().getSize();
        }

        backgroundColor = button.getStateHolder().getDefault().getBackgroundColor();
        borderColor = button.getStateHolder().getDefault().getBorderColor();
        borderWidth = button.getStateHolder().getDefault().getBorderWidth();

        if (button.getTextAlignment() != null) {
            horizontalTextAlign = button.getTextAlignment().getHorizontalAlign();
            horizontalTextOffset = button.getTextAlignment().getHorizontalOffset();
            verticalTextAlign = button.getTextAlignment().getVerticalAlign();
            verticalTextOffset = button.getTextAlignment().getVerticalOffset();
        }
    }
}
