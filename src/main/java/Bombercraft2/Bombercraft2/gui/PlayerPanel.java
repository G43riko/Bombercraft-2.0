package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;

import java.awt.*;

public class PlayerPanel implements Interactable {
    public final static Color PLAYER_PANEL_FILL_COLOR  = new Color(11, 170, 185);
    public final static int   PLAYER_PANEL_WIDTH       = 150;
    public final static int   PLAYER_PANEL_HEIGHT      = 100;
    public final static int   PLAYER_PANEL_OFFSET      = 5;
    public final static int   PLAYER_PANEL_RADIUS      = 5;
    public final static int   PLAYER_PANEL_FONT_SIZE   = 15;
    public final static int   PLAYER_PANEL_FONT_OFFSET = 5;
    public final static Color PLAYER_PANEL_FONT_COLOR  = new Color(255, 255, 255);
    private final GameAble parent;
    private final boolean visible = true;

    public PlayerPanel(GameAble parent) {
        this.parent = parent;
    }

    @Override
    public void render(Graphics2D g2) {
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
        g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD, PLAYER_PANEL_FONT_SIZE));

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
