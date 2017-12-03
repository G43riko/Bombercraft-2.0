package Bombercraft2.Bombercraft2.game.entity.flora;

import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.GVector2f;
import utils.resouces.JSONAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class FloraManager implements JSONAble {
    private List<Bush>  bushes = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private List<Tree>  trees  = new ArrayList<>();
    private final GameAble parent;
    private final Map      map;

    public FloraManager(GameAble parent, Map map) {
        this.parent = parent;
        this.map = map;
        createFlora();
    }

    public void addFlora(Flora flora) {
        switch (flora.getType().getType()) {
            case BUSH:
                bushes.add((Bush) flora);
                break;
            case PLANT:
                plants.add((Plant) flora);
                break;
            case TREE:
                trees.add((Tree) flora);
                break;
        }
    }

    public void renderUpperLevel(Graphics2D g2) {
        trees.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
    }

    public void renderLowerLevel(Graphics2D g2) {
        plants.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
        bushes.stream().filter(parent::isVisible).forEach(a -> a.render(g2));
    }

    private void creteBushByType(Flora.Bushes type, GVector2f maxSize) {
        GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
        if (map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS) {
            addFlora(new Bush(type, sur, parent));
        }
    }

    private void creteTreeByType(Flora.Trees type, GVector2f maxSize) {
        GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
        if (map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS) {
            addFlora(new Tree(type, sur, parent));
        }
    }

    private void cretePlantByType(Flora.Plants type, GVector2f maxSize) {
        GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
        if (map.getBlockOnPosition(sur.add(Block.SIZE.div(2))).getType() == Block.Type.GRASS) {
            addFlora(new Plant(type, sur, parent));
        }
    }

    private void createFlora() {
        GVector2f nums = map.getSize().sub(Block.SIZE);
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < Flora.Bushes.values().length; j++) {
                creteBushByType(Flora.Bushes.values()[j], nums);
            }
            for (int j = 0; j < Flora.Trees.values().length; j++) {
                creteTreeByType(Flora.Trees.values()[j], nums);
            }
            for (int j = 0; j < Flora.Plants.values().length; j++) {
                cretePlantByType(Flora.Plants.values()[j], nums);
            }
        }
    }

    @Override
    public void fromJSON(JSONObject data) {
        bushes.clear();
        plants.clear();
        trees.clear();
        try {
            JSONArray bushesArray = data.getJSONArray(Texts.BUSHES);
            for (int i = 0; i < bushesArray.length(); i++) {
                bushes.add(new Bush(parent, bushesArray.getJSONObject(i)));
            }

            JSONArray plantsArray = data.getJSONArray(Texts.PLANTS);
            for (int i = 0; i < plantsArray.length(); i++) {
                plants.add(new Plant(parent, plantsArray.getJSONObject(i)));
            }

            JSONArray treesArray = data.getJSONArray(Texts.TREES);
            for (int i = 0; i < treesArray.length(); i++) {
                trees.add(new Tree(parent, treesArray.getJSONObject(i)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            JSONArray bushesArray = new JSONArray();
            bushes.forEach(a -> bushesArray.put(a.toJSON()));
            result.put(Texts.BUSHES, bushesArray);

            JSONArray plantsArray = new JSONArray();
            plants.forEach(a -> plantsArray.put(a.toJSON()));
            result.put(Texts.PLANTS, plantsArray);

            JSONArray treesArray = new JSONArray();
            trees.forEach(a -> treesArray.put(a.toJSON()));
            result.put(Texts.TREES, treesArray);
            return result;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
