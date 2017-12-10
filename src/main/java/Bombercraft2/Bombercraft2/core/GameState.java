package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.gui.ClickAble;

public abstract class GameState implements InteractAble, ClickAble {
    public enum Type {
        CreateGameMenu("CreateGameMenu"),
        LoadingScreen("LoadingScreen"),
        JoinMenu("JoinMenu"),
        ProfileMenu("ProfileMenu"),
        EndGameMenu("EndGameMenu"),
        OptionsMenu("OptionsMenu"),
        PauseMenu("PauseMenu"),
        MainMenu("MainMenu"),
        Game("Game");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() { return name; }
    }

    private final Type type;

    protected GameState(Type type) {
        this.type = type;
    }

    public Type getType() {return type;}

    public void onResize() {}
}
