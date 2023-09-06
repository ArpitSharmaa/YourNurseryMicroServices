package com.follis.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(config: TokenConfig){
   authentication {
     jwt{
        realm = "users"
        verifier(
           JWT.require(Algorithm.HMAC256(config.secret))
              .withAudience(config.audience)
              .withIssuer(config.issuer)
              .build()
        )
        validate {
           if ( config.audience in it.payload.audience){
              JWTPrincipal(it.payload)
           }else{
              null
           }
        }
     }
  }
}







