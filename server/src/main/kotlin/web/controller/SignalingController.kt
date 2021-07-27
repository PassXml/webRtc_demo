package web.controller

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions
import io.vertx.ext.web.handler.sockjs.SockJSSocket
import org.apache.logging.log4j.LogManager
import org.start2do.vertx.ext.toJsonObject
import org.start2do.vertx.web.utils.BaseController
import web.dto.WebSocketLoginReq
import web.dto.WebSocketRoom
import web.dto.WebSocketUser
import web.enums.WebSocketSignalTypeEnum
import web.factory.WebSocketSignalingFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/**
 * @Author passxml
 * @date  2021/6/13:14:59
 */
class SignalingController : BaseController {
    companion object {
        val logger = LogManager.getLogger(SignalingController::class.java)
    }

    @Inject
    constructor(router: Router, vertx: Vertx) : super(router, vertx)

    val roomMap = ConcurrentHashMap<String, WebSocketRoom>()
    val userInfo = ConcurrentHashMap<String, WebSocketUser>()
    val handleId = ConcurrentHashMap<SockJSSocket, String>()
    lateinit var sockJSHandler: SockJSHandler;
    override fun build(): BaseController {
        sockJSHandler = SockJSHandler.create(vertx, SockJSHandlerOptions().setHeartbeatInterval(2000))
        router.mountSubRouter("/ws", sockJSHandler.socketHandler { ws ->
            val id: String = ws.headers()["Sec-WebSocket-Key"]
            ws.handler {
                ws.on(WebSocketSignalTypeEnum.login, it) { array ->
                    val req = WebSocketLoginReq(array.getJsonObject(1))
                    val webSocketUser = WebSocketUser(id, req.name, "", "")
                    webSocketUser.handler = ws
                    userInfo[id] = webSocketUser
                    WebSocketSignalingFactory.login(id)
                }
//                ws.on(WebSocketSignalTypeEnum.pong, it) {
//                    userInfo[id]?.heart?.increment()
//                    ""
//                }
                ws.on(WebSocketSignalTypeEnum.userJoin, it) { jsonArray ->
                    val roomId = jsonArray.getString(1)
                    val room = roomMap[roomId] ?: WebSocketRoom(roomId, vertx)
                    val user = userInfo[id]!!
                    user.roomId = roomId
                    userInfo[id] = user
                    room.add(user)
                    roomMap[roomId] = room
                    room.sendAll(WebSocketSignalingFactory.userJoin(id, room.count.sum(), room.userIds))
                    null
                }
                ws.on(WebSocketSignalTypeEnum.signal, it) { jsonArray: JsonArray ->
                    val toUserId = jsonArray.getString(1)
                    if (toUserId.isEmpty()) {
                        return@on null
                    }
                    val string = jsonArray.getJsonObject(2)
                    userInfo[toUserId]?.let { user ->
                        user.handler.write(
                            WebSocketSignalingFactory.signal(id, string.toString())
                        )
                    }
                    null
                }
            }
            ws.endHandler {
                val user = userInfo[id]
                logger.info("用户:{}离开", id)
                user?.let {
                    val room = roomMap[it.roomId]
                    room?.userLeft(user)
                    userInfo.remove(id)
                }
            }

        })
        return super.build()
    }

}

private inline fun SockJSSocket.on(
    message: WebSocketSignalTypeEnum,
    buffer: Buffer,
    function: (array: JsonArray) -> String?
) {
    try {
        SignalingController.Companion.logger.debug("{},{}", "Message", buffer.toString())
        val array = buffer.toJsonArray()
        if (WebSocketSignalTypeEnum.valueOf(array.getString(0)) == message) {
            function.invoke(array)?.let {
                this.write(it)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        this.close()
    }
}



