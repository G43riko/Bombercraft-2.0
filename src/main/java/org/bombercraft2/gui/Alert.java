package org.bombercraft2.gui;

import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Alert implements InteractAbleG2 {
    private final long         createdAt = System.currentTimeMillis();
    private final String       text;
    private final AlertManager parent;
    private final long         duration;
    private       boolean      alive     = true;
    private       int          verticalOffset;


    public Alert(String text, AlertManager parent, long duration, int verticalOffset) {
        this.verticalOffset = verticalOffset;
        this.duration = duration;
        this.parent = parent;
        this.text = text;
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
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
