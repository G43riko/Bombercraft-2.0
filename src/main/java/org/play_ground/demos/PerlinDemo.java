package org.play_ground.demos;

import com.sun.javafx.util.Utils;
import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.utils.enums.Keys;
import org.utils.noises.SimplexNoise;

import java.awt.*;

public class PerlinDemo extends GameState {
    private final CorePlayGround parent;
    private final int            itemSize = 2;
    private       Item[][]       map;

    public PerlinDemo(CorePlayGround parent) {
        super(GameStateType.PerlinDemo);
        this.parent = parent;
        generateMap();
    }

    private void generateMap() {
        /*
        float[][] dataR = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(parent.getCanvas().getWidth() / itemSize,
                                                                                        parent.getCanvas().getHeight() / itemSize),
                                                         6,
                                                         0.7f,
                                                          true);

        float[][] dataG = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(parent.getCanvas().getWidth() / itemSize,
                                                                                         parent.getCanvas().getHeight() / itemSize),
                                                          6,
                                                          0.7f,
                                                          true);

        float[][] dataB = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(parent.getCanvas().getWidth() / itemSize,
                                                                                         parent.getCanvas().getHeight() / itemSize),
                                                          6,
                                                          0.7f,
                                                          true);
        */

        float[][] dataR = SimplexNoise.generateOctavedSimplexNoise(parent.getCanvas().getWidth() / itemSize,
                                                                   parent.getCanvas().getHeight() / itemSize,
                                                                   2,
                                                                   0.5f,
                                                                   0.01f);

        map = new Item[dataR.length][dataR[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Item();
                dataR[i][j] = (dataR[i][j] + 1) / 2;
                map[i][j].color = new Color(Utils.clamp(0, 255, (int) (dataR[i][j] * 255)),
                                            Utils.clamp(0, 255, (int) (dataR[i][j] * 255)),
                                            Utils.clamp(0, 255, (int) (dataR[i][j] * 255)), 255);
            }
        }

    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                g2.setColor(map[i][j].color);
                g2.fillRect(i * itemSize, j * itemSize, itemSize, itemSize);
            }
        }
    }

    private class Item {
        Color color;
    }
}
