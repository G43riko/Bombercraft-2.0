package org.play_ground.misc.bots;


import org.bombercraft2.game.misc.Direction;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.SimpleGameAble;
import org.utils.enums.Keys;
import utils.math.BVector2f;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SimpleMyPlayer extends SimplePlayer {
    private final Map<Keys, Boolean> keys      = new EnumMap<>(Keys.class);
    private       BVector2f          offset    = null;
    private       BVector2f          move      = new BVector2f();
    private       BVector2f          totalMove = new BVector2f();

    public SimpleMyPlayer(SimpleGameAble parent,
                          BVector2f position,
                          String name,
                          int speed,
                          int health,
                          String image,
                          int range
                         ) {
        super(parent, position, name, speed, health, image, range);
        resetOffset();
        keys.put(Keys.W, false);
        keys.put(Keys.A, false);
        keys.put(Keys.S, false);
        keys.put(Keys.D, false);
    }

    private static BVector2f getMoveFromKeys(Map<Keys, Boolean> keys) {
        BVector2f move = new BVector2f();
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
        return move;
    }

    private void resetOffset() {
        offset = new BVector2f(parent.getCanvasSize().getXi(),
                               parent.getCanvasSize().getYi()).getDiv(-2);
    }

    @Override
    public void input() {
        processKeys();

        move = getMoveFromKeys(keys);

        setMoving(!move.isNull());

        setDirection(Direction.getFromMove(move, getDirection()));
    }

    private void processKeys() {
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
    }

    @NotNull
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("MyPlayer pos: " + position.toDecimal(1));
        return result;
    }

    public void update(float delta) {
        if (position == null) {
            return;
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


        isOnEdge();
    }


    public void clearTotalMove() {
        totalMove = new BVector2f();
    }


    public BVector2f getOffset() {
        return offset;
    }

    public BVector2f getTargetLocation() {
        return getCenter();
    }

    public BVector2f getTargetDirection() {
        return getTargetLocation().getSub(getCenter());
    }
}
