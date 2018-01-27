package Bombercraft2.playGround.Demos;

import java.awt.Color;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.Bombercraft2.gui2.components.Button;
import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.components.VerticalScrollPanel;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.particles.SimpleEmitter;
import utils.math.GVector2f;

public class ParticlesPreviewDemo extends GameState implements SimpleGameAble{
    private GuiManager guiManager = new GuiManager();
    private static VerticalScrollPanel panel = new VerticalScrollPanel();
    private SimpleEmitter emitter = new SimpleEmitter(new GVector2f(400, 400), this);
    private final CorePlayGround parent;
    public ParticlesPreviewDemo(CorePlayGround parent) {
        super(Type.ParticlesPreviewDemo);
        this.parent = parent;
        guiManager.add(testScrollPanel());
    }
	private Panel testScrollPanel() {
        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(1);

        panel.setColorBox(colorBox);
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
		panel.setHeight(parent.getCanvas().getHeight() - 10);

        Button button = new Button("Tlacitko " + 0);
        button.setHeight(40);
        button.setWidth(100);
        button.setClickHandler(() -> System.out.println("gabooo"));
        button.getColorBox().setBackgroundColor(new Color(0, 255, 255));
        button.setHoverColorBox(new ColorBox(new Color(255,
                                                       255,
                                                       255), Color.blue, 5));
        panel.addComponent(button);

        panel.getLayout().resize();
        //manager.add(panel);
        return panel;
    }
	@Override
	public void doAct(GVector2f click) { }
	
	@Override
	public void onResize() {
		panel.setHeight(parent.getCanvas().getHeight() - 10);
	}
	
	@Override
	public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        emitter.render(g2);
		guiManager.render(g2);
		
	}
	
	@Override
	public void update(float delta) {
		guiManager.update();
		emitter.update(delta);
	}

	@Override
	public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseDown(Input.BUTTON_LEFT) && !GuiConnector.isMouseOn(panel)) {
        	emitter.setPosition(Input.getMousePosition());
        }
	}
	
    
    private void emitParticles(GVector2f position) {
    	System.out.println("paráda");
    }

	@Override
	public GVector2f getCanvasSize() {
		return new GVector2f(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
	}
}
