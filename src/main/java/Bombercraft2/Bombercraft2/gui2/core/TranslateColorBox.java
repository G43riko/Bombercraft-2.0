package Bombercraft2.Bombercraft2.gui2.core;

import Bombercraft2.Bombercraft2.gui2.core.translation.TranslateColor;
import Bombercraft2.Bombercraft2.gui2.utils.ColorBox;

public class TranslateColorBox extends ColorBox {
    private TranslateColor backgroundTranslation;
    private TranslateColor borderTranslation;

    public TranslateColorBox(TranslateColor backgroundTranslation, TranslateColor borderTranslation) {
        super(backgroundTranslation.getStartColor(), borderTranslation.getStartColor(), 1);
        this.backgroundTranslation = backgroundTranslation;
        this.borderTranslation = borderTranslation;
    }
    public TranslateColorBox(TranslateColor backgroundTranslation) {
        super(backgroundTranslation.getStartColor());
        this.backgroundTranslation = backgroundTranslation;
    }

    public void setHover(boolean value) {
        setHover(value, false);
    }
    public void setHover(boolean value, boolean force) {
        if (force) {
            setBackgroundColor(backgroundTranslation.getEndColor());
            return;
        }
        if (backgroundTranslation != null) {
            if (backgroundTranslation.setValue(value)) {
                setBackgroundColor(backgroundTranslation.getActualColor());
            }
        }

        if (borderTranslation != null) {
            if (borderTranslation.setValue(value)) {
                setBorderColor(borderTranslation.getActualColor());
            }
        }
    }

    public TranslateColor getBackgroundTranslation() {return backgroundTranslation;}

    public TranslateColor getBorderTranslation() {return borderTranslation;}

    public void setBackgroundTranslation(TranslateColor backgroundTranslation) {this.backgroundTranslation = backgroundTranslation;}

    public void setBorderTranslation(TranslateColor borderTranslation) {this.borderTranslation = borderTranslation;}
}
