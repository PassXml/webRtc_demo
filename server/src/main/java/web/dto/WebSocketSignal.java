package web.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.start2do.vertx.codegen.Start2doCodeGen;
import web.enums.WebSocketSignalTypeEnum;

@Start2doCodeGen
@DataObject
public class WebSocketSignal {

	private WebSocketSignalTypeEnum type;
	private String body;

	public WebSocketSignal() {
	}

	public WebSocketSignal(JsonObject json) {
		if (json.containsKey("type")) {
			this.type = WebSocketSignalTypeEnum.valueOf(json.getString("type"));
		}
		if (json.containsKey("body")) {
			this.body = json.getString("body");
		}
	}

	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		result.put("type", this.type);
		result.put("bode", this.body);
		return result;
	}

	public WebSocketSignalTypeEnum getType() {
		return type;
	}

	public void setType(WebSocketSignalTypeEnum type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
