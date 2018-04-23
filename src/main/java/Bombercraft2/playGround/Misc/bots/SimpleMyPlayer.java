package Bombercraft2.playGround.Misc.bots;


import Bombercraft2.Bombercraft2.game.misc.Direction;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleMyPlayer extends SimplePlayer {
    private       GVector2f offset       = null;
    private       GVector2f move         = new GVector2f();
    private       GVector2f totalMove    = new GVector2f();

    private final HashMap<Integer, Boolean> keys = new HashMap<>();

    private static GVector2f getMoveFromKeys(HashMap<Integer, Boolean> keys) {
        GVector2f move = new GVector2f();
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
        return move;
    }

    public SimpleMyPlayer(SimpleGameAble parent,
                          GVector2f position,
                          String name,
                          int speed,
                          int health,
                          String image,
                          int range
                         ) {
        super(parent, position, name, speed, health, image, range);
        resetOffset();
        keys.put(Input.KEY_W, false);
        keys.put(Input.KEY_A, false);
        keys.put(Input.KEY_S, false);
        keys.put(Input.KEY_D, false);
    }

    private void resetOffset() {
        offset = new GVector2f(parent.getCanvasSize().getXi(),
                               parent.getCanvasSize().getYi()).div(-2);
    }

    @Override
    public void input() {
        processKeys();

        move = getMoveFromKeys(keys);

        setMoving(!move.isNull());

        setDirection(Direction.getFromMove(move, getDirection()));
    }

    private void processKeys() {
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

        totalMove = totalMove.add(move);

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
        totalMove = new GVector2f();
    }


    public GVector2f getOffset() {
        return offset;
    }

    public GVector2f getTargetLocation() {
        return getCenter();
    }

    public GVector2f getTargetDirection() {
        return getTargetLocation().sub(getCenter());
    }
}
