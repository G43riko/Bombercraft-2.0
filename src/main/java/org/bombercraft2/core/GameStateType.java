
package org.bombercraft2.core;

import org.jetbrains.annotations.Contract;

public enum GameStateType {
    CreateGameMenu("CreateGameMenu"),
    LoadingScreen("LoadingScreen"),
    JoinMenu("JoinMenu"),
    ProfileMenu("ProfileMenu"),
    BasicDemo("BasicDemo"),
    PlayerDemo("PlayerDemo"),
    EndGameMenu("EndGameMenu"),
    OptionsMenu("OptionsMenu"),
    PauseMenu("PauseMenu"),
    MainMenu("MainMenu"),
    Game("Game"),
    MapDemo("ProfileMenu"),
    CollisionDemo("CollisionDemo"),
    ParticlesDemo("ParticlesDemo"),
    ShootingDemo("ShootingDemo"),
    PerlinDemo("PerlinDemo"),
    ChunkedMapDemo("ChunkedMapDemo"),
    ParticlesPreviewDemo("articlesPreviewDemo"),
    BombDemo("BombDemo"),
    WorkerDemo("WorkerDemo"),
    MissileDemo("MissileDemo"),
    BomberDemo("BomberDemo"),
    GenericGame("GenericGame"),
    ChunkDemo("ChunkDemo");

    private final String name;

    GameStateType(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
