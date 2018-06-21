package org.bombercraft2.gui;

import org.bombercraft2.StaticConfig;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.SimpleGameAble;

import java.awt.*;
import java.util.List;


public class GameLogs implements InteractAbleG2 {
    private final SimpleGameAble parent;
    private final int            offset = 10;

    public GameLogs(SimpleGameAble parent) {
        this.parent = parent;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        List<String> data = parent.getLogInfo();

        g2.setFont(new Font(StaticConfig.DEFAULT_FONT, Font.BOLD | Font.ITALIC, StaticConfig.LOG_TEXT_SIZE));

        int maxWidth = 0;
        for (String aData : data) {
            maxWidth = Math.max(g2.getFontMetrics().stringWidth(aData), maxWidth);
        }

        int height = StaticConfig.LOG_TEXT_SIZE * data.size() + 5;
        int width = maxWidth + offset;
        int positionX = parent.getCanvasSize().getXi() - width;

        g2.setColor(StaticConfig.LOG_BG_COLOR);
        g2.fillRoundRect(positionX,
                         0,
                         width,
                         height,
                         StaticConfig.DEFAULT_ROUND,
                         StaticConfig.DEFAULT_ROUND);

        g2.setStroke(new BasicStroke(StaticConfig.LOG_BORDER_WIDTH));
        g2.setColor(StaticConfig.LOG_BORDER_COLOR);
        g2.drawRoundRect(positionX,
                         0,
                         width,
                         height,
                         StaticConfig.DEFAULT_ROUND,
                         StaticConfig.DEFAULT_ROUND);


        g2.setColor(StaticConfig.LOG_TEXT_COLOR);
        for (int i = 0; i < data.size(); i++) {
            g2.drawString(data.get(i), positionX + 6, StaticConfig.LOG_TEXT_SIZE * i + StaticConfig.LOG_TEXT_SIZE);
        }

    }
}
