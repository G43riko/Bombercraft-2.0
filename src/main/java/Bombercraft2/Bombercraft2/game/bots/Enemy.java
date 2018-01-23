package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotManager.Types;
import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import utils.math.GVector2f;

public abstract class Enemy extends Bot {

    Enemy(GVector2f position, GameAble parent, Types type, Direction direction) {
        super(position, parent, type, direction);
    }

}
