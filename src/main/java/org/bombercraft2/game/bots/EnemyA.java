package org.bombercraft2.game.bots;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.bots.BotFactory.Types;
import org.bombercraft2.game.level.Block;
import org.bombercraft2.game.level.Map;
import org.bombercraft2.game.misc.Direction;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EnemyA extends Enemy {
    private final float borderSize  = 3;
    private final Color color       = Color.RED;
    private final Color borderColor = Color.WHITE;

    public EnemyA(GVector2f position, GameAble parent) {
        this(position,
             parent,
             getRandPossibleDir(parent.getLevel().getMap(), position.getDiv(StaticConfig.BLOCK_SIZE).toInt()));
    }

    private EnemyA(GVector2f position, GameAble parent, Direction direction) {
        super(position, parent, Types.A, direction);
    }

    private static Direction getRandPossibleDir(Map map, GVector2f sur) {
        Direction[] ret = map.getPossibleWays(sur);
        if (ret.length == 0) {
            return null;
        }

        return ret[(int) (Math.random() * ret.length)];
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        int tempRound = (int) (StaticConfig.ENEMY_DEFAULT_ROUND * getParent().getZoom());

        GVector2f pos = position.getAdd(StaticConfig.ENEMY_DEFAULT_OFFSET)
                .getMul(getParent().getZoom())
                .getSub(getParent().getOffset());

        GVector2f tempSize = getSize().getMul(getParent().getZoom());


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
        if (position.getMod(StaticConfig.BLOCK_SIZE).isNull()) {
            GVector2f nextPos = position.getAdd(direction.getDirection().getMul(StaticConfig.BLOCK_SIZE));
            Block block = getParent().getLevel().getMap().getBlockOnPosition(nextPos);
            if (block == null || !block.isWalkable()) {
                direction = getRandPossibleDir(getParent().getLevel().getMap(), getSur());

            }
        }
        if (direction == null) {
            return;
        }
        position = position.getAdd(direction.getDirection().getMul(getSpeed()));
    }

}
