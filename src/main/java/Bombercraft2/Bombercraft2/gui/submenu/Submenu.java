package Bombercraft2.Bombercraft2.gui.submenu;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.gui.submenu.SubmenuItem.Types;
import Bombercraft2.engine.Input;
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

        position = parent == null ? Config.SUBMENU_DEFAULT_POSITION : parent.getChildrenPosition();
    }

    public boolean isVisible() {
        return visible;
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
        if (Input.getKeyDown(Input.KEY_ARROW_DOWN)) {
            selectedIndex++;
            if (selectedIndex == items.size()) {
                selectedIndex = 0;
            }
        }
        if (Input.getKeyDown(Input.KEY_ARROW_UP)) {
            selectedIndex--;
            if (selectedIndex < 0) {
                selectedIndex = items.size() - 1;
            }
        }

        if (Input.getKeyDown(Input.KEY_ARROW_RIGHT)) {
            SubmenuItem item = items.get(selectedIndex);
            if (!item.isFinal()) {
                openChildren(item);
            }
        }
        if (Input.getKeyDown(Input.KEY_ARROW_LEFT)) {
            if (parent != null) {
                parent.closeChildren();
            }
            else {
                setVisible(false);
            }
        }
        if (Input.getKeyDown(Input.KEY_ENTER)) {
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
        int submenuHeight = Config.SUBMENU_LINE_HEIGHT * items.size();
        g2.setColor(Config.SUBMENU_FILL_COLOR);
        g2.fillRoundRect(position.getXi(),
                         position.getYi(),
                         Config.SUBMENU_WIDTH,
                         submenuHeight,
                         Config.SUBMENU_ROUND,
                         Config.SUBMENU_ROUND);


        g2.setColor(Config.SUBMENU_FONT_COLOR);
        g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC, Config.DEFAULT_FONT_SIZE));
        for (int i = 0; i < items.size(); i++) {

            if (selectedIndex == i) {
                g2.setColor(Config.SUBMENU_ACTIVE_ITEM_FILL_COLOR);
                g2.fillRoundRect(position.getXi(),
                                 position.getYi() + i * Config.SUBMENU_LINE_HEIGHT,
                                 Config.SUBMENU_WIDTH,
                                 Config.SUBMENU_LINE_HEIGHT,
                                 Config.SUBMENU_LINE_ROUND,
                                 Config.SUBMENU_LINE_ROUND);
            }

            SubmenuItem a = items.get(i);
            if (a.isFinal()) {
                int beginTextX = position.getXi();
                int beginTextY = position.getYi() + i * Config.SUBMENU_LINE_HEIGHT;
                int imageOffset = 0;

                if (a.getType() == Types.ICONABLE) {
                    beginTextX += Config.SUBMENU_ICON_OFFSET;

                    a.renderIcon(g2, beginTextX, beginTextY);
//					
                    imageOffset += Config.SUBMENU_LINE_HEIGHT;
                    beginTextX += Config.SUBMENU_ICON_OFFSET;
                }
                else if (a.getType() == Types.CHECKBOX) {
                    beginTextX += Config.SUBMENU_ICON_OFFSET;

                    a.renderCheckbox(g2, beginTextX, beginTextY);
                    imageOffset += Config.SUBMENU_LINE_HEIGHT;
                    beginTextX += Config.SUBMENU_ICON_OFFSET;
                }
                else if (a.getType() == Types.RADIO && a.isSelected()) {
                    beginTextX += Config.SUBMENU_ICON_OFFSET;

                    a.renderRadio(g2, beginTextX, beginTextY);
                    imageOffset += Config.SUBMENU_LINE_HEIGHT;
                    beginTextX += Config.SUBMENU_ICON_OFFSET;
                }

                g2.setColor(Config.SUBMENU_FONT_COLOR);
                g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC, Config.DEFAULT_FONT_SIZE));
                g2.drawString(a.getLabel(),
                              beginTextX + Config.SUBMENU_FONT_HORIZONTAL_OFFSET + imageOffset,
                              beginTextY + Config.SUBMENU_FONT_VERTICAL_OFFSET + Config.SUBMENU_FONT_SIZE);


            }
            else {
                int yOffset = i * Config.SUBMENU_LINE_HEIGHT + Config.SUBMENU_FONT_VERTICAL_OFFSET + Config.SUBMENU_FONT_SIZE;
                g2.setColor(Config.SUBMENU_FONT_COLOR);
                g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC, Config.SUBMENU_FONT_SIZE));
                g2.drawString(a.getLabel(),
                              position.getXi() + Config.SUBMENU_FONT_HORIZONTAL_OFFSET,
                              position.getYi() + yOffset);
            }
        }

        g2.setStroke(new BasicStroke(Config.SUBMENU_BORDER_WIDTH));
        g2.setColor(Config.SUBMENU_BORDER_COLOR);
        g2.drawRoundRect(position.getXi(),
                         position.getYi(),
                         Config.SUBMENU_WIDTH,
                         submenuHeight,
                         Config.SUBMENU_ROUND,
                         Config.SUBMENU_ROUND);


        if (children != null) {
            children.render(g2);
        }
    }

    private GVector2f getChildrenPosition() {
        return new GVector2f(position.getX() + Config.SUBMENU_WIDTH,
                             position.getY() + Config.SUBMENU_LINE_HEIGHT * selectedIndex);
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

    public void setVisible(boolean value) {
        Submenu first = getFirst();
        first.visible = value;
        if (!first.visible) {
            first.closeChildren();
            first.selectedIndex = 0;
        }
    }
}
