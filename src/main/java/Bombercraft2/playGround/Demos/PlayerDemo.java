package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.bots.SimpleMyPlayer;
import Bombercraft2.playGround.Misc.bots.SimpleNPC;
import Bombercraft2.playGround.Misc.bots.SimplePlayer;
import Bombercraft2.playGround.Misc.map.MapManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDemo extends SimpleAbstractGame<CorePlayGround> implements SimpleGameAble {
    private static GVector2f          NUMBER_OF_CHUNKS = new GVector2f(1, 1);
    private        List<SimplePlayer> players          = new ArrayList<>();
    private        SimpleMyPlayer     myPlayer         = new SimpleMyPlayer(this,
                                                                            new GVector2f(),
                                                                            "Gabriel",
                                                                            3,
                                                                            10, "player1.png", 4);

    public PlayerDemo(CorePlayGround parent) {
        super(parent, Type.PlayerDemo);
        manager.setManagers(new ViewManager(NUMBER_OF_CHUNKS.mul(Config.BLOCK_SIZE).mul(Config.CHUNK_SIZE),
                                            parent.getCanvas().getWidth(),
                                            parent.getCanvas().getHeight(),
                                            3));

        manager.getViewManager().setTarget(myPlayer);
        manager.setManagers(new MapManager(new SimpleChunkedMap(this, NUMBER_OF_CHUNKS)));
        myPlayer.setPosition(manager.getMapManager().getFreePosition());
        for (int i = 1; i <= 100; i++) {
            int number = (int)Math.ceil(Math.random() * 3) + 1;
            players.add(new SimpleNPC(this,
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
        players.forEach((player) -> player.render(g2));
        myPlayer.render(g2);
    }


    @Override
    public void update(float delta) {
        manager.update(delta);
        myPlayer.update(delta);
        players.parallelStream().forEach((player) -> {
            player.update(delta);
            player.input();
        });
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        manager.input();
        myPlayer.input();
    }
}

