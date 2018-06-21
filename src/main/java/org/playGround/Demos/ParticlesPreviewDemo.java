package org.playGround.Demos;

import org.bombercraft2.core.GameStateType;
import org.bombercraft2.gui2.GuiManager;
import org.bombercraft2.gui2.components.Button;
import org.bombercraft2.gui2.components.Panel;
import org.bombercraft2.gui2.components.VerticalScrollPanel;
import org.bombercraft2.gui2.core.GuiConnector;
import org.bombercraft2.gui2.utils.ColorBox;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.playGround.CorePlayGround;
import org.playGround.Misc.particles.ParticleEmitterData;
import org.playGround.Misc.particles.SimpleEmitter;
import org.playGround.SimpleAbstractGame;
import org.utils.enums.Keys;
import utils.math.GVector2f;

import java.awt.*;

public class ParticlesPreviewDemo extends SimpleAbstractGame<CorePlayGround> {
    private static VerticalScrollPanel panel      = new VerticalScrollPanel();
    private        GuiManager          guiManager = new GuiManager();
    private        SimpleEmitter       emitter    = new SimpleEmitter(new GVector2f(400, 400),
                                                                      this,
                                                                      ParticleEmitterData.Type.GREEN);

    public ParticlesPreviewDemo(CorePlayGround parent) {
        super(parent, GameStateType.ParticlesPreviewDemo);
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
    public void onResize() {
        parent.onResize();
        panel.setHeight(parent.getCanvas().getHeight() - 10);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
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
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseDown(Input.BUTTON_LEFT) && !GuiConnector.isMouseOn(panel)) {
            emitter.setPosition(Input.getMousePosition());
        }
    }
}
