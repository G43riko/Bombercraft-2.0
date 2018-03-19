package Bombercraft2.playGround;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.GameStatesManager;
import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.Demos.*;
import Bombercraft2.playGround.Demos.BomberDemo.BomberDemo;
import Bombercraft2.playGround.Demos.ChunkedMapDemo;
import Bombercraft2.playGround.Demos.MapDemo;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CorePlayGround extends CoreEngine {
    @NotNull
    private final GameStatesManager states      = new GameStatesManager();
    @NotNull
    private final GuiManager        guiManager  = new GuiManager();
    @NotNull
    private final MainMenu          mainMenu    = new MainMenu();
    private       boolean           runningGame = false;

    CorePlayGround(int fps, int ups, boolean renderTime) {
        super(fps, ups, renderTime);
        guiManager.createMainPanel(getCanvas());
        guiManager.getMainPanel().addComponent(mainMenu);
        mainMenu.addButton("Ray casting", () -> setScene(new BasicDemo(this)));
        mainMenu.addButton("Closest point", () -> setScene(new ClosestDemo(this)));
        mainMenu.addButton("Collisions", () -> setScene(new CollisionDemo(this)));
        mainMenu.addButton("Bomber", () -> setScene(new BomberDemo(this)));
        mainMenu.addButton("Player", () -> setScene(new PlayerDemo(this)));
        mainMenu.addButton("Particles", () -> setScene(new ParticlesDemo(this)));
        mainMenu.addButton("Shooting", () -> setScene(new ShootingDemo(this)));
        mainMenu.addButton("Perlin", () -> setScene(new PerlinDemo(this)));
        mainMenu.addButton("Map", () -> setScene(new MapDemo(this)));
        mainMenu.addButton("Missile", () -> setScene(new MissileDemo(this)));
        mainMenu.addButton("Worker", () -> setScene(new WorkerDemo(this)));
        mainMenu.addButton("Chunked map", () -> setScene(new ChunkedMapDemo(this)));
        mainMenu.addButton("Particles preview", () -> setScene(new ParticlesPreviewDemo(this)));
        mainMenu.addButton("Bomb", () -> setScene(new BombDemo(this)));

        mainMenu.addButton("Exit", this::exitGame);
    }

    public void stopDemo() {
        states.pop();
        runningGame = false;
    }

    private void setScene(@NotNull GameState scene) {
        runningGame = true;
        guiManager.setDefaultCursor();
        states.push(scene);
    }

    @Override
    protected void render(@NotNull Graphics2D g2) {
        if (!runningGame) {
            guiManager.render(g2);
        }
        states.render(g2);
    }

    @Override
    protected void input() {
        if (!runningGame && Input.getKeyDown(Input.KEY_ESCAPE)) {
            exitGame();
        }
        states.input();
    }

    private void exitGame() {
        cleanUp();
        onExit();
        System.exit(1);
    }

    @Override
    protected void update(float delta) {
        if (!runningGame) {
            guiManager.update();
            getCanvas().setCursor(guiManager.getCursor());
        }
        states.update(delta);
    }

    @Override
    public void onResize() {
        if (guiManager != null) {
            guiManager.onResize();
        }
        if (states != null) {
            states.onResize();
        }
    }

    @Override
    public void onExit() {

    }

}
