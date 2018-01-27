package Bombercraft2.Bombercraft2.gui.menus;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class OptionsMenu extends Menu {

    public OptionsMenu(MenuAble parent) {
        super(parent, GameState.Type.OptionsMenu);
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
    public void doAct(GVector2f click) {
        if (components.get(Texts.BACK).isClickIn(click)) {
            parent.showMainMenu();
        }
    }

    @Override
    public void calcPosition() {
        // TODO Auto-generated method stub

    }

}
