package web

import com.google.inject.AbstractModule
import io.vertx.core.Context
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.core.net.PemKeyCertOptions
import io.vertx.core.net.PemTrustOptions
import org.start2do.vertx.dto.ServiceVerticle
import org.start2do.vertx.web.AbsWebVerticle
import web.config.InjectConfig


@ServiceVerticle
class WebVerticle : AbsWebVerticle() {

    override fun injectConfig(packages: JsonArray): MutableList<AbstractModule> {
        val list = super.injectConfig(packages)
        list.add(InjectConfig(vertx, router))
        return list
    }
}


