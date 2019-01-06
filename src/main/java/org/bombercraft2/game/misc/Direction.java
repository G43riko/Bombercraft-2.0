package org.bombercraft2.game.misc;

import utils.math.BVector2f;

public enum Direction {
    UP(2, new BVector2f(0, -1)),
    DOWN(3, new BVector2f(0, 1)),
    LEFT(0, new BVector2f(-1, 0)),
    RIGHT(1, new BVector2f(1, 0));

    private final int       id;
    private final BVector2f direction;

    Direction(int id, BVector2f direction) {
        this.id = id;
        this.direction = direction;
    }

    public static Direction getRandomDirection() {return Direction.values()[(int) (Math.random() * 4)];}

    public static Direction getFromMove(BVector2f move, Direction defaultDirection) {
        if (move.getX() < 0 && move.getY() == 0) {
            return Direction.LEFT;
        }
        else if (move.getX() > 0 && move.getY() == 0) {
            return Direction.RIGHT;
        }
        else if (move.getX() == 0 && move.getY() < 0) {
            return Direction.UP;
        }
        else if (move.getX() == 0 && move.getY() > 0) {
            return Direction.DOWN;
        }
        return defaultDirection;
    }

    public int getID() {return id;}

    public BVector2f getDirection() {return direction;}
}
