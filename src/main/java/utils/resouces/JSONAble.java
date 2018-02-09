package utils.resouces;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public interface JSONAble {
    void fromJSON(@NotNull JSONObject data);

    @NotNull
    JSONObject toJSON();
}
