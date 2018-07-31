package org.bombercraft2.components;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import org.ecs.Mappers;
import org.ecs.components.MovableComponent;
import org.ecs.components.TransformComponent;
import org.ecs.components.deprecated.PositionComponent;

public class ControlSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ControlComponent> controlMapper = ComponentMapper.getFor(ControlComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MovableComponent.class,
                                                    ControlComponent.class).one(PositionComponent.class,
                                                                                TransformComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        entities.forEach(entity -> {
            PositionComponent position = Mappers.position.get(entity);
            TransformComponent transform = Mappers.transform.get(entity);
            ControlComponent control = controlMapper.get(entity);
            MovableComponent movement = Mappers.movement.get(entity);

            if (transform != null) {
                control.update(deltaTime, movement.getSpeed(), transform.position);
            }
            else if (position != null) {
                control.update(deltaTime, movement.getSpeed(), position.position);
            }
        });
    }
}
