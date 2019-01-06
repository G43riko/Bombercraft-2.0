package org.bombercraft2.gui.components;

import org.bombercraft2.StaticConfig;
import org.engine.Input;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.interfaces.Visible;
import org.glib2.math.physics.Collisions;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;

public abstract class GuiComponent implements InteractAbleG2, Visible {
    final static int   SIDEBAR_DEFAULT_BUTTON_HEIGHT = 30;
    final static float CENTER_ALIGN                  = -1.111111f;

    static final  HashMap<Visible, Integer> buttons = new HashMap<>();
    private final Visible                   parent;
    GVector2f offset              = new GVector2f();
    GVector2f textOffset          = new GVector2f();
    GVector2f position;
    GVector2f size;
    int       textSize            = 20;
    int       round               = 0;
    int       borderWidth         = 0;
    int       topCousePrevButtons = 0;
    boolean   hover               = false;
    boolean   value               = true;
    String    text                = "";
    Color     backgroundColor     = Color.WHITE;
    Color     borderColor         = Color.black;
    Color     textColor           = Color.black;
    Color     hoverColor          = Color.lightGray;
    Color     disabledColor       = Color.DARK_GRAY;
    String    font                = StaticConfig.DEFAULT_FONT;
    private boolean disable = false;

    GuiComponent(Visible parent) {
        this.parent = parent;
        position = parent.getPosition();
        size = parent.getSize();
    }

    protected abstract void init();

    private void clickIn() {
        value = !value;
    }

    public boolean isClickIn(GVector2f click) {
        if (disable) {
            return false;
        }

        boolean result = Collisions._2D.pointRect(click.getX(),
                                                  click.getY(),
                                                  position.getX(),
                                                  position.getY(),
                                                  size.getX(),
                                                  size.getY());

        if (result) {
            clickIn();
        }

        return result;
    }

    @Override
    public void update(float delta) {
        if (disable) {
            return;
        }
        GVector2f mousePosition = Input.getMousePosition();
        hover = Collisions._2D.pointRect(position.getX(),
                                         position.getY(),
                                         size.getX(),
                                         size.getY(),
                                         mousePosition.getX(),
                                         mousePosition.getY());
    }

    public void calcPosition() {
        position = getParent().getPosition().getAdd(offset.getAdd(new GVector2f(0, topCousePrevButtons)));

    }

    public void calcPosAndSize() {
        position = getParent().getPosition().getAdd(offset.getAdd(new GVector2f(0, topCousePrevButtons)));
//		size.setX(getParent().getSize().getXi() - 2 * offset.getX());
        size = new GVector2f(getParent().getSize().getXi() - 2 * offset.getX(),
                             size.getY() - offset.getY());

    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (disable) {
            g2.setColor(disabledColor);
        }
        else if (hover) {
            g2.setColor(hoverColor);
        }
        else {
            g2.setColor(backgroundColor);
        }

        g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);

        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));
            g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
        }

        g2.setColor(textColor);
        g2.setFont(new Font(font, Font.BOLD | Font.ITALIC, textSize));
        if (text != null && !text.isEmpty() && textSize > 0) {
            if (textOffset.getX() == CENTER_ALIGN) {
                g2.drawString(text,
                              position.getX() + (size.getX() - g2.getFontMetrics().stringWidth(text)) / 2,
                              position.getY() + textSize + textOffset.getY());
            }
            else {
                g2.drawString(text,
                              position.getX() + textOffset.getX(),
                              position.getY() + textSize + textOffset.getY());
            }
        }
    }

    public int getTextWidth(Graphics2D g2) {
        g2.setColor(textColor);
        g2.setFont(new Font(font, Font.BOLD | Font.ITALIC, textSize));
        return g2.getFontMetrics().stringWidth(text);
    }


    public void setDisable(boolean disable) {this.disable = disable;}

    Visible getParent() {return parent;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getSize() {return size;}
}
