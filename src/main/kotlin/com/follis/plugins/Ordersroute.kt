package com.follis.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*

fun Route.neworders(){
    val db = Databaseconnection.database
    get("/orders/{ownerid}") {
        val owner = call.parameters["ownerid"].toString()
        val z = db.from(ordersentity).select().where(ordersentity.product_owner eq owner).map {
            val name = it[ordersentity.product_name]!!
            val type = it[ordersentity.product_type]!!
            val number = it[ordersentity.numberofproducts]!!
            val image = it[ordersentity.image]!!
            val fullname = it[ordersentity.full_name]!!
            val mobile  = it[ordersentity.mobile]!!
            val address = it[ordersentity.address]!!
            val state = it[ordersentity.state]!!
            val city = it[ordersentity.city]!!
            val postal = it[ordersentity.postal]!!
            ordersresponse(name,number,type,image,fullname,mobile,address,state,city,postal)
        }

        if (z.isEmpty()){
            call.respond(HttpStatusCode.BadRequest,replytoclient("no orders"))
        }else{
            call.respond(HttpStatusCode.OK,z)
        }
    }
}