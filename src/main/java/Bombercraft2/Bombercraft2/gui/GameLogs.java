package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameLogs implements InteractAble {
    private final SimpleGameAble parent;
    private final int offset = 10;

    public GameLogs(SimpleGameAble parent) {
        this.parent = parent;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        List<String> data = parent.getLogInfo();

        g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC, Config.LOG_TEXT_SIZE));

        int maxWidth = 0;
        for (String aData : data) {
            maxWidth = Math.max(g2.getFontMetrics().stringWidth(aData), maxWidth);
        }

        int height = Config.LOG_TEXT_SIZE * data.size() + 5;
        int width = maxWidth + offset;
        int positionX = parent.getCanvasSize().getXi() - width;

        g2.setColor(Config.LOG_BG_COLOR);
        g2.fillRoundRect(positionX,
                         0,
                         width,
                         height,
                         Config.DEFAULT_ROUND,
                         Config.DEFAULT_ROUND);

        g2.setStroke(new BasicStroke(Config.LOG_BORDER_WIDTH));
        g2.setColor(Config.LOG_BORDER_COLOR);
        g2.drawRoundRect(positionX,
                         0,
                         width,
                         height,
                         Config.DEFAULT_ROUND,
                         Config.DEFAULT_ROUND);


        g2.setColor(Config.LOG_TEXT_COLOR);
        for (int i = 0; i < data.size(); i++) {
            g2.drawString(data.get(i), positionX + 6, Config.LOG_TEXT_SIZE * i + Config.LOG_TEXT_SIZE);
        }

    }
}
