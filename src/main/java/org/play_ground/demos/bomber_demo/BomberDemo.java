package org.play_ground.demos.bomber_demo;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.GameStateType;
import org.bombercraft2.game.entity.explosion.Explosion;
import org.bombercraft2.gui.GameLogs;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.*;
import org.play_ground.misc.bots.SimpleMyPlayer;
import org.play_ground.misc.bots.SimpleNPC;
import org.play_ground.misc.map.MapManager;
import org.play_ground.misc.map.SimpleChunk;
import org.play_ground.misc.map.SimpleChunkedMap;
import org.play_ground.misc.map.SimpleTypedBlock;
import org.play_ground.misc.selectors.SelectorManager;
import org.play_ground.misc.selectors.SimpleLineSelector;
import org.utils.MeasureUtils;
import org.utils.enums.Keys;

import java.awt.*;

public class BomberDemo extends SimpleAbstractGame<CorePlayGround> {
    private GameLogs                      gameLogs = new GameLogs(this);
    private SimpleMapRaycast<SimpleChunk> raycast;

    public BomberDemo(CorePlayGround parent) {
        super(parent, GameStateType.BomberDemo);
        final SimpleChunkedMap map = new SimpleChunkedMap(this, new GVector2f(2, 2));
        manager.setManagers(new MapManager(map));
        manager.setManagers(new PlayerManager(this, new SimpleMyPlayer(this,
                                                                       // manager.getMapManager().getFreePosition(),
                                                                       new GVector2f(50, 50),
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
        manager.getPlayerManager().getMyPlayer().setPosition(StaticConfig.BLOCK_SIZE.getMul(2));
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
        MeasureUtils.measureNano("---------Render total", () -> {
            MeasureUtils.measureNano("-----------Render - clear", () -> {
                g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
            });
            MeasureUtils.measureNano("---------Render - manager", () -> {
                manager.render(g2);
            });
            MeasureUtils.measureNano("------------Render - logs", () -> {
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
        MeasureUtils.measureNano("---------Uptate total", () -> manager.update(delta));
    }

    @Override
    public void input() {
        MeasureUtils.measureNano("----------Input total", () -> {
            if (Input.getKeyDown(Keys.ESCAPE)) {
                parent.stopDemo();
            }
            if (Input.getMouseDown(Input.BUTTON_LEFT)) {
                SimpleTypedBlock block = raycast.getFirstBlock(manager.getPlayerManager().getMyPlayer().getCenter(),
                                                               manager.getViewManager().transformInvert(Input.getMousePosition()));
                if (block != null) {
                    block.remove();
                }
            }

            if (Input.getKeyDown(Keys.LCONTROL)) {
                SimpleMyPlayer player = manager.getPlayerManager().getMyPlayer();
                if (player != null) {
                    ImagedBomb bomb = new ImagedBomb(new GVector2f(player.getPosition()), this);
                    bomb.callback = (b) -> {
                        manager.getSceneManager()
                                .addExplosion(new Explosion(b.getPosition().getAdd(StaticConfig.BLOCK_SIZE_HALF),
                                                            this,
                                                            StaticConfig.BLOCK_SIZE,
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
