package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.game.Direction;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotFactory.Types;
import utils.math.GVector2f;

public abstract class Enemy extends Bot {

    Enemy(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent, type, direction);
    }

}
