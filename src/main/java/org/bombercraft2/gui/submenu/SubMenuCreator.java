package org.bombercraft2.gui.submenu;

import org.bombercraft2.core.Render;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.flora.Flora;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.player.placers.PlacerType;

import java.util.ArrayList;
import java.util.List;

public class SubMenuCreator {
    public static List<SubmenuItem> generateSubmenu(GameAble game) {
        ArrayList<SubmenuItem> items = new ArrayList<>();
        items.add(new SubmenuItem(game.getLabelOf(Texts.OPTIONS), getOptions(game)));
        items.add(new SubmenuItem(game.getLabelOf(Texts.SHOW), getShowOptions(game)));
        items.add(new SubmenuItem(game.getLabelOf(Texts.BLOCKS), getBlocks()));
        items.add(new SubmenuItem("Placer", getPlacers(game)));
        items.add(new SubmenuItem(game.getLabelOf(Texts.FLORA), getFloras(game)));
        items.add(new SubmenuItem(game.getLabelOf(Texts.EXIT_GAME), () -> {
            System.exit(0);
            return false;
        }));

        return items;
    }

    private static List<SubmenuItem> getShowOptions(GameAble game) {
        ArrayList<SubmenuItem> options = new ArrayList<>();

        options.add(new SubmenuItem(game.getLabelOf(Texts.LOGS), game.getVisibleOption(Render.LOGS), () -> {
            game.switchVisibleOption(Render.LOGS);
            return false;
        }));

        options.add(new SubmenuItem(game.getLabelOf(Texts.WALLS), game.getVisibleOption(Render.MAP_WALLS), () -> {
            game.switchVisibleOption(Render.MAP_WALLS);
            return false;
        }));

        options.add(new SubmenuItem(game.getLabelOf(Texts.LIGHTS), game.getVisibleOption(Render.LIGHTS), () -> {
            game.switchVisibleOption(Render.LIGHTS);
            return false;
        }));

        options.add(new SubmenuItem(game.getLabelOf(Texts.LIGHT_MAP),
                                    game.getVisibleOption(Render.ONLY_SHADOW_MAP),
                                    () -> {
                                        game.switchVisibleOption(Render.ONLY_SHADOW_MAP);
                                        return false;
                                    }));

        return options;
    }

    private static List<SubmenuItem> getPlacers(GameAble game) {
        ArrayList<SubmenuItem> placers = new ArrayList<>();

        //SubmenuRadioGroup group = new SubmenuRadioGroup((e) -> System.out.println("selected: " + e + " " + e.getLabel()));
        SubmenuRadioGroup group = new SubmenuRadioGroup();
        placers.add(new SubmenuItem(PlacerType.SIMPLE.getLabel(), group, () -> {
            game.getToolsManager().setActualPlacer(PlacerType.SIMPLE);
            return false;
        }));
        placers.add(new SubmenuItem(PlacerType.AREA.getLabel(), group, () -> {
            game.getToolsManager().setActualPlacer(PlacerType.AREA);
            return false;
        }));
        return placers;
    }

    private static List<SubmenuItem> getOptions(GameAble game) {
        ArrayList<SubmenuItem> options = new ArrayList<>();

        options.add(new SubmenuItem(game.getLabelOf(Texts.NEW_GAME), () -> {
            game.newGame();
            return true;
        }));

        options.add(new SubmenuItem(game.getLabelOf(Texts.RESTART_GAME), () -> {
            game.resetGame();
            return true;
        }));

        return options;
    }

    private static List<SubmenuItem> getBlocks() {
        ArrayList<SubmenuItem> blocks = new ArrayList<>();
        BlockType[] types = BlockType.values();
        for (BlockType type : types) {
            blocks.add(new SubmenuItem(type.toString(), type.getImage()));
        }
        return blocks;
    }

    private static List<SubmenuItem> getFloras(GameAble game) {
        ArrayList<SubmenuItem> floras = new ArrayList<>();
        floras.add(new SubmenuItem(game.getLabelOf(Texts.TREES), getTrees()));
        floras.add(new SubmenuItem(game.getLabelOf(Texts.BUSHES), getBushes()));
        floras.add(new SubmenuItem(game.getLabelOf(Texts.PLANTS), getPlants()));
        return floras;
    }

    private static List<SubmenuItem> getBushes() {
        ArrayList<SubmenuItem> bushes = new ArrayList<>();
        Flora.Bushes[] types = Flora.Bushes.values();
        for (Flora.Bushes type : types) {
            bushes.add(new SubmenuItem(type.toString(), type.getImage()));
        }
        return bushes;
    }

    private static List<SubmenuItem> getTrees() {
        ArrayList<SubmenuItem> trees = new ArrayList<>();
        Flora.Trees[] types = Flora.Trees.values();
        for (Flora.Trees type : types) {
            trees.add(new SubmenuItem(type.toString(), type.getImage()));
        }
        return trees;
    }

    private static List<SubmenuItem> getPlants() {
        ArrayList<SubmenuItem> plants = new ArrayList<>();
        Flora.Plants[] types = Flora.Plants.values();
        for (Flora.Plants type : types) {
            plants.add(new SubmenuItem(type.toString(), type.getImage()));
        }
        return plants;
    }
}
