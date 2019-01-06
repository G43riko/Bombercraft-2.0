package org.bombercraft2.game.level;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.flora.FloraManager;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.GMath;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.math.BVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level implements InteractAbleG2 {
    private final List<BVector2f> respawnZones = new ArrayList<>();
    private       Map             map          = null;
    private       GameAble        parent       = null;
    private       String          mapData      = null;
    private       JSONObject      floraData    = null;
    private       JSONObject      playerInfo   = null;
    private       FloraManager    floraManager = null;

    public Level(@NotNull JSONObject object) {
        try {
            mapData = object.getString(Texts.MAP);
            playerInfo = object.getJSONObject(Texts.PLAYER_INFO);
            int i = 0;
            while (object.has(Texts.RESPAWN_ZONE + i)) {
                respawnZones.add(new BVector2f(object.getString((Texts.RESPAWN_ZONE + i))));
                i++;
            }
            floraData = object.getJSONObject(Texts.FLORA);
            GLogger.log(GLogger.GLog.LEVEL_SUCCESSFULLY_CREATED);
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.LEVEL_CREATION_FAILED, e);
        }
    }

    public Level() {
        respawnZones.add(StaticConfig.BLOCK_SIZE);
        setDefaultPlayerInfo();
        GLogger.log(GLogger.GLog.LEVEL_SUCCESSFULLY_CREATED);
    }

    //OTHERS

    public void changeBlock(BVector2f position, int health, int type) {

    }


    @Override
    public void render(@NotNull Graphics2D g2) {
        map.render(g2);
        floraManager.renderLowerLevel(g2);
    }

    public void renderUpperFlora(Graphics2D g2) {
        floraManager.renderUpperLevel(g2);
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put(Texts.MAP, map.toJSON());
            for (int i = 0; i < respawnZones.size(); i++) {
                result.put(Texts.RESPAWN_ZONE + i, respawnZones.get(i));
            }

            result.put(Texts.PLAYER_INFO, playerInfo);
            result.put(Texts.FLORA, floraManager.toJSON());
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_SERIALIZE_LEVEL, e);
        }
        return result;
    }


    public Map getMap() {return map;}

    public GameAble getParent() {return parent;}

    public boolean isReady() {return parent != null && map != null;}

    public List<BVector2f> getRespawnZones() {return new ArrayList<>(respawnZones);}

    public BVector2f getRandomRespawnZone() {return new BVector2f(GMath.choose(respawnZones));}

    //SETTERS

    private void setDefaultPlayerInfo() {
        playerInfo = new JSONObject();
        try {
            playerInfo.put(Texts.SPEED, 4);
            playerInfo.put(Texts.RANGE, 2);
            playerInfo.put(Texts.HEALTH, 10);
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_SET_DEFAULT_PLAYER_INFO, e);
        }
    }

    public void setGame(GameAble game) {
        parent = game;

        if (mapData != null) {
            try {
                map = new Map(new JSONObject(mapData), parent);
            }
            catch (JSONException e) {
                GLogger.error(GLogger.GError.MAP_PARSING_FAILED, e);
            }
        }
        else {
            map = new Map(game, new BVector2f(40, 40));
        }

        floraManager = new FloraManager(game, map);
        if (floraData != null) {
            floraManager.fromJSON(floraData);
        }

    }


    public JSONObject getDefaultPlayerInfo() {
        return playerInfo;
    }


}
