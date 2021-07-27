package web.dto;

import io.vertx.codegen.annotations.DataObject;
import lombok.Data;
import org.start2do.vertx.codegen.Start2doCodeGen;
import io.vertx.core.json.JsonObject;

/**
 * @Author Lijie
 *
 * @date 2021/5/15:09:10
 */
@Data
@DataObject
@Start2doCodeGen
public class UserFindDto {

	public UserFindDto() {
	}

	public UserFindDto(JsonObject json) {
	}

	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		return result;
	}

}
