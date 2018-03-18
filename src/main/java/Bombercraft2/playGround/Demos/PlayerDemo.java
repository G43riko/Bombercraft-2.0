package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.gui.GameLogs;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.PlayerManager;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.bots.SimpleMyPlayer;
import Bombercraft2.playGround.Misc.bots.SimpleNPC;
import Bombercraft2.playGround.Misc.map.MapManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class PlayerDemo extends SimpleAbstractGame<CorePlayGround> {
    private GameLogs gameLogs = new GameLogs(this);

    public PlayerDemo(CorePlayGround parent) {
        super(parent, Type.PlayerDemo);
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
        for (int i = 1; i <= 100; i++) {
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
    public void update(float delta) {
        manager.update(delta);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        manager.input();
    }
}

