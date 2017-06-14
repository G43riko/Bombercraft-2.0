package Bombercraft2.Bombercraft2.game;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import utils.math.GVector2f;

public class LoadingScreen extends GameState{
	private Canvas canvas;
	
	public LoadingScreen(Canvas canvas) {
		super(GameState.Type.LoadingScreen);
		this.canvas = canvas;
	}
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Config.LOADING_DEFAULT_BACKGROUND_COLOR);
		g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g2.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC , Config.LOADING_DEFAULT_FONT));
		g2.setColor(Config.LOADING_DEFAULT_TEXT_COLOR);
		g2.drawString("Loading",
					  (canvas.getWidth()  - g2.getFontMetrics().stringWidth("Loading")) / 2, 
					  (canvas.getHeight() - Config.LOADING_DEFAULT_FONT) / 2);
	}
	@Override
	public void doAct(GVector2f click) {}
}
