package web.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;
import java.util.concurrent.atomic.LongAdder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.start2do.vertx.codegen.Start2doCodeGen;

@Start2doCodeGen
@DataObject
@Getter
@Setter
public class WebSocketUser {

  private String id;
  private String name;
  private String role;
  private String roomId;
  private String dspInfo;
  private SockJSSocket handler;
  private LongAdder heart = new LongAdder();

  public WebSocketUser() {
    this.heart.add(10);
  }

  public WebSocketUser(String id, String name, String role, String roomId) {
    this.id = id;
    this.name = name;
    this.role = role;
    this.roomId = roomId;
    this.heart.add(5);
  }

  @Start2doCodeGen(force = true)
  public WebSocketUser(io.vertx.core.json.JsonObject json) {
    if (json.containsKey("id")) {
      this.id = (String) json.getValue("id");
    }
    if (json.containsKey("name")) {
      this.name = (String) json.getValue("name");
    }
    if (json.containsKey("role")) {
      this.role = (String) json.getValue("role");
    }
    if (json.containsKey("roomId")) {
      this.roomId = (String) json.getValue("roomId");
    }
    if (json.containsKey("dspInfo")) {
      this.dspInfo = (String) json.getValue("dspInfo");
    }
  }

  @Start2doCodeGen(force = true)
  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    result.put("id", this.id);
    result.put("name", this.name);
    result.put("role", this.role);
    result.put("roomId", this.roomId);
    result.put("dspInfo", this.dspInfo);
    return result;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getDspInfo() {
    return dspInfo;
  }

  public void setDspInfo(String dspInfo) {
    this.dspInfo = dspInfo;
  }

  public SockJSSocket getHandler() {
    return handler;
  }

  public void setHandler(SockJSSocket handler) {
    this.handler = handler;
  }

  public LongAdder getHeart() {
    return heart;
  }

  public void setHeart(LongAdder heart) {
    this.heart = heart;
  }

  public boolean heartDecrement() {
    heart.decrement();
    return heart.sum() == 0;
  }
}
