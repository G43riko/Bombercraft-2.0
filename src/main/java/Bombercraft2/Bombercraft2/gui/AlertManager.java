package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.engine.CoreEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlertManager implements Interactable {
    private List<Alert> alerts   = new ArrayList<>();
    private Color       color    = Color.BLACK;
    private int         fontSize = 33;
    private Font        font     = new Font("Garamond", Font.BOLD | Font.ITALIC, fontSize);
    private CoreEngine  parent   = null;

    public AlertManager(CoreEngine parent) {
        this.parent = parent;
    }

    public void addAlert(String text) {
        alerts.add(new Alert(text, this, 3000, fontSize * alerts.size() + fontSize + Config.ALERT_VERTICAL_OFFSET));
    }

    @Override
    public void render(Graphics2D g2) {
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
