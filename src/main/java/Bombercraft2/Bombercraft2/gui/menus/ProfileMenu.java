package Bombercraft2.Bombercraft2.gui.menus;

import java.awt.Graphics2D;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.components.Button;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public class ProfileMenu extends Menu{
	private ArrayList<String> availableProfiles = loadProfiles();
	private MenuAble parent;
	
	public ProfileMenu(MenuAble parent) {
		super(parent, GameState.Type.ProfileMenu);
		this.parent = parent;
		position.setY(100);
		init();
	}

	@Override
	public void doAct(GVector2f click) {
		availableProfiles.stream().forEach(a -> {
			if(components.get(a).isClickIn(click)){
				selectProfile(a);
			}
		});
		if(components.get(Texts.CREATE_PROFIL).isClickIn(click)){
			createProfile();
		}
		if(components.get(Texts.EXIT_GAME).isClickIn(click)){
			parent.exitGame();
		}
	}
	
	@Override
	public void onResize() {
		for(GuiComponent component : components.values()){
			component.calcPosAndSize();
		}
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.clearRect(0, 0, size.getXi(), size.getYi());
		super.render(g2);
	}

	@Override
	public void calcPosition() {
		
	}

	protected void init() {
		availableProfiles.stream().forEach(a -> addComponent(a, new Button(this, a)));
		
		setItem(Texts.CREATE_PROFIL);
		components.get(Texts.CREATE_PROFIL).setDisable(true);
		
		setItem(Texts.EXIT_GAME);
	}

	private void selectProfile(String profile){
		parent.setProfile(profile);
		parent.showMainMenu();
	}
	
	private void createProfile(){
		System.out.println("create profile: ");
	}
	
	private ArrayList<String> loadProfiles(){
		ArrayList<String> result = new ArrayList<String>(); 
		URL url = ResourceLoader.getURL(Config.FOLDER_PROFILE);
		
		if (url != null){
			try {
				File dir = new File(url.toURI());
				
			    for (File nextFile : dir.listFiles()){
			    	result.add(nextFile.getName().replace(Config.EXTENSION_PROFILE, ""));
			    }
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
