package org.bombercraft2.gui.submenu;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.gui.submenu.SubmenuItem.Types;
import org.engine.Input;
import org.utils.enums.Keys;
import utils.math.GVector2f;

import java.awt.*;
import java.util.List;


public class Submenu {
    private GVector2f         position      = null;
    private boolean           visible       = false;
    private List<SubmenuItem> items         = null;
    private int               selectedIndex = 0;
    private Submenu           children      = null;
    private Submenu           parent        = null;
    private GameAble          game          = null;

    public Submenu(GameAble game, List<SubmenuItem> items) {
        this(game, items, null);
    }

    private Submenu(GameAble game, List<SubmenuItem> items, Submenu parent) {
        this.parent = parent;
        this.items = items;
        this.game = game;

        position = parent == null ? StaticConfig.SUBMENU_DEFAULT_POSITION : parent.getChildrenPosition();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean value) {
        Submenu first = getFirst();
        first.visible = value;
        if (!first.visible) {
            first.closeChildren();
            first.selectedIndex = 0;
        }
    }

    private Submenu getFirst() {
        Submenu result = this;
        while (result.parent != null) {
            result = result.parent;
        }
        return result;
    }

    public void input() {
        if (children != null) {
            children.input();
            return;
        }
        if (Input.getKeyDown(Keys.ARROW_DOWN)) {
            selectedIndex++;
            if (selectedIndex == items.size()) {
                selectedIndex = 0;
            }
        }
        if (Input.getKeyDown(Keys.ARROW_UP)) {
            selectedIndex--;
            if (selectedIndex < 0) {
                selectedIndex = items.size() - 1;
            }
        }

        if (Input.getKeyDown(Keys.ARROW_RIGHT)) {
            SubmenuItem item = items.get(selectedIndex);
            if (!item.isFinal()) {
                openChildren(item);
            }
        }
        if (Input.getKeyDown(Keys.ARROW_LEFT)) {
            if (parent != null) {
                parent.closeChildren();
            }
            else {
                setVisible(false);
            }
        }
        if (Input.getKeyDown(Keys.ENTER)) {
            SubmenuItem item = items.get(selectedIndex);
            if (item.isFinal()) {
                if (item.fire()) {
                    setVisible(false);
                }
            }
            else {
                openChildren(item);
            }
        }
    }

    public void render(Graphics2D g2) {
        int submenuHeight = StaticConfig.SUBMENU_LINE_HEIGHT * items.size();
        g2.setColor(StaticConfig.SUBMENU_FILL_COLOR);
        g2.fillRoundRect(position.getXi(),
                         position.getYi(),
                         StaticConfig.SUBMENU_WIDTH,
                         submenuHeight,
                         StaticConfig.SUBMENU_ROUND,
                         StaticConfig.SUBMENU_ROUND);


        g2.setColor(StaticConfig.SUBMENU_FONT_COLOR);
        g2.setFont(new Font(StaticConfig.DEFAULT_FONT, Font.BOLD | Font.ITALIC, StaticConfig.DEFAULT_FONT_SIZE));
        for (int i = 0; i < items.size(); i++) {

            if (selectedIndex == i) {
                g2.setColor(StaticConfig.SUBMENU_ACTIVE_ITEM_FILL_COLOR);
                g2.fillRoundRect(position.getXi(),
                                 position.getYi() + i * StaticConfig.SUBMENU_LINE_HEIGHT,
                                 StaticConfig.SUBMENU_WIDTH,
                                 StaticConfig.SUBMENU_LINE_HEIGHT,
                                 StaticConfig.SUBMENU_LINE_ROUND,
                                 StaticConfig.SUBMENU_LINE_ROUND);
            }

            SubmenuItem a = items.get(i);
            if (a.isFinal()) {
                int beginTextX = position.getXi();
                int beginTextY = position.getYi() + i * StaticConfig.SUBMENU_LINE_HEIGHT;
                int imageOffset = 0;

                if (a.getType() == Types.ICONABLE) {
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;

                    a.renderIcon(g2, beginTextX, beginTextY);
//
                    imageOffset += StaticConfig.SUBMENU_LINE_HEIGHT;
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;
                }
                else if (a.getType() == Types.CHECKBOX) {
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;

                    a.renderCheckbox(g2, beginTextX, beginTextY);
                    imageOffset += StaticConfig.SUBMENU_LINE_HEIGHT;
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;
                }
                else if (a.getType() == Types.RADIO && a.isSelected()) {
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;

                    a.renderRadio(g2, beginTextX, beginTextY);
                    imageOffset += StaticConfig.SUBMENU_LINE_HEIGHT;
                    beginTextX += StaticConfig.SUBMENU_ICON_OFFSET;
                }

                g2.setColor(StaticConfig.SUBMENU_FONT_COLOR);
                g2.setFont(new Font(StaticConfig.DEFAULT_FONT,
                                    Font.BOLD | Font.ITALIC,
                                    StaticConfig.DEFAULT_FONT_SIZE));
                g2.drawString(a.getLabel(),
                              beginTextX + StaticConfig.SUBMENU_FONT_HORIZONTAL_OFFSET + imageOffset,
                              beginTextY + StaticConfig.SUBMENU_FONT_VERTICAL_OFFSET + StaticConfig.SUBMENU_FONT_SIZE);


            }
            else {
                int yOffset = i * StaticConfig.SUBMENU_LINE_HEIGHT + StaticConfig.SUBMENU_FONT_VERTICAL_OFFSET + StaticConfig.SUBMENU_FONT_SIZE;
                g2.setColor(StaticConfig.SUBMENU_FONT_COLOR);
                g2.setFont(new Font(StaticConfig.DEFAULT_FONT,
                                    Font.BOLD | Font.ITALIC,
                                    StaticConfig.SUBMENU_FONT_SIZE));
                g2.drawString(a.getLabel(),
                              position.getXi() + StaticConfig.SUBMENU_FONT_HORIZONTAL_OFFSET,
                              position.getYi() + yOffset);
            }
        }

        g2.setStroke(new BasicStroke(StaticConfig.SUBMENU_BORDER_WIDTH));
        g2.setColor(StaticConfig.SUBMENU_BORDER_COLOR);
        g2.drawRoundRect(position.getXi(),
                         position.getYi(),
                         StaticConfig.SUBMENU_WIDTH,
                         submenuHeight,
                         StaticConfig.SUBMENU_ROUND,
                         StaticConfig.SUBMENU_ROUND);


        if (children != null) {
            children.render(g2);
        }
    }

    private GVector2f getChildrenPosition() {
        return new GVector2f(position.getX() + StaticConfig.SUBMENU_WIDTH,
                             position.getY() + StaticConfig.SUBMENU_LINE_HEIGHT * selectedIndex);
    }

    private void openChildren(SubmenuItem item) {
        children = new Submenu(game, item.getItems(), this);
    }

    private void closeChildren() {
        children = null;
    }

    public boolean isActive() {
        return children == null;
    }
}
