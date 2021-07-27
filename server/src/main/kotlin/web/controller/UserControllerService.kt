package web.controller

import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Log4j2LogDelegateFactory
import io.vertx.ext.web.api.service.ServiceRequest
import io.vertx.ext.web.api.service.ServiceResponse
import io.vertx.ext.web.api.service.WebApiServiceGen
import io.vertx.kotlin.coroutines.await
import io.vertx.serviceproxy.ServiceBinder
import org.start2do.api.AbsService
import org.start2do.dto.Pageable
//import org.start2do.vertx.db.entity.tables.pojos.BaseUserInfo
import org.start2do.vertx.dto.ResultMessageDto
import org.start2do.vertx.ext.createFuture
import org.start2do.vertx.ext.handler
import org.start2do.vertx.ext.toJsonObject
import org.start2do.vertx.sys.BaseService
import org.start2do.vertx.utils.getProxy
import org.start2do.vertx.web.ext.build
import web.domain.UserDomain
import javax.inject.Singleton

/**
 * @Author passxml
 * @date  2021/5/29:09:04
 */
@WebApiServiceGen
interface UserControllerService : AbsService {
    fun login(
        body: JsonObject,
        context: ServiceRequest
    ): Future<ServiceResponse>
}


@Singleton
class UserControllerServiceImpl : UserControllerService, BaseService() {
    private val logger = Log4j2LogDelegateFactory().createDelegate(UserControllerServiceImpl::class.java.name)

    override fun register(serviceBinder: ServiceBinder): MessageConsumer<JsonObject> {
        return serviceBinder.setAddress("WEB").register(UserControllerService::class.java, this)
    }

    override fun login(
        body: JsonObject,
        context: ServiceRequest
    ): Future<ServiceResponse> {
        logger.debug(body)
//        val userInfo = body.build<BaseUserInfo> { baseUserInfo, jsonObject ->
//            baseUserInfo.name = jsonObject.getString("username")
//            baseUserInfo
//        }
//        return createFuture {
//            ServiceResponse.completedWithJson(
//                getProxy<UserDomain>().handler {
//                    val obj = it.login(userInfo).await().toJson()
//                    obj.remove(org.start2do.vertx.db.entity.tables.BaseUserInfo.BASE_USER_INFO.SALT.name)
//                    obj.remove(org.start2do.vertx.db.entity.tables.BaseUserInfo.BASE_USER_INFO.PASSWORD.name)
//                    it.page("hello", null, Pageable()).await()
//                    ResultMessageDto.build(
//                        obj
//                    )
//                }.toJsonObject()
//            )
//        }
        return createFuture { ServiceResponse.completedWithJson(Buffer.buffer("{}"))}
    }


}

