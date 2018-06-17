package Bombercraft2.Bombercraft2;

import Bombercraft2.Bombercraft2.components.pathfinging.Grid2d;
import Bombercraft2.Bombercraft2.components.tasks.BotManager;
import Bombercraft2.Bombercraft2.components.tasks.BotWorker;
import Bombercraft2.Bombercraft2.components.tasks.Task;
import Bombercraft2.Bombercraft2.components.tasks.TaskType;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.SceneManager;
import Bombercraft2.Bombercraft2.game.ToolManager;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.EmitterTypes;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;



public class App {

    public static void main(String[] args) {
        Bombercraft game = new Bombercraft();
        // PlayGround game = new PlayGround();
        game.run();

        // testPathFinging();
        // testTaskManager();
    }

    private static void testPathFinging() {
        double[][] map = { { 0, -1, 0 }, { 0, 3, 0 }, { 0, 0, 0 } };
        Grid2d map2d = new Grid2d(map, false);
        System.out.println(map2d.findPath(0, 0, 2, 0));
    }

    private static void testTaskManager() {
        TaskType taskType = new TaskType() {
            @NotNull
            @Override
            public GVector2f getPosition() {
                return null;
            }

            @Override
            public void finish() {
                System.out.println("Hotovoooo");
            }
        };
        GameAble parent = new GameAble() {
            @Override
            public Canvas getCanvas() {
                return null;
            }

            @Override
            public @NotNull JSONObject getWeapon(@NotNull String weaponLaser) {
                return null;
            }

            @Override
            public boolean getVisibleOption(@NotNull String key) {
                return false;
            }

            @Override
            public @NotNull Player getPlayerByName(@NotNull String name) {
                return null;
            }

            @Override
            public @NotNull String getLabelOf(@NotNull String key) {
                return null;
            }

            @Override
            public @NotNull GVector2f getPlayerDirection() {
                return null;
            }

            @Override
            public @NotNull SceneManager getSceneManager() {
                return null;
            }

            @Override
            public @NotNull ToolManager getToolsManager() {
                return null;
            }

            @Override
            public @NotNull GVector2f getPlayerTarget() {
                return null;
            }

            @Override
            public @NotNull Connector getConnector() {
                return null;
            }

            @Override
            public @NotNull String getBasicInfo() {
                return null;
            }

            @Override
            public @NotNull ArrayList<String> getLogInfo() {
                return null;
            }

            @Override
            public @NotNull MyPlayer getMyPlayer() {
                return null;
            }

            @Override
            public @NotNull String getGameInfo() {
                return null;
            }

            @Override
            public @NotNull Profile getProfile() {
                return null;
            }

            @Override
            public @NotNull Level getLevel() {
                return null;
            }

            @Override
            public void addBullet(BulletManager.@NotNull Types bulletType,
                                  @NotNull GVector2f angle,
                                  @NotNull GVector2f position
                                 ) {

            }

            @Override
            public void addExplosion(@NotNull GVector2f position,
                                     @NotNull GVector2f size,
                                     @NotNull Color color,
                                     int number,
                                     boolean explosion,
                                     boolean shockWave
                                    ) {

            }

            @Override
            public void addHelper(@NotNull GVector2f pos, Helper.@NotNull Type type, long createdTime
                                 ) {

            }

            @Override
            public void addEmitter(@NotNull GVector2f position, EmitterTypes type
                                  ) {

            }

            @Override
            public void addEnemy(@NotNull GVector2f position, @NotNull String type) {

            }

            @Override
            public void addPlayer(@NotNull String name, @NotNull String image) {

            }

            @Override
            public void explodeBombAt(@NotNull GVector2f globalPosToLocalPos) {

            }

            @Override
            public void switchVisibleOption(@NotNull String key) {

            }

            @Override
            public boolean hasWall(float i, float j) {
                return false;
            }

            @Override
            public void removePlayer(@NotNull String name) {

            }

            @Override
            public void changeZoom(float f) {

            }

            @Override
            public void calcPosition() {

            }

            @Override
            public void resetGame() {

            }

            @Override
            public void newGame() {

            }

            @Override
            public @NotNull JSONObject toJSON() {
                return null;
            }

            @Override
            public void endGame() {

            }

            @Override
            public @NotNull HashMap<String, String> getStats() {
                return null;
            }

            @Override
            public @NotNull GVector2f getPosition() {
                return null;
            }

            @Override
            public @NotNull GVector2f getSize() {
                return null;
            }
        };
        BotWorker bot = new BotWorker(new GVector2f(), parent);
        BotManager botManager = new BotManager();
        botManager.addBot(new BotWorker(new GVector2f(), parent));
        botManager.addBot(bot);
        Task task = new Task(parent, 1, 100, taskType);

        task.startWork((BotWorker)botManager.getFreeBot());
        task.startWork(bot);
        while(!task.isFinished()) {
            botManager.update(1);
            task.update(1);
            Utils.sleep(1000);
        }
    }

    private static void testMultiPlayer() {
        Thread t1 = new Thread(() -> {
            Bombercraft server = new Bombercraft();
            server.initDefaultProfile();
            server.startNewGame();
            server.run();
        });
        Thread t2 = new Thread(() -> {
            Bombercraft client = new Bombercraft();
            client.initDefaultProfile();
            client.showJoinMenu();
            client.run();
        });
        t1.start();
        Utils.sleep(1000);
        t2.start();
    }
}
