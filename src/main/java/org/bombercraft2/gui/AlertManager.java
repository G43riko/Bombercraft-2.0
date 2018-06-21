package org.bombercraft2.gui;

import org.bombercraft2.StaticConfig;
import org.engine.CoreEngine;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlertManager implements InteractAbleG2 {
    private final List<Alert> alerts   = new ArrayList<>();
    private final Color       color    = Color.BLACK;
    private final int         fontSize = 33;
    private final Font        font     = new Font(StaticConfig.DEFAULT_FONT, Font.BOLD | Font.ITALIC, fontSize);
    private final CoreEngine  parent;

    public AlertManager(CoreEngine parent) {
        this.parent = parent;
    }

    public void addAlert(String text) {
        alerts.add(new Alert(text,
                             this,
                             3000,
                             fontSize * alerts.size() + fontSize + StaticConfig.ALERT_VERTICAL_OFFSET));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        alerts.forEach(a -> a.render(g2));
    }

    @Override
    public void update(float delta) {
        List<Alert> forRemove = alerts.stream().filter(a -> !a.isAlive()).collect(Collectors.toList());
        alerts.removeAll(forRemove);
        alerts.forEach(a -> a.moveUp(forRemove.size() * fontSize));
    }

    public Color getColor() {return color;}

    public Font getFont() {return font;}

    public int getScreenWidth() {
        return parent.getCanvas().getWidth();
    }
}
