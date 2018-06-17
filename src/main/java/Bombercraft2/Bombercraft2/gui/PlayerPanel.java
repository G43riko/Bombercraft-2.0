package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PlayerPanel implements InteractAble {
    private final static Color PLAYER_PANEL_FILL_COLOR  = new Color(11, 170, 185);
    private final static int   PLAYER_PANEL_WIDTH       = 150;
    private final static int   PLAYER_PANEL_HEIGHT      = 100;
    private final static int   PLAYER_PANEL_OFFSET      = 5;
    private final static int   PLAYER_PANEL_RADIUS      = 5;
    private final static int   PLAYER_PANEL_FONT_SIZE   = 15;
    private final static int   PLAYER_PANEL_FONT_OFFSET = 5;
    private final static Color PLAYER_PANEL_FONT_COLOR  = new Color(255, 255, 255);
    private final GameAble parent;
    private final boolean visible = true;

    public PlayerPanel(GameAble parent) {
        this.parent = parent;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.setColor(PLAYER_PANEL_FILL_COLOR);

        int posX = parent.getCanvas().getWidth() - PLAYER_PANEL_WIDTH - PLAYER_PANEL_OFFSET;
        int posY = parent.getCanvas().getHeight() - PLAYER_PANEL_HEIGHT - PLAYER_PANEL_OFFSET;

        g2.fillRoundRect(posX,
                         posY,
                         PLAYER_PANEL_WIDTH,
                         PLAYER_PANEL_HEIGHT,
                         PLAYER_PANEL_RADIUS,
                         PLAYER_PANEL_RADIUS);

        g2.setColor(PLAYER_PANEL_FONT_COLOR);
        g2.setFont(new Font(StaticConfig.DEFAULT_FONT, Font.BOLD, PLAYER_PANEL_FONT_SIZE));

        int verticalOffset = PLAYER_PANEL_FONT_OFFSET + PLAYER_PANEL_FONT_SIZE;
        g2.drawString(parent.getLabelOf(Texts.NAME) + ": " + parent.getMyPlayer().getName(),
                      posX + PLAYER_PANEL_FONT_OFFSET,
                      posY + verticalOffset);

        verticalOffset += PLAYER_PANEL_FONT_OFFSET + PLAYER_PANEL_FONT_SIZE;
        g2.drawString(parent.getLabelOf(Texts.HEALTH) + ": " +
                              parent.getMyPlayer().getActHealth() + "/" +
                              parent.getMyPlayer().getMaxHealth(),
                      posX + PLAYER_PANEL_FONT_OFFSET,
                      posY + verticalOffset);

        verticalOffset += PLAYER_PANEL_FONT_OFFSET + PLAYER_PANEL_FONT_SIZE;
        g2.drawString(parent.getLabelOf(Texts.DAMAGE) + ": " + parent.getMyPlayer().getDamage(),
                      posX + PLAYER_PANEL_FONT_OFFSET,
                      posY + verticalOffset);

        verticalOffset += PLAYER_PANEL_FONT_OFFSET + PLAYER_PANEL_FONT_SIZE;
        g2.drawString(parent.getLabelOf(Texts.SPEED) + ": " + parent.getMyPlayer().getSpeed(),
                      posX + PLAYER_PANEL_FONT_OFFSET,
                      posY + verticalOffset);


    }

    public boolean isVisible() {
        return visible;
    }


}
