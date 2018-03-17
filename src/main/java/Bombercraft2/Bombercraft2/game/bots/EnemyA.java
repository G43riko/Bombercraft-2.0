package Bombercraft2.Bombercraft2.game.bots;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.Direction;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.bots.BotFactory.Types;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class EnemyA extends Enemy {
    private final float borderSize  = 3;
    private final Color color       = Color.RED;
    private final Color borderColor = Color.WHITE;

    public EnemyA(GVector2f position, GameAble parent) {
        this(position, parent, getRandPossibleDir(parent.getLevel().getMap(), position.div(Config.BLOCK_SIZE).toInt()));
    }

    private EnemyA(GVector2f position, GameAble parent, Direction direction) {
        super(position, parent, Types.A, direction);
    }


    @Override
    public void render(@NotNull Graphics2D g2) {
        int tempRound = (int) (Config.ENEMY_DEFAULT_ROUND * getParent().getZoom());

        GVector2f pos = position.add(Config.ENEMY_DEFAULT_OFFSET)
                                .mul(getParent().getZoom())
                                .sub(getParent().getOffset());

        GVector2f tempSize = getSize().mul(getParent().getZoom());


        g2.setStroke(new BasicStroke(getParent().getZoom() * borderSize));

        g2.setColor(color);
        g2.fillRoundRect(pos.getXi(), pos.getYi(), tempSize.getXi(), tempSize.getYi(), tempRound, tempRound);

        g2.setColor(borderColor);
        g2.drawRoundRect(pos.getXi(), pos.getYi(), tempSize.getXi(), tempSize.getYi(), tempRound, tempRound);

        g2.setStroke(new BasicStroke(getParent().getZoom() * 1));
        g2.setColor(Color.red);
        g2.drawRect(pos.getXi(), pos.getYi() - 6, tempSize.getXi(), 4);
    }

    @Override
    public void update(float delta) {
        if (direction == null) {
            return;
        }
        if (position.mod(Config.BLOCK_SIZE).isNull()) {
            GVector2f nextPos = position.add(direction.getDirection().mul(Config.BLOCK_SIZE));
            Block block = getParent().getLevel().getMap().getBlockOnPosition(nextPos);
            if (block == null || !block.isWalkable()) {
                direction = getRandPossibleDir(getParent().getLevel().getMap(), getSur());

            }
        }
        if (direction == null) {
            return;
        }
        position = position.add(direction.getDirection().mul(getSpeed()));
    }

    private static Direction getRandPossibleDir(Map map, GVector2f sur) {
        Direction[] ret = map.getPossibleWays(sur);
        if (ret.length == 0) {
            return null;
        }

        return ret[(int) (Math.random() * ret.length)];
    }

}
