package org.bombercraft2.gui.menus;

import org.bombercraft2.core.GameStateType;
import org.bombercraft2.core.MenuAble;
import org.bombercraft2.core.Texts;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

import java.awt.*;

public class OptionsMenu extends Menu {

    public OptionsMenu(MenuAble parent) {
        super(parent, GameStateType.OptionsMenu);
        this.parent = parent;
        position.setY(100);
        init();
    }

    private void init() {
        setItem(Texts.BACK);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, size.getXi(), size.getYi());
        super.render(g2);
    }

    @Override
    public void doAct(BVector2f click) {
        if (components.get(Texts.BACK).isClickIn(click)) {
            parent.showMainMenu();
        }
    }

    @Override
    public void calcPosition() {
        // TODO Auto-generated method stub

    }

}
