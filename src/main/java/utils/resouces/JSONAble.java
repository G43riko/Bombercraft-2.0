package utils.resouces;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public interface JSONAble {
    void fromJSON(@NotNull JSONObject data);

    @NotNull
    JSONObject toJSON();

    default void JSONWrapper(JSONEvent event) {
        try {
            event.event();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
