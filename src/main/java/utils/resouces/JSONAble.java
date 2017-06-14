package utils.resouces;

import org.json.JSONObject;

public interface JSONAble {
	public void fromJSON(JSONObject data);
	public JSONObject toJSON();
}
