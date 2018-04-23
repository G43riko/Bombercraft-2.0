package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.BlockType;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GColision;
import utils.math.GVector2f;

import java.awt.*;
import java.util.HashMap;

public class NavBar extends Bar {
    private final HashMap<Integer, Iconable> items        = new HashMap<>();
    private       int                        selectedItem = 0;

    public NavBar(GameAble parent) {
        super(parent, Config.NAV_BAR_SIZE);

        items.put(0, Helper.Type.SHOVEL);
        items.put(1, Helper.Type.TOWER_LASER);
        items.put(2, Helper.Type.TOWER_MACHINE_GUN);
        items.put(3, Helper.Type.WEAPON_LASER);
        items.put(4, Helper.Type.WEAPON_BOW);
        items.put(5, Helper.Type.BOMB_NORMAL);
        items.put(6, Helper.Type.WEAPON_LIGHTNING);
        items.put(7, Helper.Type.WEAPON_STICK);
        items.put(8, Helper.Type.WEAPON_BOOMERANG);

        items.put(9, BlockType.IRON);
        items.put(10, BlockType.WOOD);
        items.put(11, BlockType.WATER);
        items.put(12, BlockType.GRASS);

        items.put(13, Helper.Type.OTHER_RESPAWNER);
        items.put(14, Helper.Type.OTHER_ADDUCTOR);

        calcPosition();
        init();
    }

    private void init() {
        setBackgroundColor(Config.NAV_BAR_BACKGROUND_COLOR);
        setBorderColor(Config.NAV_BAR_BORDER_COLOR);
        setBorderWidth(Config.NAV_BAR_BORDER_WIDTH);

        getParent().getToolsManager().setSelectedTool(getSelectedIcon());
    }

    private Iconable getSelectedIcon() {
        return items.get(selectedItem);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public void moveItem(int from, int to) {
        items.put(to, items.remove(from));
    }

    public int getSelectedIndex() {
        return selectedItem;
    }

    public void calcPosition() {
        totalSize = size.mul(new GVector2f(Config.NAV_BAR_NUMBER_OF_BLOCKS, 1));
        totalPos = new GVector2f((getParent().getCanvas().getWidth() - totalSize.getX()) / 2,
                                 getParent().getCanvas().getHeight() - Config.NAV_BAR_BOTTOM_OFFSET - totalSize.getY());
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.setColor(getBackgroundColor());
        g2.fillRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi());

        g2.setStroke(new BasicStroke(getBorderWidth()));
        g2.setColor(getBorderColor());

        for (int i = 0; i < Config.NAV_BAR_NUMBER_OF_BLOCKS; i++) {
            if (items.containsKey(i)) {
                g2.drawImage(items.get(i).getImage(),
                             totalPos.getXi() + size.getXi() * i,
                             totalPos.getYi(),
                             size.getXi(),
                             size.getYi(),
                             null);
            }
            g2.drawRect(totalPos.getXi() + size.getXi() * i, totalPos.getYi(), size.getXi(), size.getYi());
        }

        g2.setColor(Config.NAV_BAR_SELECT_BORDER_COLOR);
        g2.drawRect(totalPos.getXi() + size.getXi() * selectedItem, totalPos.getYi(), size.getXi(), size.getYi());
    }

    @Override
    public void doAct(GVector2f click) {
        if (GColision.pointRectCollision(totalPos, totalSize, Input.getMousePosition())) {
            selectedItem = Input.getMousePosition().sub(totalPos).div(size).getXi();
            getParent().getToolsManager().setSelectedTool(getSelectedIcon());
        }
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {
        return totalPos;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return size;
    }
}
