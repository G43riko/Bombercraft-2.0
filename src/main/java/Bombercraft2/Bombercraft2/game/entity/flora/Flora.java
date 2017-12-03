package Bombercraft2.Bombercraft2.game.entity.flora;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;

public abstract class Flora extends Entity {
    protected Florable type  = null;
    protected float    scale = 1.0f;

    protected interface Florable extends Iconable {
        GVector2f getSize();

        FloraType getType();

        static Florable valueOf(String value) {
            if (Utils.isInStringable(value, Plants.values())) {
                return Plants.valueOf(value);
            }
            if (Utils.isInStringable(value, Bushes.values())) {
                return Bushes.valueOf(value);
            }
            if (Utils.isInStringable(value, Trees.values())) {
                return Trees.valueOf(value);
            }
            GLogger.printLine("nerozpoznalo to floru typu: " + value);
            return null;
        }
    }

    protected void fromJSON(JSONObject data) {
        try {
            setPosition(new GVector2f(data.getString(Texts.POSITION)));
            setType(Florable.valueOf(data.getString(Texts.TYPE)));
            scale = (float) data.getDouble(Texts.SCALE);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public enum FloraType {
        BUSH, TREE, PLANT
    }

    public Flora(GVector2f position, GameAble parent, Florable type) {
        super(position, parent);
        this.type = type;
    }

    public Florable getType() {
        return type;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put(Texts.POSITION, position);
            result.put(Texts.TYPE, type);
            result.put(Texts.SCALE, scale);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void setType(Florable type) {
        this.type = type;
    }

    public enum Bushes implements Florable {
        BUSH1("bush1"),
        BUSH2("bush2"),
        BUSH3("bush3");

        private final Image     image;
        private final FloraType type;
        private final GVector2f size;

        Bushes(String imageName) {
            this.type = FloraType.BUSH;
            image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
            size = new GVector2f(image.getWidth(null), image.getHeight(null));
        }

        public Image getImage() {return image;}

        public GVector2f getSize() {return size;}

        public FloraType getType() {return type;}

    }

    public enum Trees implements Florable {
        TREE1("tree1"),
        TREE2("tree2"),
        TREE3("tree3"),
        TREE4("tree4"),
        TREE5("tree5"),
        TREE6("tree6");

        private final Image     image;
        private final FloraType type;
        private final GVector2f size;

        Trees(String imageName) {
            this.type = FloraType.TREE;
            image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
            size = new GVector2f(image.getWidth(null), image.getHeight(null));
        }

        public Image getImage() {return image;}

        public GVector2f getSize() {return size;}

        public FloraType getType() {return type;}
    }

    public enum Plants implements Florable {
        PLANT1("plant1"),
        PLANT2("plant2"),
        PLANT3("plant3"),
        PLANT4("plant4"),
        PLANT5("plant5");

        private final Image     image;
        private final FloraType type;
        private final GVector2f size;

        Plants(String imageName) {
            this.type = FloraType.PLANT;
            image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
            size = new GVector2f(image.getWidth(null), image.getHeight(null));
        }

        public Image getImage() {return image;}

        public GVector2f getSize() {return size;}

        public FloraType getType() {return type;}
    }
}
