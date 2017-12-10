package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.core.*;
import Bombercraft2.Bombercraft2.gui2.core.TextField;

import java.awt.*;

public class Button extends DrawableComponent {
    private TextAlignment textAlignment    = new TextAlignment();
    private TextField     textField        = null;
    private ColorBox      colorBox         = new ColorBox();
    private ColorBox      disabledColorBox = null;
    private ColorBox      hoverColorBox    = null;
    private ColorBox      activeColorBox   = null;


    public Button(String text) {
        textField = new TextField(text);
    }

    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        colorBox.render(g2, this);
        textAlignment.renderText(g2, textField, this);
    }

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
