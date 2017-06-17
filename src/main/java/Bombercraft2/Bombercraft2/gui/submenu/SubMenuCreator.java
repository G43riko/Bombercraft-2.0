package Bombercraft2.Bombercraft2.gui.submenu;

import java.util.ArrayList;
import java.util.List;

import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.flora.Flora;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.player.placers.Placer.Types;

public class SubMenuCreator {
	public static List<SubmenuItem> generateSubmenu(GameAble game){
		ArrayList<SubmenuItem> items = new ArrayList<SubmenuItem>();
		items.add(new SubmenuItem(game.getLabelOf(Texts.OPTIONS), getOptions(game)));
		items.add(new SubmenuItem(game.getLabelOf(Texts.BLOCKS), getBlocks()));
		items.add(new SubmenuItem("Placer", getPlacers(game)));
		items.add(new SubmenuItem(game.getLabelOf(Texts.FLORA), getFloras(game)));
		items.add(new SubmenuItem(game.getLabelOf(Texts.EXIT_GAME), () -> {
			System.exit(0); 
			return false;
		}));
		
		return items;
	}
	private static List<SubmenuItem> getPlacers(GameAble game) {
		ArrayList<SubmenuItem> placers = new ArrayList<SubmenuItem>();
		
		//SubmenuRadioGroup group = new SubmenuRadioGroup((e) -> System.out.println("selected: " + e + " " + e.getLabel()));
		SubmenuRadioGroup group = new SubmenuRadioGroup();
		placers.add(new SubmenuItem(Types.SIMPLE.getLabel(), group, () -> {
			game.getToolsManager().setActualPlacer(Types.SIMPLE);
			return false;
		}));
		placers.add(new SubmenuItem(Types.AREA.getLabel(), group, () -> {
			game.getToolsManager().setActualPlacer(Types.AREA);
			return false;
		}));
		return placers;
	}
	private static List<SubmenuItem> getOptions(GameAble game){
		ArrayList<SubmenuItem> options = new ArrayList<SubmenuItem>();
		
		options.add(new SubmenuItem(game.getLabelOf(Texts.SHOW_LOGS), game.getVisibleOption(Render.LOGS), () -> {
			game.switchVisibleOption(Render.LOGS);
			return false;
		}));
		options.add(new SubmenuItem(game.getLabelOf(Texts.SHOW_WALS), game.getVisibleOption(Render.MAP_WALLS), () -> {
			game.switchVisibleOption(Render.MAP_WALLS);
			return false;
		}));
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
	
	private static List<SubmenuItem> getBlocks(){
		ArrayList<SubmenuItem> blocks = new ArrayList<SubmenuItem>();
		Block.Type[] types = Block.Type.values();
		for(int i=0 ; i<types.length ; i++){
			blocks.add(new SubmenuItem(types[i].toString(), types[i].getImage()));
		}
		return blocks;
	}
	
	private static List<SubmenuItem> getFloras(GameAble game){
		ArrayList<SubmenuItem> floras = new ArrayList<SubmenuItem>();
		floras.add(new SubmenuItem(game.getLabelOf(Texts.TREES), getTrees()));
		floras.add(new SubmenuItem(game.getLabelOf(Texts.BUSHES), getBushes()));
		floras.add(new SubmenuItem(game.getLabelOf(Texts.PLANTS), getPlants()));
		return floras;
	}
	
	private static List<SubmenuItem> getBushes(){
		ArrayList<SubmenuItem> bushes = new ArrayList<SubmenuItem>();
		Flora.Bushes[] types = Flora.Bushes.values();
		for(int i=0 ; i<types.length ; i++){
			bushes.add(new SubmenuItem(types[i].toString(), types[i].getImage()));
		}
		return bushes;
	}
	
	private static List<SubmenuItem> getTrees(){
		ArrayList<SubmenuItem> trees = new ArrayList<SubmenuItem>();
		Flora.Trees[] types = Flora.Trees.values();
		for(int i=0 ; i<types.length ; i++){
			trees.add(new SubmenuItem(types[i].toString(), types[i].getImage()));
		}
		return trees;
	}
	
	private static List<SubmenuItem> getPlants(){
		ArrayList<SubmenuItem> plants = new ArrayList<SubmenuItem>();
		Flora.Plants[] types = Flora.Plants.values();
		for(int i=0 ; i<types.length ; i++){
			plants.add(new SubmenuItem(types[i].toString(), types[i].getImage()));
		}
		return plants;
	}
}
