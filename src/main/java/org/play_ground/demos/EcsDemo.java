package org.play_ground.demos;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import org.bombercraft2.components.ControlComponent;
import org.bombercraft2.components.ControlSystem;
import org.bombercraft2.core.GameStateType;
import org.ecs.components.*;
import org.ecs.systems.BorderColliderSystem;
import org.ecs.systems.CanvasRenderSystem;
import org.ecs.systems.HealthSystem;
import org.ecs.systems.MovementSystem;
import org.engine.Input;
import org.glib2.math.GMath;
import org.glib2.math.vectors.SimpleVector3f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.utils.enums.Keys;

import java.awt.*;

public class EcsDemo extends SimpleAbstractGame<CorePlayGround> {
    private final Engine             engine       = new Engine();
    private final CanvasRenderSystem canvasSystem = new CanvasRenderSystem();
    private final HealthSystem       healthSystem = new HealthSystem();

    public EcsDemo(@NotNull CorePlayGround parent) {
        super(parent, GameStateType.EcsDemo);

        engine.addSystem(new BorderColliderSystem(new SimpleVector3f(parent.getCanvas().getWidth(),
                                                                     parent.getCanvas().getHeight(),
                                                                     0)));
        engine.addSystem(canvasSystem);
        engine.addSystem(healthSystem);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new ControlSystem());


        Entity entity = new Entity();
        entity.add(new TransformComponent(new SimpleVector3f(20, 20, 0),
                                          new SimpleVector3f(),
                                          new SimpleVector3f(50, 50, 0)))
              .add(new MovableComponent(10))
              .add(new AABBComponent().setSize(new SimpleVector3f(10, 10, 0)))
              .add(new HealthComponent(1000, 500))
              .add(new HealthBarCanvasComponent())
              .add(new ControlComponent(1))
              .add(new CanvasRectDrawableComponent(Color.RED));

        engine.addEntity(entity);


    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            Entity entity = new Entity();
            entity.add(new TransformComponent(new SimpleVector3f(Input.getMousePosition().getX(),
                                                                 Input.getMousePosition().getY(),
                                                                 0),
                                              new SimpleVector3f(),
                                              new SimpleVector3f(5, 5, 0)))
                  .add(new MovableComponent(10))
                  .add(new VelocityComponent(new SimpleVector3f(GMath.rand(-1, 1), GMath.rand(-1, 1), 0)))
                  .add(new AABBComponent().setSize(new SimpleVector3f(30, 30, 0)))
                  .add(new CanvasRectDrawableComponent(Color.BLUE));
            engine.addEntity(entity);
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        canvasSystem.setG2(g2);
        healthSystem.setG2(g2);
        engine.update(1);
    }
}
