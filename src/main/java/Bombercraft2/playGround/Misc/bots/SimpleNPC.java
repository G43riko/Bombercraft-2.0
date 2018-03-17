package Bombercraft2.playGround.Misc.bots;

import Bombercraft2.Bombercraft2.game.Direction;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class SimpleNPC extends SimplePlayer{
    public SimpleNPC(SimpleGameAble parent,
                          GVector2f position,
                          String name,
                          int speed,
                          int health,
                          String image,
                          int range
                         ) {
        super(parent, position, name, speed, health, image, range);
        setDirection(Direction.getRandomDirection());
    }

    @Override
    public void update(float delta) {
        GVector2f move = getDirection().getDirection();

        position.addToX(move.getX() * getSpeed() * delta);
        if (!isOnWalkableBlock()) {
            position.addToX(-move.getX() * getSpeed() * delta);
            setDirection(Direction.getRandomDirection());
        }

        position.addToY(move.getY() * getSpeed() * delta);
        if (!isOnWalkableBlock()) {
            position.addToY(-move.getY() * getSpeed() * delta);
            setDirection(Direction.getRandomDirection());
        }


        if (checkBorders()) {
            setDirection(Direction.getRandomDirection());
        }
    }

    @Override
    public void input() {
        setMoving(true);
    }
}
