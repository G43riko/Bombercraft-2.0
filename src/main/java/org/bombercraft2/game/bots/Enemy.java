package org.bombercraft2.game.bots;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.bots.BotFactory.Types;
import org.bombercraft2.game.misc.Direction;
import utils.math.GVector2f;

public abstract class Enemy extends Bot {

    Enemy(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent, type, direction);
    }

}
