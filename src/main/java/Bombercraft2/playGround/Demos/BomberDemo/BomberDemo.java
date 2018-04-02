package Bombercraft2.playGround.Demos.BomberDemo;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.explosion.Explosion;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.gui.GameLogs;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.*;
import Bombercraft2.playGround.Misc.bots.SimpleMyPlayer;
import Bombercraft2.playGround.Misc.bots.SimpleNPC;
import Bombercraft2.playGround.Misc.map.*;
import Bombercraft2.playGround.Misc.selectors.SelectorManager;
import Bombercraft2.playGround.Misc.selectors.SimpleLineSelector;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;

public class BomberDemo extends SimpleAbstractGame<CorePlayGround> {
    private GameLogs                      gameLogs = new GameLogs(this);
    private SimpleMapRaycast<SimpleChunk> raycast;

    public BomberDemo(CorePlayGround parent) {
        super(parent, Type.BomberDemo);
        final SimpleChunkedMap map = new SimpleChunkedMap(this, new GVector2f(2, 2));
        manager.setManagers(new MapManager(map));
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
        manager.setManagers(new SelectorManager(this, new SimpleLineSelector()));
        manager.setManagers(new SceneManager_(this));
        manager.getPlayerManager().getMyPlayer().setPosition(Config.BLOCK_SIZE.mul(2));
        raycast = new SimpleMapRaycast(map);
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

            GVector2f playerPos = manager.getPlayerManager().getMyPlayer().getCenter();
            GVector2f transformedPlayerPos = manager.getViewManager().transform(playerPos);

            raycast.onHit((SimpleTypedBlock block, GVector2f position) -> {
                GVector2f realPos = manager.getViewManager().transform(block.getPosition());
                g2.setColor(Color.WHITE);
                GVector2f realIntersectCollision = manager.getViewManager().transform(position);
                g2.drawArc(realPos.getXi(), realPos.getYi(), 20, 20, 0, 360);
                g2.setColor(Color.RED);
                g2.drawArc(realIntersectCollision.getXi(),
                           realIntersectCollision.getYi(), 20, 20, 0, 360);

                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(transformedPlayerPos.getXi(),
                            transformedPlayerPos.getYi(),
                            realIntersectCollision.getXi(),
                            realIntersectCollision.getYi());
                return true;
            });

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(transformedPlayerPos.getXi(),
                        transformedPlayerPos.getYi(),
                        Input.getMousePosition().getXi(),
                        Input.getMousePosition().getYi());
            raycast.drawLine(playerPos, manager.getViewManager().transformInvert(Input.getMousePosition()));
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
            if (Input.getMouseDown(Input.BUTTON_LEFT)) {
                raycast.getFirstBlock(manager.getPlayerManager().getMyPlayer().getCenter(),
                                      manager.getViewManager().transformInvert(Input.getMousePosition())).remove();
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
                                                           10 + ((int) (Math.random() * 20)),
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
