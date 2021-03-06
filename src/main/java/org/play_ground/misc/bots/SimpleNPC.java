package org.play_ground.misc.bots;

import org.bombercraft2.game.misc.Direction;
import org.glib2.math.vectors.GVector2f;
import org.play_ground.misc.SimpleGameAble;

public class SimpleNPC extends SimplePlayer {
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
        GVector2f move = getDirection().getDirection().getMul(getSpeed() * delta);


        position.addToX(move.getX());
        if (!isOnWalkableBlock()) {
            position.addToX(-move.getX());
            setDirection(Direction.getRandomDirection());
        }

        position.addToY(move.getY());
        if (!isOnWalkableBlock()) {
            position.addToY(-move.getY());
            setDirection(Direction.getRandomDirection());
        }


        if (isOnEdge()) {
            setDirection(Direction.getRandomDirection());
        }
    }

    @Override
    public void input() {
        setMoving(true);
    }
}
