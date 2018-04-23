package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.level.BlockType;
import Bombercraft2.Bombercraft2.game.misc.Direction;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.components.healthBar.HealthBar;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.BombCreator;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.HashMap;

public class MyPlayer extends Player {
    private       GVector2f      offset       = null;
    private       GVector2f      move         = new GVector2f();
    private       GVector2f      totalMove    = new GVector2f();
    private final boolean        showSelector = true;
    private final PlayerSelector selector     = new PlayerSelector(this);
    private final PlayerPointer  pointer      = new PlayerPointer(this);
    private final HealthBar healthBar;
    private final int  cadenceBonus = 0;
    private final int  speedBonus   = 0;
    private       int  damageBonus  = 0;
    private       Bomb lastPutBomb  = null;

    private final HashMap<Integer, Boolean> keys = new HashMap<>();

    public MyPlayer(GameAble parent,
                    GVector2f position,
                    String name,
                    int speed,
                    int health,
                    String image,
                    int range
                   ) {
        super(parent, position, name, speed, health, image, range);
        healthBar = new HealthBar(this, parent);
        resetOffset();
        checkOffset();
        keys.put(Input.KEY_W, false);
        keys.put(Input.KEY_A, false);
        keys.put(Input.KEY_S, false);
        keys.put(Input.KEY_D, false);
    }

    private void resetOffset() {
        offset = new GVector2f(getParent().getCanvas().getWidth(),
                               getParent().getCanvas().getHeight()).div(-2);
    }

    @Override
    public void input() {
        move = new GVector2f();

        if (!keys.get(Input.KEY_W) && Input.isKeyDown(Input.KEY_W)) {
            setDirection(Direction.UP);
        }
        keys.put(Input.KEY_W, Input.isKeyDown(Input.KEY_W));


        if (!keys.get(Input.KEY_S) && Input.isKeyDown(Input.KEY_S)) {
            setDirection(Direction.DOWN);
        }
        keys.put(Input.KEY_S, Input.isKeyDown(Input.KEY_S));


        if (!keys.get(Input.KEY_A) && Input.isKeyDown(Input.KEY_A)) {
            setDirection(Direction.LEFT);
        }
        keys.put(Input.KEY_A, Input.isKeyDown(Input.KEY_A));


        if (!keys.get(Input.KEY_D) && Input.isKeyDown(Input.KEY_D)) {
            setDirection(Direction.RIGHT);
        }
        keys.put(Input.KEY_D, Input.isKeyDown(Input.KEY_D));


        if (keys.get(Input.KEY_W)) {
            move.addToY(-1);
        }
        if (keys.get(Input.KEY_S)) {
            move.addToY(1);
        }
        if (keys.get(Input.KEY_A)) {
            move.addToX(-1);
        }
        if (keys.get(Input.KEY_D)) {
            move.addToX(1);
        }


        setMoving(!move.isNull());

        if (Input.getKeyDown(Input.KEY_LCONTROL)) {
            doAction();
        }



        setDirection(Direction.getFromMove(move, getDirection()));

        pointer.input();

    }

