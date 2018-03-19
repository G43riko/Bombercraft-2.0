package Bombercraft2.playGround.Demos.BomberDemo;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.explosion.Explosion;
import Bombercraft2.Bombercraft2.gui.GameLogs;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.*;
import Bombercraft2.playGround.Misc.bots.SimpleMyPlayer;
import Bombercraft2.playGround.Misc.bots.SimpleNPC;
import Bombercraft2.playGround.Misc.map.MapManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;

public class BomberDemo extends SimpleAbstractGame<CorePlayGround> {
    private GameLogs gameLogs = new GameLogs(this);

    public BomberDemo(CorePlayGround parent) {
        super(parent, Type.BomberDemo);
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
        manager.setManagers(new PostFxManager(this, manager.getMapManager().getMapSize()));
        manager.setManagers(new SceneManager_(this));
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
        Utils.measureNano("---------Render total", () -> {
            Utils.measureNano("-----------Render - clear", () -> {
                g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
            });
            Utils.measureNano("---------Render - manager", () -> {
                manager.render(g2);
            });
            Utils.measureNano("------------Render - logs", () -> {
                gameLogs.render(g2);
            });
        });
    }

    @Override
    public void update(float delta) {
        Utils.measureNano("---------Uptate total", () -> manager.update(delta));
    }

    @Override
    public void input() {
        Utils.measureNano("----------Input total", () -> {
            if (Input.getKeyDown(Input.KEY_ESCAPE)) {
                parent.stopDemo();
            }

            if (Input.getKeyDown(Input.KEY_LCONTROL)) {
                SimpleMyPlayer player = manager.getPlayerManager().getMyPlayer();
                if (player != null) {
                    ImagedBomb bomb = new ImagedBomb(new GVector2f(player.getPosition()), this);
                    bomb.callback = (b) -> {
                        manager.getSceneManager()
                               .addExplosion(new Explosion(b.getPosition().add(Config.BLOCK_SIZE_HALF),
                                                           this,
                                                           Config.BLOCK_SIZE,
                                                           Color.black,
                                                           10 + ((int)(Math.random() * 20)),
                                                           true,
                                                           true));
                        manager.getPostFxManager().addImage(((ImagedBomb) b).getCrater(),
                                                            b.getPosition(),
                                                            b.getSize());
                    };
                    manager.getSceneManager().addBomb(bomb);
                }
            }
            manager.input();
        });
    }
}
