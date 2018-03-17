package Bombercraft2.Bombercraft2.game;

import utils.math.GVector2f;

public enum Direction {
    UP(2, new GVector2f(0, -1)),
    DOWN(3, new GVector2f(0, 1)),
    LEFT(0, new GVector2f(-1, 0)),
    RIGHT(1, new GVector2f(1, 0));

    private final int       id;
    private final GVector2f direction;

    Direction(int id, GVector2f direction) {
        this.id = id;
        this.direction = direction;
    }

    public int getID() {return id;}

    public GVector2f getDirection() {return direction;}

    public static Direction getRandomDirection() {return Direction.values()[(int) (Math.random() * 4)];}
}
