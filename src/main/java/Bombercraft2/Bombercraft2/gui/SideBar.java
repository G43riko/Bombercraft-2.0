package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import Bombercraft2.Bombercraft2.gui.components.MiniButton;
import Bombercraft2.Bombercraft2.gui.components.MiniSwitch;
import utils.math.GVector2f;

import java.awt.*;
import java.util.HashMap;

public class SideBar extends Bar {
    private int                           offset  = Config.SIDEBAR_OFFSET;
    private HashMap<String, GuiComponent> buttons = new HashMap<>();

    public SideBar(GameAble parent) {
        super(parent, new GVector2f(200, 0));
        calcPosition();
        buttons.put("newGame", new MiniButton(this, "New game"));
        buttons.put("resetGame", new MiniButton(this, "Reset game"));
        buttons.put("showLogs", new MiniSwitch(this, "Show logs", parent.getVisibleOption(Render.LOGS)));
        buttons.put("showMinimap", new MiniSwitch(this, "Show minimap", parent.getVisibleOption(Render.MINI_MAP)));
        buttons.put("showLights", new MiniSwitch(this, "Show shadows", parent.getVisibleOption(Render.LIGHTS)));
        buttons.put("showLightMap",
                    new MiniSwitch(this, "Show light map", parent.getVisibleOption(Render.ONLY_SHADOW_MAP)));
        buttons.put("showWalls", new MiniSwitch(this, "Show walls", parent.getVisibleOption(Render.MAP_WALLS)));

        init();

    }

    private void init() {
        setBackgroundColor(Config.SIDEBAR_BACKGROUND_COLOR);
        setBorderColor(Config.SIDEBAR_BORDER_COLOR);
        setVisible(false);
        setBorderWidth(Config.SIDEBAR_BORDER_WIDTH);
    }

    @Override
    public void calcPosition() {
        final int localOffset = 30;
        totalPos = new GVector2f(getParent().getCanvas().getWidth() - size.getX() + offset, offset);
        totalPos.addToX(-localOffset);
        totalSize = new GVector2f(size).sub(offset * 2);
        totalSize.addToX(localOffset);

        if (size.getY() == 0) {
            totalSize.setY(getParent().getCanvas().getHeight() - offset * 2);
        }

        if (size.getX() == 0) {
            totalSize.setX(getParent().getCanvas().getWidth() - offset * 2);
        }

        buttons.forEach((key, value) -> value.calcPosition());
    }

    @Override
    public void update(float delta) {
        buttons.forEach((key, value) -> value.update(delta));
    }

    @Override
    public void render(Graphics2D g2) {

        g2.setColor(getBackgroundColor());
        g2.fillRoundRect(totalPos.getXi(),
                         totalPos.getYi(),
                         totalSize.getXi(),
                         totalSize.getYi(),
                         Config.DEFAULT_ROUND,
                         Config.DEFAULT_ROUND);

        g2.setStroke(new BasicStroke(getBorderWidth()));
        g2.setColor(getBorderColor());
        g2.drawRoundRect(totalPos.getXi(),
                         totalPos.getYi(),
                         totalSize.getXi(),
                         totalSize.getYi(),
                         Config.DEFAULT_ROUND,
                         Config.DEFAULT_ROUND);


        buttons.forEach((key, value) -> value.render(g2));
    }

    @Override
    public GVector2f getPosition() {
        return totalPos;
    }

    @Override
    public GVector2f getSize() {
        return totalSize;
    }

    @Override
    public void doAct(GVector2f click) {
        if (!isVisible()) { return; }
        if (buttons.get("newGame").isClickIn(click)) {
            getParent().newGame();
        }
        else if (buttons.get("resetGame").isClickIn(click)) {
            getParent().resetGame();
        }
        else if (buttons.get("showLogs").isClickIn(click)) {
            getParent().switchVisibleOption(Render.LOGS);
        }
        else if (buttons.get("showMinimap").isClickIn(click)) {
            getParent().switchVisibleOption(Render.MINI_MAP);
        }
        else if (buttons.get("showLights").isClickIn(click)) {
            getParent().switchVisibleOption(Render.LIGHTS);
        }
        else if (buttons.get("showLightMap").isClickIn(click)) {
            getParent().switchVisibleOption(Render.ONLY_SHADOW_MAP);
        }
        else if (buttons.get("showWalls").isClickIn(click)) {
            getParent().switchVisibleOption(Render.MAP_WALLS);
        }


    }


}
