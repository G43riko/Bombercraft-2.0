package org.bombercraft2.game.entity.flora;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.level.BlockType;
import org.bombercraft2.game.level.Map;
import org.glib2.interfaces.JSONAble;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class FloraManager implements JSONAble {
    private final List<Bush>  bushes = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private final List<Tree>  trees  = new ArrayList<>();
    private final GameAble    parent;
    private final Map         map;

    public FloraManager(GameAble parent, Map map) {
        this.parent = parent;
        this.map = map;
        createFlora();
    }

    private void addFlora(Flora flora) {
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
        if (map.getBlockOnPosition(sur.getAdd(StaticConfig.BLOCK_SIZE_HALF)).getType() == BlockType.GRASS) {
            addFlora(new Bush(type, sur, parent));
        }
    }

    private void creteTreeByType(Flora.Trees type, GVector2f maxSize) {
        GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
        if (map.getBlockOnPosition(sur.getAdd(StaticConfig.BLOCK_SIZE_HALF)).getType() == BlockType.GRASS) {
            addFlora(new Tree(type, sur, parent));
        }
    }

    private void cretePlantByType(Flora.Plants type, GVector2f maxSize) {
        GVector2f sur = new GVector2f(Math.random() * maxSize.getX(), Math.random() * maxSize.getY());
        if (map.getBlockOnPosition(sur.getAdd(StaticConfig.BLOCK_SIZE_HALF)).getType() == BlockType.GRASS) {
            addFlora(new Plant(type, sur, parent));
        }
    }

    private void createFlora() {
        GVector2f numbers = map.getSize().getSub(StaticConfig.BLOCK_SIZE);
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < Flora.Bushes.values().length; j++) {
                creteBushByType(Flora.Bushes.values()[j], numbers);
            }
            for (int j = 0; j < Flora.Trees.values().length; j++) {
                creteTreeByType(Flora.Trees.values()[j], numbers);
            }
            for (int j = 0; j < Flora.Plants.values().length; j++) {
                cretePlantByType(Flora.Plants.values()[j], numbers);
            }
        }
    }

    @Override
    public void fromJSON(@NotNull JSONObject data) {
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

    @NotNull
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
