package Bombercraft2.Bombercraft2.game.entity.towers;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Helper.Type;
import Bombercraft2.Bombercraft2.game.level.Map;
import Bombercraft2.Bombercraft2.game.player.ToolAble;
import org.json.JSONException;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.util.HashMap;

public class TowerCreator implements ToolAble {
    private static final HashMap<Type, TowerModel> towers = new HashMap<>();
    private final GameAble parent;
    private final Type     type;

    public static void init(JSONObject data) {
        try {
            towers.put(Type.TOWER_MACHINE_GUN, new TowerModel(data.getJSONObject(Type.TOWER_MACHINE_GUN.getName())));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static TowerModel getTower(Type type) {
        return towers.get(type);
    }

    public static TowerModel getTower(String type) {
        return towers.get(Type.valueOf(type));
    }

    public TowerCreator(GameAble parent, Type type) {
        this.parent = parent;
        this.type = type;
    }

    @Override
    public void useOnLocalPos(GVector2f pos) {
        useOnGlobalPos(Map.localPosToGlobalPos(pos));
    }

    @Override
    public void useOnGlobalPos(GVector2f pos) {
        //bombu chcemepoložiť iba tam kde sa da
        if (parent.getLevel().getMap().getBlockOnPosition(pos).isWalkable()) {
            parent.getConnector().setPutHelper(pos, type);
        }
    }
}
