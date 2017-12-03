package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import utils.math.GVector2f;

import java.awt.*;

public class LoadingScreen extends GameState {
    private final Canvas canvas;
    private final String text;

    public LoadingScreen(Canvas canvas, String text) {
        super(GameState.Type.LoadingScreen);
        this.text = text;
        this.canvas = canvas;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Config.LOADING_DEFAULT_BACKGROUND_COLOR);
        g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, Config.LOADING_DEFAULT_FONT));
        g2.setColor(Config.LOADING_DEFAULT_TEXT_COLOR);
        g2.drawString(text,
                      (canvas.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2,
                      (canvas.getHeight() - Config.LOADING_DEFAULT_FONT) / 2);
    }

    @Override
    public void doAct(GVector2f click) {}
}
