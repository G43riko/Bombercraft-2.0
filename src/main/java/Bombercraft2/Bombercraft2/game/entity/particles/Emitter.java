package Bombercraft2.Bombercraft2.game.entity.particles;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Utils;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Emitter extends Entity<GameAble> {
    private static final HashMap<EmitterTypes, JSONObject> predefinedParticles = new HashMap<>();

    ArrayList<Particle> particles = new ArrayList<>();
    private Color color;
    float particlePerFrame;
    private GVector2f speed; // x - value, y - randomness
    private GVector2f rotation; // - speed, y - randomness
    private GVector2f health; // - normal, y - randomness
    private GVector2f direction; // x - start angle, y - end angle
    private GVector2f size;
    private int       sizeRandomness;
    private GVector2f positionRandomness;
    private long      renderedParticles;
    private int       particlesOnStart;

    static {
        initDefault();
    }
//

    Emitter(EmitterTypes type, GVector2f position, GameAble parent) {
        super(position, parent);

        loadDataFromJSON(predefinedParticles.get(type));

        if (type == EmitterTypes.PARTICLE_EXPLOSION_BLUE_SPARK) {
            color = Utils.choose(Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHT_GRAY, Color.BLUE);
        }

        createParticles(particlesOnStart);
    }


    @Override
    public void render(@NotNull Graphics2D g2) {
        renderedParticles = new ArrayList<>(particles).stream()
                                                      .filter(a -> getParent().isVisible(a))
                                                      .peek(a -> a.render(g2))
                                                      .count();
    }

    void createParticles(int numOfParticles) {
        for (int i = 0; i < numOfParticles; i++) {
            particles.add(new Particle(position.add(new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(
                    positionRandomness)),
                                       getParent(),
                                       color,
                                       new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(speed.getX()),
                                       size.add(sizeRandomness * (float) (Math.random() - 0.5)),
                                       (int) (health.getXi() + health.getYi() * (Math.random() - 0.5))));
        }
    }

    private void loadDataFromJSON(JSONObject object) {
        try {
            this.color = new Color(object.getInt("color"));
            this.speed = new GVector2f(object.getString("speed"));
            this.rotation = new GVector2f(object.getString("rotation"));
            this.health = new GVector2f(object.getString("health"));
            this.direction = new GVector2f(object.getString("speed"));
            this.size = new GVector2f(object.getString("size"));
            this.sizeRandomness = object.getInt("sizeRandomness");
            this.positionRandomness = new GVector2f(object.getString("positionRandomness"));
            this.particlePerFrame = (float) object.getDouble("particlePerFrame");
            this.particlesOnStart = object.getInt("particlesOnStart");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static void initDefault() {
        try {
            EmitterTypes[] types = EmitterTypes.values();
            JSONObject object = ResourceLoader.getJSON(StaticConfig.FILE_PARTICLES);
            for (EmitterTypes type : types) {
                predefinedParticles.put(type, object.getJSONObject(type.getName()));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public long getRenderedParticles() {return renderedParticles;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getSize() {return size;}

    public boolean isAlive() {return alive || !particles.isEmpty();}

}
