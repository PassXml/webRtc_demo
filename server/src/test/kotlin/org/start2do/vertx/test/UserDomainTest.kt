package org.start2do.vertx.test

/**
 * @Author passxml
 * @date  2021/6/6:09:00
 */
//
//@ExtendWith(VertxExtension::class)
//class UserDomainTest {
//
//    companion object {
//        @BeforeAll
//        @JvmStatic
//        fun setUp(vertx: Vertx) {
//            vertx.exceptionHandler { it.printStackTrace() }
//            val options = VertxOptions()
//            ServiceManager.init(vertx)
//            ServiceManager.publish("service", "org.start2do.vertx")
//        }
//    }
//
//
//    @Test
//    fun listWorkRecord(vertx: Vertx, testContext: VertxTestContext) {
//        getProxy<UserDomain>().blockingHandler {
//            println(it.load(1).await())
//        }
//
//    }
//
//}
