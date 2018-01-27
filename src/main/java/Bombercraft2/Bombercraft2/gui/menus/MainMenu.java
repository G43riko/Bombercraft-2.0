package Bombercraft2.Bombercraft2.gui.menus;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class MainMenu extends Menu {
    private final MenuAble parent;

    public MainMenu(MenuAble parent) {
        super(parent, GameState.Type.MainMenu);
        this.parent = parent;
        position.setY(100);
        init();
    }

    private void init() {
        setItem(Texts.NEW_GAME);
        setItem(Texts.CONTINUE_GAME);
        setItem(Texts.STOP_GAME);
        setItem(Texts.JOIN_GAME);
        setItem(Texts.CHANGE_PROFILE);
        setItem(Texts.OPTIONS);
        setItem(Texts.EXIT_GAME);
    }

    @Override
    public void doAct(GVector2f click) {
        if (components.get(Texts.EXIT_GAME).isClickIn(click)) {
            parent.exitGame();
        }
        else if (components.get(Texts.CHANGE_PROFILE).isClickIn(click)) {
            parent.showProfileMenu();
        }
        else if (components.get(Texts.NEW_GAME).isClickIn(click)) {
            parent.startNewGame();
        }
        else if (components.get(Texts.CONTINUE_GAME).isClickIn(click)) {
            parent.continueGame();
        }
        else if (components.get(Texts.STOP_GAME).isClickIn(click)) {
            parent.stopGame();
        }
        else if (components.get(Texts.JOIN_GAME).isClickIn(click)) {
            parent.showJoinMenu();
        }
        else if (components.get(Texts.OPTIONS).isClickIn(click)) {
            parent.showOptions();
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, size.getXi(), size.getYi());
        super.render(g2);
    }

    @Override
    public void calcPosition() {

    }

    @Override
    public void onResize() {
        for (GuiComponent component : components.values()) {
            component.calcPosAndSize();
        }
    }
}
