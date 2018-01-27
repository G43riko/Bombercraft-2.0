package Bombercraft2.Bombercraft2.game;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.bots.BotManager.Types;
import Bombercraft2.Bombercraft2.game.bots.Enemy;
import Bombercraft2.Bombercraft2.game.bots.EnemyA;
import Bombercraft2.Bombercraft2.game.entity.Bomb;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.Bombercraft2.game.entity.Helper;
import Bombercraft2.Bombercraft2.game.entity.bullets.Bullet;
import Bombercraft2.Bombercraft2.game.entity.explosion.Explosion;
import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import Bombercraft2.Bombercraft2.game.entity.particles.ParticleEmitter;
import Bombercraft2.Bombercraft2.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.math.GVector2f;
import utils.resouces.JSONAble;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SceneManager implements InteractAble, JSONAble {
    private enum Keys {
        BULLETS("bullets"),
        BULLETS_RENDERED("bulletsRendered"),
        HELPERS("helpers"),
        HELPERS_RENDERED("helpersRendered"),
        BOMBS("bombs"),
        PARTICLES("particles"),
        PARTICLES_RENDERED("particlesRendered"),
        ENEMIES("enemies"),
        ENEMIES_RENDERED("enemiesRendered"),
        EXPLOSIONS("explosions");
        final String key;

        Keys(String key) {this.key = key;}
    }

    private final HashMap<String, Player> players        = new HashMap<>();
    //	private HashMap<String, Player>		playersNew			= new HashMap<>();
    private final HashMap<String, Helper> helpers        = new HashMap<>();
    private       ArrayList<Emitter>      emitters       = new ArrayList<>();
    private final HashSet<Helper>         helpersRemoved = new HashSet<>();
    private       ArrayList<Entity>       explosions     = new ArrayList<>();
    private       ArrayList<Bullet>       bullets        = new ArrayList<>();
    private       ArrayList<Enemy>        enemies        = new ArrayList<>();
    private final GameAble parent;
    private       Bomb                    lastBomb       = null;
    private final HashMap<Keys, Integer>  stats          = new HashMap<>();
//	private long						renderedParticles	= 0;

    public SceneManager(GameAble parent) {
        this.parent = parent;
        reset();
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        stats.put(Keys.BULLETS, stats.get(Keys.BULLETS) + 1);
    }

    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public void addHelper(String key, Helper helper) {
        if (Helper.isBomb(helper.getType())) {
            lastBomb = (Bomb) helper;
            stats.put(Keys.BOMBS, stats.get(Keys.BOMBS) + 1);
        }
        stats.put(Keys.HELPERS, stats.get(Keys.HELPERS) + 1);
        helpers.put(key, helper);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        stats.put(Keys.BULLETS_RENDERED, (int) bullets.stream()
                                                      .filter(parent::isVisible)
                                                      .peek(a -> a.render(g2))
                                                      .count());

        players.values().stream().filter(parent::isVisible).forEach(a -> a.render(g2));

        stats.put(Keys.ENEMIES_RENDERED, (int) enemies.stream()
                                                      .filter(parent::isVisible)
                                                      .peek(a -> a.render(g2))
                                                      .count());

        stats.put(Keys.HELPERS_RENDERED, (int) helpers.values().stream()
                                                      .filter(parent::isVisible)
                                                      .peek(a -> a.render(g2))
                                                      .count());

        stats.put(Keys.PARTICLES_RENDERED, (int) new ArrayList<>(emitters).stream()
                                                                                 .peek(a -> a.render(g2))
                                                                                 .mapToLong(Emitter::getRenderedParticles)
                                                                                 .sum());


        new ArrayList<>(explosions).forEach(a -> a.render(g2));
    }

    @Override
    public void update(float delta) {
//		playersNew.forEach((a, b)-> players.put(a, b));
//		playersNew.clear();

        emitters = new ArrayList<>(emitters).stream()
                                            .peek(a -> a.update(delta))
                                            .collect(Collectors.toCollection(ArrayList::new));

        bullets = new ArrayList<>(bullets).stream()
                                          .peek(a -> a.update(delta))
                                          .filter(Entity::isAlive)
                                          .collect(Collectors.toCollection(ArrayList::new));

        enemies = new ArrayList<>(enemies).stream()
                                          .peek(a -> a.update(delta))
                                          .filter(Entity::isAlive)
                                          .collect(Collectors.toCollection(ArrayList::new));

        helpers.values().removeAll(helpersRemoved);
        helpersRemoved.clear();
        helpers.values().forEach(a -> a.update(delta));


        explosions = new ArrayList<>(explosions).stream()
                                                .filter(Entity::isAlive)
                                                .peek(a -> a.update(delta))
                                                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean existHelperOn(String key) {
        return helpers.containsKey(key);
    }

    public int getPlayersCount() {
        return players.size();
    }

    public Player getPlayerByName(String name) {
        return players.get(name);
    }

    public void removePlayer(String key) {
        players.remove(key);
    }

    public void removeHelper(String key) {
        helpersRemoved.add(helpers.get(key));
    }

    @Override
    public void fromJSON(JSONObject data) {

    }

    public void addEnemy(GVector2f position, Types type) {
        stats.put(Keys.ENEMIES, stats.get(Keys.ENEMIES) + 1);
        switch (type) {
            case A:
                enemies.add(new EnemyA(position, parent));
                break;
        }

    }

    public void addEmitter(GVector2f position, Emitter.Types type) {
        emitters.add(new ParticleEmitter(type, position, parent));
    }

    public void addExplosion(GVector2f position,
                             GVector2f size,
                             Color color,
                             int number,
                             boolean explosion,
                             boolean shockWave
                            ) {
        stats.put(Keys.EXPLOSIONS, stats.get(Keys.EXPLOSIONS) + 1);
        explosions.add(new Explosion(position, parent, size, color, number, explosion, shockWave));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            Collection<Player> values = players.values();
            for (Player a : values) {
                result.put("player_" + result.length(), a.toJSON());
            }
            result.put(Texts.PLAYERS_NUMBER, players.size());
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_SERIALIZE_SCENE_MANAGER, e);
        }

        return result;
    }

    public List<Player> getPlayersInArea(GVector2f pos, GVector2f size) {
        List<Player> hitPlayers = new ArrayList<>();
        players.values().stream()
               .filter(a -> a.getCenter().isInRect(pos, size))
               .forEach(hitPlayers::add);
        return hitPlayers;
    }

    public boolean isBombOn(int xi, int yi, Bomb lastPutBomb) {
        for (Helper helper : helpers.values()) {
            if (Helper.isBomb(helper.getType())) {
                if (helper.getSur().equals(new GVector2f(xi, yi))) {
                    return helper != lastPutBomb;
                }
            }
        }
        return false;
    }

    public void reset() {
        Keys[] values = Keys.values();

        for (Keys value : values) {
            stats.put(value, 0);
        }

        helpers.clear();
        emitters.clear();
        bullets.clear();
        explosions.clear();
    }

    public Collection<? extends String> getLogInfo() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Helpers: " + stats.get(Keys.HELPERS_RENDERED) + "/" + helpers.size() + "/" + stats.get(Keys.HELPERS));

        result.add("Bullets: " + stats.get(Keys.BULLETS_RENDERED) + "/" + bullets.size() + "/" + stats.get(Keys.BULLETS));

        result.add("Enemies: " + stats.get(Keys.ENEMIES_RENDERED) + "/" + enemies.size() + "/" + stats.get(Keys.ENEMIES));
        return result;
    }

    public Bomb getLastBomb() {return lastBomb;}

    public HashMap<String, String> getStats() {
        HashMap<String, String> result = new HashMap<>();
        //RENDER/EXIST/ALL
        result.put(Keys.BULLETS.key, String.valueOf(stats.get(Keys.BULLETS)));
        result.put(Keys.HELPERS.key, String.valueOf(stats.get(Keys.HELPERS)));
        result.put(Keys.BOMBS.key, String.valueOf(stats.get(Keys.BOMBS)));
        result.put(Keys.EXPLOSIONS.key, String.valueOf(stats.get(Keys.EXPLOSIONS)));
        return result;
    }
}
