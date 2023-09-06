package com.follis


import com.follis.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)


}

fun Application.module() {

    val tokenService= TokengnerateService()
    val tokenconfig = TokenConfig(
        issuer = "http://0.0.0.0:8080",
        audience = "users",
        expiresIn = 365L*1000L*60L*60L*24L,
        secret = "follisnursery"
    )
    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenconfig)
    configureRouting(tokenService,tokenconfig)








}



