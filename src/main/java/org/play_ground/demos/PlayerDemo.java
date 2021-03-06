package org.play_ground.demos;

import org.bombercraft2.core.GameStateType;
import org.bombercraft2.gui.GameLogs;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.PlayerManager;
import org.play_ground.misc.ViewManager;
import org.play_ground.misc.bots.SimpleMyPlayer;
import org.play_ground.misc.bots.SimpleNPC;
import org.play_ground.misc.map.MapManager;
import org.play_ground.misc.map.SimpleChunkedMap;
import org.utils.enums.Keys;

import java.awt.*;

public class PlayerDemo extends SimpleAbstractGame<CorePlayGround> {
    private GameLogs gameLogs = new GameLogs(this);

    public PlayerDemo(CorePlayGround parent) {
        super(parent, GameStateType.PlayerDemo);
        manager.setManagers(new MapManager(new SimpleChunkedMap(this, new GVector2f(2, 2))));
        manager.setManagers(new PlayerManager(this, new SimpleMyPlayer(this,
                                                                       manager.getMapManager().getFreePosition(),
                                                                       "Gabriel",
                                                                       3,
                                                                       10, "player1.png", 4)));
        manager.setManagers(new ViewManager(manager.getPlayerManager().getMyPlayer(),
                                            manager.getMapManager().getMapSize(),
                                            parent.getCanvas().getWidth(),
                                            parent.getCanvas().getHeight(),
                                            3));
        generatePlayers(100);
    }

    private void generatePlayers(int count) {
        for (int i = 1; i <= count; i++) {
            int number = (int) Math.ceil(Math.random() * 3) + 1;
            manager.getPlayerManager().addPlayer(new SimpleNPC(this,
                                                               manager.getMapManager().getFreePosition(),
                                                               "player-" + i,
                                                               1,
                                                               10,
                                                               "player" + number + ".png",
                                                               4));
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        manager.render(g2);
        gameLogs.render(g2);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        manager.input();
    }
}

