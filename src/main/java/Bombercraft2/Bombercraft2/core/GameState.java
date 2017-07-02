package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.gui.Clicable;

public abstract class GameState implements Interactable, Clicable{
	public enum Type{
		CreateGameMenu("CreateGameMenu"), 
		LoadingScreen("LoadingScreen"),
		JoinMenu("JoinMenu"), 
		ProfileMenu("ProfileMenu"), 
		EndGameMenu("EndGameMenu"), 
		OptionsMenu("OptionsMenu"), 
		PauseMenu("PauseMenu"), 
		MainMenu("MainMenu"), 
		Game("Game");
		
		private String name;
		private Type(String name){
			this.name = name;
		}
	};
	private Type type;
	public GameState(Type type){
		this.type = type;
	}
	public Type getType(){return type;}
	public void onResize() {}
	
}
