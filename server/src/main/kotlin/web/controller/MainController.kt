package web.controller

import io.vertx.core.Vertx
import io.vertx.core.http.ServerWebSocket
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.api.service.RouteToEBServiceHandler
import io.vertx.ext.web.validation.ValidationHandler
import io.vertx.ext.web.validation.builder.Bodies
import io.vertx.json.schema.SchemaParser
import io.vertx.json.schema.SchemaRouter
import io.vertx.json.schema.SchemaRouterOptions
import io.vertx.json.schema.common.dsl.Schemas.objectSchema
import io.vertx.json.schema.common.dsl.Schemas.stringSchema
import org.start2do.vertx.web.utils.BaseController
import org.start2do.vertx.web.utils.Controller
import org.start2do.vertx.web.utils.HttpMethod
import org.start2do.vertx.web.utils.WebServiceHandles
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@Controller(mountPath = "/api")
class MainController : BaseController {
    @Inject
    constructor(router: Router, vertx: Vertx) : super(router, vertx)

    @Controller(mountPath = "/login", HttpMethod.POST)
    fun login(): WebServiceHandles {
        val schemaRouter = SchemaRouter.create(vertx, SchemaRouterOptions())
        val schemaParser = SchemaParser.createOpenAPI3SchemaParser(schemaRouter)
        val bodySchemaBuilder = objectSchema()
            .requiredProperty("username", stringSchema())
            .requiredProperty("password", stringSchema())
        val validationHandler = ValidationHandler.builder(schemaParser)
            .body(Bodies.json(bodySchemaBuilder))
            .build()
        return WebServiceHandles(
            validationHandler, RouteToEBServiceHandler.build(vertx.eventBus(), "WEB", "login")
        )
    }

    @Controller(mountPath = "/v3", useRoute = true)
    fun login(route: Route) {
        route.handler { rc ->
            rc.end("Hello")
        }

    }


    @Controller(mountPath = "/v2", useWebSocket = true)
    suspend fun ws(ws: ServerWebSocket) {
        val currentTimeMillis = System.currentTimeMillis()
        ws.accept()
        ws.handler {
            println(it.toString())
        }
        ws.writeTextMessage("你好")
        ws.closeHandler {
            println("${ws.binaryHandlerID()}在线时长:${System.currentTimeMillis() - currentTimeMillis}ms")
        }
    }

}
