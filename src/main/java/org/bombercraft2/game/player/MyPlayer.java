package org.bombercraft2.game.player;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.components.health_bar.HealthBar;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Bomb;
import org.bombercraft2.game.entity.BombCreator;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.misc.Direction;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.utils.enums.Keys;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class MyPlayer extends Player {
    private final boolean            showSelector = true;
    private final PlayerSelector     selector     = new PlayerSelector(this);
    private final PlayerPointer      pointer      = new PlayerPointer(this);
    private final HealthBar          healthBar;
    private final int                cadenceBonus = 0;
    private final int                speedBonus   = 0;
    private final Map<Keys, Boolean> keys         = new EnumMap<>(Keys.class);
    private       GVector2f          offset       = null;
    private       GVector2f          move         = new GVector2f();
    private       GVector2f          totalMove    = new GVector2f();
    private       int                damageBonus  = 0;
    private       Bomb               lastPutBomb  = null;

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
        keys.put(Keys.W, false);
        keys.put(Keys.A, false);
        keys.put(Keys.S, false);
        keys.put(Keys.D, false);
    }

    private void resetOffset() {
        offset = new GVector2f(getParent().getCanvas().getWidth(),
                               getParent().getCanvas().getHeight()).getDiv(-2);
    }

    @Override
    public void input() {
        move = new GVector2f();

        if (!keys.get(Keys.W) && Input.isKeyDown(Keys.W)) {
            setDirection(Direction.UP);
        }
        keys.put(Keys.W, Input.isKeyDown(Keys.W));


        if (!keys.get(Keys.S) && Input.isKeyDown(Keys.S)) {
            setDirection(Direction.DOWN);
        }
        keys.put(Keys.S, Input.isKeyDown(Keys.S));


        if (!keys.get(Keys.A) && Input.isKeyDown(Keys.A)) {
            setDirection(Direction.LEFT);
        }
        keys.put(Keys.A, Input.isKeyDown(Keys.A));


        if (!keys.get(Keys.D) && Input.isKeyDown(Keys.D)) {
            setDirection(Direction.RIGHT);
        }
        keys.put(Keys.D, Input.isKeyDown(Keys.D));


        if (keys.get(Keys.W)) {
            move.addToY(-1);
        }
        if (keys.get(Keys.S)) {
            move.addToY(1);
        }
        if (keys.get(Keys.A)) {
            move.addToX(-1);
        }
        if (keys.get(Keys.D)) {
            move.addToX(1);
        }


        setMoving(!move.isNull());

        if (Input.getKeyDown(Keys.LCONTROL)) {
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
            move = move.getMul(StaticConfig.WATER_TILE_SPEED_COEFFICIENT);
        }
        totalMove = totalMove.getAdd(move);

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

        GVector2f t = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX(),
                                                    StaticConfig.BLOCK_SIZE.getY() - topOffset).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f b = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX(),
                                                    StaticConfig.BLOCK_SIZE.getY() + bottomOffset).getDiv(
                2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f r = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX() - rightOffset,
                                                    StaticConfig.BLOCK_SIZE.getY()).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();
        GVector2f l = position.getAdd(new GVector2f(StaticConfig.BLOCK_SIZE.getX() + leftOffset,
                                                    StaticConfig.BLOCK_SIZE.getY()).getDiv(2))
                .getDiv(StaticConfig.BLOCK_SIZE)
                              .toInt();

        boolean checkBomb = !getParent().getSceneManager().isBombOn(t.getXi(), t.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(b.getXi(), b.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(r.getXi(), r.getYi(), lastPutBomb) &&
                !getParent().getSceneManager().isBombOn(l.getXi(), l.getYi(), lastPutBomb);

        if (lastPutBomb != null && lastPutBomb.getPosition()
                                              .dist(getPosition()) > StaticConfig.BLOCK_SIZE.getLength()) {
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

        GVector2f pos = getPosition().getMul(getParent().getZoom())
                .getAdd(StaticConfig.BLOCK_SIZE.getMul(getParent().getZoom() / 2));

        offset.setX(pos.getX() - getParent().getCanvas().getWidth() / 2);
        offset.setY(pos.getY() - getParent().getCanvas().getHeight() / 2);

        GVector2f numbers = getParent().getLevel().getMap().getNumberOfBlocks();

        //skontroluje posun
        if (offset.getX() < 0) {
            offset.setX(0);
        }

        if (offset.getX() > (numbers.getX() * StaticConfig.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas()
                                                                                                                     .getWidth()) {
            offset.setX((numbers.getX() * StaticConfig.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) - getParent().getCanvas()
                                                                                                                 .getWidth());
        }

        if (offset.getY() < 0) {
            offset.setY(0);
        }

        if (offset.getY() > (numbers.getY() * StaticConfig.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas()
                                                                                                                      .getHeight()) {
            offset.setY((numbers.getY() * StaticConfig.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) - getParent().getCanvas()
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

        if (position.getX() * getParent().getZoom() + StaticConfig.DEFAULT_BLOCK_WIDTH * getParent().getZoom() > nums.getX() * getParent()
                .getZoom()) {
            position.setX((nums.getX() * getParent().getZoom() - StaticConfig.DEFAULT_BLOCK_WIDTH * getParent().getZoom()) / getParent()
                    .getZoom());
        }

        if (position.getY() * getParent().getZoom() + StaticConfig.DEFAULT_BLOCK_HEIGHT * getParent().getZoom() > nums.getY() * getParent()
                .getZoom()) {
            position.setY((nums.getY() * getParent().getZoom() - StaticConfig.DEFAULT_BLOCK_HEIGHT * getParent().getZoom()) / getParent()
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
        return getTargetLocation().getSub(getCenter());
    }
}