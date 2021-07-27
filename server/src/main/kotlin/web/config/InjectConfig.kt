package web.config

import com.google.inject.AbstractModule
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

/**
 * @Author Lijie
 * @date  2021/5/17:19:53
 */

class InjectConfig(private val vertx: Vertx, private val router: Router) : AbstractModule() {
    override fun configure() {
        bind(Vertx::class.java).toInstance(vertx)
        bind(Router::class.java).toInstance(router)
    }
}
