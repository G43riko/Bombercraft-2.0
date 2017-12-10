package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.core.*;
import Bombercraft2.Bombercraft2.gui2.core.TextField;

import java.awt.*;

public class Button extends DrawableComponent {
    protected TextAlignment textAlignment    = new TextAlignment();
    protected TextField     textField;
    protected boolean       disabled         = false;
    protected ColorBox      colorBox         = new ColorBox();
    protected ColorBox      disabledColorBox = null;
    protected ColorBox      hoverColorBox    = null;
    protected ColorBox      activeColorBox   = null;


    public Button(String text) {
        this(text, TextAlignment.HORIZONTAL_ALIGN_CENTER);
    }
    public Button(String text, byte horizontalAlignment) {
        textField = new TextField(text);
        textAlignment.setHorizontalAlign(horizontalAlignment);
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
            if (activeColorBox != null && GuiConnector.isButtonDown()) {
                activeColorBox.render(g2, this);
            }
            else if (hoverColorBox != null) {
                hoverColorBox.render(g2, this);
            }
            else {
                colorBox.render(g2, this);
            }
        }
    }
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        renderBackground(g2);
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
