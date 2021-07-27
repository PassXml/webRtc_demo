package web.dto

import io.vertx.core.Vertx
import org.apache.logging.log4j.LogManager
import web.factory.WebSocketSignalingFactory
import java.util.*
import java.util.concurrent.atomic.LongAdder

/**
 * @Author Lijie
 * @date  2021/6/22:19:33
 */
class WebSocketRoom {
    companion object {
        private val logger = LogManager.getLogger(WebSocketRoom::class.java)
    }

    constructor(roomId: String, vert: Vertx) {
//        vert.setPeriodic(10000) {
//            for (user in removeList) {
//                users.remove(user)
//            }
//            for (user in users) {
//                if (user.heartDecrement()) {
//                    logger.info("{},loss heart userId:{}", roomId, user.id)
//                    try {
//                        user.handler.close()
//                    } catch (e: Exception) {
//                        logger.error(e.message, e)
//                    }
//                    userLeft(user)
//                }
//            }
//            sendAll(WebSocketSignalingFactory.ping())
//        }
    }

    val users = Collections.synchronizedList(mutableListOf<WebSocketUser>())
    val removeList = Collections.synchronizedList(mutableListOf<WebSocketUser>())
    val userIds = Collections.synchronizedList(mutableListOf<String>())
    val count = LongAdder()
    fun add(user: WebSocketUser) {
        userIds.add(user.id)
        users.add(user)
        count.increment()
    }


    fun sendAll(msg: String, vararg notSendId: String) {
        logger.info("send All user Message:{}",msg)
        for (user in users) {
            if (notSendId.any { it == user.id }) {
                continue
            }
            user.handler.write(msg)
        }
    }

    fun remove(it: WebSocketUser) {
        userIds.remove(it.id)
        removeList.add(it)
        count.decrement()
    }

    fun userLeft(user: WebSocketUser) {
        logger.debug("room remove user")
        remove(user)
        for (user in users) {
            user.handler.write(WebSocketSignalingFactory.userLeft(user.id))
        }
    }
}
