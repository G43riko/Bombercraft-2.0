package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.map.BasicChunk;
import Bombercraft2.playGround.Misc.BasicEntity;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkDemo extends GameState implements SimpleGameAble {
    private final int items = 10;
    private final GVector2f size = new GVector2f(40, 40);
    private final Map<String, BasicChunk> chunks = new HashMap<>();
    private final BasicEntity entity = new BasicEntity(this, Input.getMousePosition(), new GVector2f(80, 80));
    @NotNull
    private final CorePlayGround parent;

    public ChunkDemo(@NotNull CorePlayGround parent) {
        super(Type.ChunkDemo);
        this.parent = parent;

        final int colorSkip = 255 / (items * items);
        int actColor = 0;
        for (int i = 0; i < items; i++) {
            for (int j = 0; j < items; j++) {
                chunks.put(i + "_" + j,
                           new BasicChunk(new GVector2f(i, j).mul(size),
                                          size,
                                          new Color(actColor, actColor, actColor)));
                actColor += colorSkip;
            }
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        chunks.forEach((key, value) -> value.render(g2));
        entity.render(g2);
    }

    @Override
    public List<BasicChunk> getActChunk(BasicEntity entity) {
        GVector2f targetStartPos = Input.getMousePosition().div(size);
        GVector2f targetEndPos = Input.getMousePosition().add(entity.size).div(size);
        List<BasicChunk> result = new ArrayList<>();
        for (int i = targetStartPos.getXi(); i <= targetEndPos.getXi(); i++) {
            for (int j = targetStartPos.getYi(); j <= targetEndPos.getYi(); j++) {
                BasicChunk actChunk = chunks.get(i + "_" + j);
                if (actChunk != null) {
                    result.add(actChunk);
                }
            }
        }
        return result;
    }

    @Override
    public void update(float delta) {
        entity.position.set(Input.getMousePosition());
        entity.update(delta);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
    }

}
