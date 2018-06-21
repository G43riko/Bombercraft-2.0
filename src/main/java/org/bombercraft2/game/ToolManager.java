package org.bombercraft2.game;

import org.bombercraft2.game.entity.BombCreator;
import org.bombercraft2.game.entity.Helper;
import org.bombercraft2.game.entity.towers.TowerCreator;
import org.bombercraft2.game.entity.weapons.WeaponLaser;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.player.Shovel;
import org.bombercraft2.game.player.ToolAble;
import org.bombercraft2.game.player.placers.AreaPlacer;
import org.bombercraft2.game.player.placers.Placer;
import org.bombercraft2.game.player.placers.PlacerType;
import org.bombercraft2.game.player.placers.SimplePlacer;

import java.awt.*;
import java.util.HashMap;

public class ToolManager {
    private final HashMap<Iconable, ToolAble> tools = new HashMap<>();
    private final GameAble                    parent;
    private       Placer                      actualPlacer;
    private       ToolAble                    selectedTool;

    public ToolManager(GameAble parent) {
        this.parent = parent;
        init();
    }

    private void init() {
        setActualPlacer(PlacerType.SIMPLE);
        tools.put(Helper.Type.SHOVEL, new Shovel(parent));
        tools.put(Helper.Type.WEAPON_LASER, new WeaponLaser(parent, parent.getWeapon("laser")));
        tools.put(Helper.Type.BOMB_NORMAL, new BombCreator(parent, Helper.Type.BOMB_NORMAL));
        tools.put(Helper.Type.TOWER_MACHINE_GUN, new TowerCreator(parent, Helper.Type.TOWER_MACHINE_GUN));
    }

    public void render(Graphics2D g2) {
        actualPlacer.render(g2);
    }


    public ToolAble getTool(Iconable icon) {return tools.get(icon);}

    public ToolAble getSelectedTool() {return selectedTool;}

    public void setSelectedTool(Iconable icon) {selectedTool = getItemByIconable(icon);}

    //SETTERS

    private ToolAble getItemByIconable(Iconable selectedIcon) {
        if (selectedIcon.getClass().isAssignableFrom(BlockType.class)) {
            actualPlacer.setBlockType((BlockType) selectedIcon);
            return actualPlacer;
        }
        return tools.get(selectedIcon);
    }

    public void setActualPlacer(PlacerType type) {
        Placer newPlacer = null;
        switch (type) {
            case SIMPLE:
                newPlacer = new SimplePlacer(parent);
                break;
            case AREA:
                newPlacer = new AreaPlacer(parent);
                break;
        }
        if (actualPlacer != null) {
            newPlacer.setBlockType(actualPlacer.getType());
        }
        actualPlacer = newPlacer;
        if (selectedTool instanceof Placer) {
            selectedTool = actualPlacer;
        }
    }
}
