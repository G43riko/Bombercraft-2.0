package org.bombercraft2.game.bots;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.bots.BotFactory.Types;
import org.bombercraft2.game.misc.Direction;
import utils.math.BVector2f;

public abstract class Enemy extends Bot {

    Enemy(BVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent, type, direction);
    }

}
