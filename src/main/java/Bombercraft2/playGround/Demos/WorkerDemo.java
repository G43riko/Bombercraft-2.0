package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.Profile;
import Bombercraft2.Bombercraft2.components.tasks.*;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.SceneManager;
import Bombercraft2.Bombercraft2.game.ToolManager;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.BulletManager;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.level.Level;
import Bombercraft2.Bombercraft2.game.player.MyPlayer;
import Bombercraft2.Bombercraft2.game.player.Player;
import Bombercraft2.Bombercraft2.multiplayer.Connector;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.map.SimpleTypedBlock;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.SimpleMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkerDemo extends GameState implements GameAble {
    private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(40, 40);
    private final CorePlayGround parent;
    private final ViewManager    viewManager;
    private final BotManager botManager = new BotManager();
    private final TaskManager taskManager = new TaskManager(botManager, this);
    private final SimpleMap      map;

    public WorkerDemo(CorePlayGround parent) {
        super(Type.WorkerDemo);
        viewManager = new ViewManager(NUMBERS_OF_BLOCKS.mul(Config.BLOCK_SIZE),
                                      parent.getCanvas().getWidth(),
                                      parent.getCanvas().getHeight(),
                                      3);
        this.parent = parent;
        map = new SimpleMap(this, NUMBERS_OF_BLOCKS);
    }

    @Override
    public void doAct(GVector2f click) {
        SimpleTypedBlock block = map.getBlockOnAbsolutePos(click);
        if (block != null) {
            taskManager.addTask(new Task(this, 1, 100, new TaskDestroyBlock(block)));
        }
    }

    @Override
    public void update(float delta) {
        botManager.update(delta);
        taskManager.update(delta);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
        botManager.render(g2);
        taskManager.render(g2);
    }

    @Override
    public boolean isVisible(@NotNull Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + parent.getCanvas().getWidth() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + parent.getCanvas().getHeight() < b.getPosition().getY() * getZoom());
    }


    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            doAct(Input.getMousePosition());
        }
        if (Input.getMouseDown(Input.BUTTON_RIGHT)) {
            botManager.addBot(new BotWorker(Input.getMousePosition()
                                                 .add(getOffset())
                                                 .div(getZoom())
                                                 .sub(Config.BLOCK_SIZE_HALF), this));
        }
        viewManager.input();
    }

    @Override
    public void onResize() {
        viewManager.setCanvasSize(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
    }

    @Contract(pure = true)
    @Override
    public float getZoom() {
        return viewManager.getZoom();
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getCanvasSize() {
        return viewManager.getCanvasSize();
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getOffset() {
        return viewManager.getOffset();
    }

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
    public void addBullet(BulletManager.@NotNull Types bulletType, @NotNull GVector2f angle, @NotNull GVector2f position
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
    public void addEmitter(@NotNull GVector2f position, Emitter.@NotNull Types type
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
}