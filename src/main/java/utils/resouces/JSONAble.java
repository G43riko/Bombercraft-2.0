package utils.resouces;

import org.json.JSONObject;

public interface JSONAble {
	void fromJSON(JSONObject data);
	JSONObject toJSON();
}
