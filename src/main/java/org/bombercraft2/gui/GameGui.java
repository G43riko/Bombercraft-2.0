package org.bombercraft2.gui;

import org.bombercraft2.core.Render;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.gui.submenu.SubMenuCreator;
import org.bombercraft2.gui.submenu.Submenu;
import org.engine.Input;
import org.glib2.interfaces.ClickAble;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.utils.enums.Keys;

import java.awt.*;

public class GameGui implements InteractAbleG2, ClickAble {
    private final GameLogs    gameLogs;
    private final NavBar      navBar;
    private final SideBar     sideBar;
    private final Submenu     subMenu;
    private final PlayerPanel playerPanel;
    private final GameAble    parent;

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
    public void render(@NotNull Graphics2D g2) {
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
//			miniMap.draw(g2);

        if (parent.getVisibleOption(Render.LOGS)) {
            gameLogs.render(g2);
        }
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.E)) {
            sideBar.setVisible(!sideBar.isVisible());
        }
        if (Input.getKeyDown(Keys.Q)) {
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
