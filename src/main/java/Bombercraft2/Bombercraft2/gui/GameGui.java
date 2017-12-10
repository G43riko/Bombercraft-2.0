package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.submenu.SubMenuCreator;
import Bombercraft2.Bombercraft2.gui.submenu.Submenu;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

import java.awt.*;

public class GameGui implements InteractAble, ClickAble {
    private GameLogs    gameLogs;
    private NavBar      navBar;
    private SideBar     sideBar;
    private Submenu     subMenu;
    private PlayerPanel playerPanel;
    private GameAble    parent;

    public GameGui(GameAble parent) {
        this.parent = parent;
        gameLogs = new GameLogs(parent);
        navBar = new NavBar(parent);
        sideBar = new SideBar(parent);
        playerPanel = new PlayerPanel(parent);
        subMenu = new Submenu(parent, SubMenuCreator.generateSubmenu(parent));

    }

    public void calcPosition() {
        sideBar.calcPosition();
        navBar.calcPosition();
    }

    public NavBar getNavBar() {
        return navBar;
    }


    @Override
    public void render(Graphics2D g2) {
        if (navBar.isVisible()) {
            navBar.render(g2);
        }

        if (sideBar.isVisible()) {
            sideBar.render(g2);
        }
        if (subMenu.isVisible()) {
            subMenu.render(g2);
        }

        if (playerPanel.isVisible()) {
            playerPanel.render(g2);
        }
//		if(Bombercraft.getViewOption("renderMiniMap"))
//			miniMap.render(g2);

        if (parent.getVisibleOption(Render.LOGS)) {
            gameLogs.render(g2);
        }
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_E)) {
            sideBar.setVisible(!sideBar.isVisible());
        }
        if (Input.getKeyDown(Input.KEY_Q)) {
            subMenu.setVisible(!subMenu.isVisible());
        }

        if (navBar.isVisible()) {
            navBar.input();
        }
        if (subMenu.isVisible()) {
            subMenu.input();
        }
    }

    @Override
    public void update(float delta) {
        if (sideBar.isVisible()) {
            sideBar.update(delta);
        }
    }

    @Override
    public void doAct(GVector2f click) {
        if (navBar.isVisible()) {
            navBar.doAct(click);
        }

        if (sideBar.isVisible()) {
            sideBar.doAct(click);
        }
    }

}
