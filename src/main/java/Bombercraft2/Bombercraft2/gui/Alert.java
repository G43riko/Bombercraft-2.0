package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.core.Interactable;

import java.awt.*;

public class Alert implements Interactable {
    private final long         createdAt      = System.currentTimeMillis();
    private       boolean      alive          = true;
    private       String       text           = null;
    private       AlertManager parent         = null;
    private       long         duration       = 0;
    private       int          verticalOffset = 0;


    public Alert(String text, AlertManager parent, long duration, int verticalOffset) {
        this.verticalOffset = verticalOffset;
        this.duration = duration;
        this.parent = parent;
        this.text = text;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(parent.getColor());
        g2.setFont(parent.getFont());

        int getPosX = parent.getScreenWidth() - g2.getFontMetrics().stringWidth(text) >> 1;
        g2.drawString(text, getPosX, verticalOffset);
    }

    public boolean isAlive() {
        return createdAt + duration > System.currentTimeMillis();
    }

    public void moveUp(int offset) {
        verticalOffset -= offset;
    }
}
