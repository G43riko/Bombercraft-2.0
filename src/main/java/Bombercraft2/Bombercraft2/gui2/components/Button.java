package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.core.BasicListener;
import Bombercraft2.Bombercraft2.gui2.core.*;
import Bombercraft2.Bombercraft2.gui2.core.TextField;

import java.awt.*;

public class Button extends DrawableComponent {
    protected TextField textField;
    protected final int              round            = 0;
    protected       TextAlignment    textAlignment    = new TextAlignment();
    protected       boolean          disabled         = false;
    protected       ColorBox         colorBox         = new ColorBox();
    protected       ColorBox         disabledColorBox = null;
    protected       ColorBox         hoverColorBox    = null;
    protected       ColorBox         activeColorBox   = null;
    protected       ColorBox         currentColor     = colorBox;
    protected final MouseInteractive mouseInteractive = new MouseInteractive(this);

    public Button(String text) {
        this(text, TextAlignment.HORIZONTAL_ALIGN_CENTER);
    }

    public Button(String text, byte horizontalAlignment) {
        textField = new TextField(text);
        textAlignment.setHorizontalAlign(horizontalAlignment);
    }

    public void update(UpdateData data) {
        mouseInteractive.update(data);

        if (disabled && disabledColorBox != null) {
            currentColor = disabledColorBox;
            getManager().setDisabledCursor();
        }
        else if (!mouseInteractive.isHover()) {
            currentColor = colorBox;
        }
        else {
            getManager().setHoverCursor();
            if (activeColorBox != null && mouseInteractive.isActive()) {
                currentColor = activeColorBox;
            }
            else if (hoverColorBox != null) {
                currentColor = hoverColorBox;
            }
            else {
                currentColor = colorBox;
            }
        }
    }

    @Override
    public void onResize() {

    }

    public void setClickHandler(BasicListener handler) {
        mouseInteractive.setClickHandler(handler);
    }

    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        if(round > 0) {
        }
        currentColor.render(g2, this);
        textAlignment.renderText(g2, textField, this);
    }

    public boolean isDisabled() {return disabled;}

    public void setDisabled(boolean disabled) {this.disabled = disabled;}

    public TextAlignment getTextAlignment() {return textAlignment;}

    public TextField getTextField() {return textField;}

    public ColorBox getActiveColorBox() {return activeColorBox;}

    public ColorBox getHoverColorBox() {return hoverColorBox;}

    public ColorBox getColorBox() {return colorBox;}

    public ColorBox getDisabledColorBox() {return disabledColorBox;}

    public void setColorBox(ColorBox colorBox) {this.colorBox = colorBox;}

    public void setDisabledColorBox(ColorBox disabledColorBox) {this.disabledColorBox = disabledColorBox;}

    public void setTextField(TextField textField) {this.textField = textField;}

    public void setHoverColorBox(ColorBox hoverColorBox) {this.hoverColorBox = hoverColorBox;}

    public void setTextAlignment(TextAlignment textAlignment) {this.textAlignment = textAlignment;}

    public void setActiveColorBox(ColorBox activeColorBox) {this.activeColorBox = activeColorBox;}
}
