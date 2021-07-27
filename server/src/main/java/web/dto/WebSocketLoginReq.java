package web.dto;

import io.vertx.codegen.annotations.DataObject;
import org.start2do.vertx.codegen.Start2doCodeGen;
import io.vertx.core.json.JsonObject;

@Start2doCodeGen
@DataObject
public class WebSocketLoginReq {

  private String name;

  public WebSocketLoginReq() {}

  public WebSocketLoginReq(JsonObject json) {
    if (json.containsKey("name")) {
      this.name = (String) json.getValue("name");
    }
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    result.put("name", this.name);
    return result;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