    private void doAction() {
        getParent().getToolsManager().getSelectedTool().useOnGlobalPos(getTargetLocation());

        if (getParent().getToolsManager().getSelectedTool() instanceof BombCreator) {
            lastPutBomb = getParent().getSceneManager().getLastBomb();
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        super.render(g2);
        pointer.render(g2);
        healthBar.render(g2);
    }

    private void onDead() {
        getParent().endGame();
    }

    public void update(float delta) {
        if (position == null) {
            return;
        }

        if (!isAlive()) {
            onDead();
        }

        if (getParent().getLevel().getMap().getBlockOnPosition(getCenter()).getType() == BlockType.WATER) {
            move = move.mul(Config.WATER_TILE_SPEED_COEFFICIENT);
        }
        totalMove = totalMove.add(move);

        position.addToX(move.getX() * getSpeed() * delta);
        if (!isOnWalkableBlock()) {
            position.addToX(-move.getX() * getSpeed() * delta);
        }

        position.addToY(move.getY() * getSpeed() * delta);
        if (!isOnWalkableBlock()) {
            position.addToY(-move.getY() * getSpeed() * delta);
        }

        checkBorders();
        checkOffset();

        pointer.update(delta);
        if (!move.isNull()) {
            getParent().getConnector().setPlayerChange(this);
        }
    }

    private boolean isOnWalkableBlock() {
//		//pri 40x40
//		final float topOffset 		= 20;
//		final float bottomOffset 	= 35;
//		final float rightOffset 	= 11;
//		final float leftOffset 		= 9;

        //pri 60x60
        final float topOffset = 35;
        final float bottomOffset = 65;
        final float rightOffset = 21;
        final float leftOffset = 19;

        GVector2f t = position.add(new GVector2f(Config.BLOCK_SIZE.getX(), Config.BLOCK_SIZE.getY() - topOffset).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f b = position.add(new GVector2f(Config.BLOCK_SIZE.getX(), Config.BLOCK_SIZE.getY() + bottomOffset).div(
                2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f r = position.add(new GVector2f(Config.BLOCK_SIZE.getX() - rightOffset,
                                                 Config.BLOCK_SIZE.getY()).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();
        GVector2f l = position.add(new GVector2f(Config.BLOCK_SIZE.getX() + leftOffset,
                                                 Config.BLOCK_SIZE.getY()).div(2))
                              .div(Config.BLOCK_SIZE)
                              .toInt();

        boolean checkBomb = !getParent().getSceneManager().isBombOn(t.getXi(), t.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(b.getXi(), b.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(r.getXi(), r.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(l.getXi(), l.getYi(), lastPutBomb);

        if (lastPutBomb != null && lastPutBomb.getPosition().dist(getPosition()) > Config.BLOCK_SIZE.getLength()) {
            lastPutBomb = null;
        }

        try {
            return checkBomb &&
                    getParent().getLevel().getMap().getBlock(t.getXi(), t.getYi()).isWalkable() &&
                    getParent().getLevel().getMap().getBlock(b.getXi(), b.getYi()).isWalkable() &&
                    getParent().getLevel().getMap().getBlock(r.getXi(), r.getYi()).isWalkable() &&
                    getParent().getLevel().getMap().getBlock(l.getXi(), l.getYi()).isWalkable();
        }
        catch (NullPointerException e) {
            return false;
        }
    }


    private void checkOffset() {

        GVector2f pos = getPosition().mul(getParent().getZoom()).add(Config.BLOCK_SIZE.mul(getParent().getZoom() / 2));

        offset.setX(pos.getX() - getParent().getCanvas().getWidth() / 2);
        offset.setY(pos.getY() - getParent().getCanvas().getHeight() / 2);

        GVector2f numbers = getParent().getLevel().getMap().getNumberOfBlocks();

        //skontroluje posun
        if (offset.getX() < 0) {
            offset.setX(0);
        }

        if (offset.getX() > (numbers.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas()
                                                                                                               .getWidth()) {
            offset.setX((numbers.getX() * Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas()
                                                                                                           .getWidth());
        }

        if (offset.getY() < 0) {
            offset.setY(0);
        }

        if (offset.getY() > (numbers.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas()
                                                                                                                .getHeight()) {
            offset.setY((numbers.getY() * Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas()
                                                                                                            .getHeight());
        }
    }

    private void checkBorders() {
        if (position.getX() * getParent().getZoom() < 0) {
            position.setX(0);
        }

        if (position.getY() * getParent().getZoom() < 0) {
            position.setY(0);
        }

        while (getParent() == null || getParent().getLevel() == null || getParent().getLevel().getMap() == null) {
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GVector2f nums = getParent().getLevel().getMap().getSize();

        if (position.getX() * getParent().getZoom() + Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom() > nums.getX() * getParent()
                .getZoom()) {
            position.setX((nums.getX() * getParent().getZoom() - Config.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) / getParent()
                    .getZoom());
        }

        if (position.getY() * getParent().getZoom() + Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom() > nums.getY() * getParent()
                .getZoom()) {
            position.setY((nums.getY() * getParent().getZoom() - Config.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) / getParent()
                    .getZoom());
        }
    }

    public void clearTotalMove() {
        totalMove = new GVector2f();
    }

    public void renderSelector(Graphics2D g2) {
        selector.render(g2);
    }

    public boolean showSelector() {
        return showSelector;
    }

    public GVector2f getOffset() {
        return offset;
    }

    public int getCadenceBonus() {
        return cadenceBonus;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public GVector2f getTargetLocation() {
        return getParent().getToolsManager().getSelectedTool() instanceof BombCreator ? getCenter() : pointer.getEndPos(
                getCenter());
    }

    public GVector2f getTargetDirection() {
        return getTargetLocation().sub(getCenter());
    }
}