package web.factory

import io.vertx.core.json.JsonArray
import org.apache.logging.log4j.LogManager
import web.enums.WebSocketSignalTypeEnum

/**
 * 信令
 * @Author passxml
 * @date  2021/6/15:19:57
 */
object WebSocketSignalingFactory {
    private val logger = LogManager.getLogger(WebSocketSignalingFactory::class.java)
    fun userLeft(id: String): String {
        val jsonArray = JsonArray()
        jsonArray.add(WebSocketSignalTypeEnum.userLeft)
        jsonArray.add(id)
        return toString(jsonArray)
    }

    fun login(id: String): String {
        val jsonArray = JsonArray()
        jsonArray.add(WebSocketSignalTypeEnum.login)
        jsonArray.add(id)
        return toString(jsonArray)
    }

    fun userJoin(id: String, count: Long, users: MutableList<String>): String {
        val jsonArray = JsonArray()
        jsonArray.add(WebSocketSignalTypeEnum.userJoin)
        jsonArray.add(id)
        jsonArray.add(count)
        val clients = JsonArray()
        for (userId in users) {
            clients.add(userId)
        }
        jsonArray.add(clients)
        return toString(jsonArray)
    }

    fun signal(fromId: String, singal: String): String {
        val jsonArray = JsonArray()
        jsonArray.add(WebSocketSignalTypeEnum.signal)
        jsonArray.add(fromId)
        jsonArray.add(singal)
        return toString(jsonArray)
    }

    fun toString(array: JsonArray): String {
        logger.debug(array)
        return array.toString()
    }

    fun ping(): String {
        val jsonArray = JsonArray()
        jsonArray.add(WebSocketSignalTypeEnum.ping)
        return jsonArray.toString()
    }
}
