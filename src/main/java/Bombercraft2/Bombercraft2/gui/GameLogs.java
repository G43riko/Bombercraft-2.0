package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.game.GameAble;

import java.awt.*;
import java.util.ArrayList;


public class GameLogs implements Interactable {
    private final GameAble parent;
    final int offset = 10;

    public GameLogs(GameAble parent) {
        this.parent = parent;
    }

    @Override
    public void render(Graphics2D g2) {
        ArrayList<String> data = parent.getLogInfo();

        g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, Config.LOG_TEXT_SIZE));

        int maxWidth = 0;
        for (String aData : data) {
            maxWidth = Math.max(g2.getFontMetrics().stringWidth(aData), maxWidth);
        }

        int height = Config.LOG_TEXT_SIZE * data.size() + 5;
        int width = maxWidth + offset;
        int positionX = parent.getCanvas().getWidth() - width;

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
