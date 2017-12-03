package Bombercraft2.Bombercraft2.gui.menus;

import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.gui.StatsPanel;
import Bombercraft2.Bombercraft2.gui.components.Button;
import utils.math.GVector2f;

import java.awt.*;

public class EndGameMenu extends Menu {
    private final StatsPanel panel;
    private final Button mainMenuButton = new Button(this, "Hlavné menu");
    private final Button newGameButton  = new Button(this, "Nová hra");

    public EndGameMenu(MenuAble parent) {
        super(parent, Type.EndGameMenu);
        panel = new StatsPanel(parent.getGame(), new GVector2f(30, 200), parent.getGame().getStats());

        mainMenuButton.getSize().setX(300);
        mainMenuButton.getPosition().setY(500);

        newGameButton.getSize().setX(300);
        newGameButton.getPosition().set(450, 500);

    }

    @Override
    public void doAct(GVector2f click) {
        if (mainMenuButton.isClickIn(click)) {
            parent.stopGame();

        }
        else if (newGameButton.isClickIn(click)) {
            parent.continueGame();
            parent.getGame().newGame();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, size.getXi(), size.getYi());
        panel.render(g2);

        mainMenuButton.render(g2);
        newGameButton.render(g2);
    }

    @Override
    public void update(float delta) {
        mainMenuButton.update(delta);
        newGameButton.update(delta);
    }

    @Override
    public void calcPosition() {

    }

}
