package org.play_ground.demos;

import org.bombercraft2.core.GameStateType;
import org.bombercraft2.gui2.GuiManagerImpl;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.gui.components.Button;
import org.gui.components.Panel;
import org.gui.components.VerticalScrollPanel;
import org.gui.core.GuiConnector;
import org.gui.utils.ColorBox;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.particles.ParticleEmitterData;
import org.play_ground.misc.particles.SimpleEmitter;
import org.utils.enums.Keys;

import java.awt.*;

public class ParticlesPreviewDemo extends SimpleAbstractGame<CorePlayGround> {
    private static VerticalScrollPanel panel      = new VerticalScrollPanel();
    private        GuiManagerImpl      guiManager = new GuiManagerImpl();
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
        button.getStateHolder().getDefault().setBackgroundColor(new Color(0, 255, 255));
        button.getStateHolder().setHover(new ColorBox(new Color(255,
                                                       255,
                                                       255), Color.blue, 5));
        panel.addComponent(button);

        panel.getLayout().resize();
        //manager.getAdd(panel);
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
