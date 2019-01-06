package org.bombercraft2.gui.components;

import org.glib2.interfaces.Visible;
import org.glib2.math.vectors.GVector2f;

import java.awt.*;

public class MiniButton extends GuiComponent {
    public MiniButton(Visible parent, String text) {
        this(parent, text, SIDEBAR_DEFAULT_BUTTON_HEIGHT);
    }

    private MiniButton(Visible parent, String text, int height) {
        super(parent);
        this.size = new GVector2f(0, height);
        this.text = text;
        init();

        if (!buttons.containsKey(parent)) { buttons.put(parent, 0); }

        topCousePrevButtons = buttons.get(parent);
        buttons.put(parent, topCousePrevButtons + height);

        calcPosition();
    }

    protected void init() {
        offset = new GVector2f(2, 2);
        textSize = 20;
        textOffset = new GVector2f(4, 0);
        round = 10;
        borderWidth = 1;

        hoverColor = Color.GRAY;
        textColor = Color.WHITE;
        backgroundColor = Color.DARK_GRAY;
        borderColor = Color.LIGHT_GRAY;
    }

    @Override
    public void calcPosition() {
        position = getParent().getPosition().getAdd(offset.getAdd(new GVector2f(0, topCousePrevButtons)));
        size = new GVector2f(getParent().getSize().getXi() - 2 * offset.getX(), size.getY() - offset.getY());
    }

}
