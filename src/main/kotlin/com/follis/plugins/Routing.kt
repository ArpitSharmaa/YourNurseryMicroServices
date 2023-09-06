package com.follis.plugins

import com.follis.plugins.Databaseconnection.aboutt
import com.follis.plugins.Databaseconnection.productss
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.mindrot.jbcrypt.BCrypt
import java.io.File
import java.util.*
val base_url = "http://192.168.29.199:8080"
fun Application.configureRouting(tokengnerateService: TokengnerateService,tokenConfig: TokenConfig) {
    val data = Databaseconnection.database
    routing {


        get("/products") {

            val respond = data.from(productentity).select().map {
                val about = data.from(aboutentity).select().where(aboutentity.productname eq it[productentity.productname]!!).map {
                    aboutreturn(it[aboutentity.details]!!)
                }

                Productresponse(it[productentity.productname],it[productentity.productdescription],it[productentity.productprice]
                ,it[productentity.image],it[productentity.image2],it[productentity.image3],it[productentity.image4],it[productentity.image5],about,it[productentity.ownerid]!!)
            }

            call.respond(respond)
        }
        get("/productsbyparam/{param}"){
            val id = call.parameters["param"].toString()
            val respond = data.from(productentity).select().where(productentity.productdescription eq id).map {
                val about = data.from(aboutentity).select().where(aboutentity.productname eq it[productentity.productname]!!).map {
                    aboutreturn(it[aboutentity.details]!!)
                }

                Productresponse(it[productentity.productname],it[productentity.productdescription],it[productentity.productprice]
                    ,it[productentity.image],it[productentity.image2],it[productentity.image3],it[productentity.image4],it[productentity.image5],about,it[productentity.ownerid]!!)
            }

            call.respond(respond)

        }
        post ("/enterproducts"){
//            val recieved = call.receive<Productrecieved1>()
            val part = call.receiveMultipart().readAllParts()
            var serverProcess:Process? = null
            var fileItem:PartData.FileItem
            val fileName : MutableList<String> = mutableListOf()
            var dat: Productrecieved1? = null
            part.forEach(){
                when(it){
                    is PartData.FileItem -> {
                        fileName.add("${UUID.randomUUID()}.${it.originalFileName?.substringAfterLast(".")}")
                        val fileBytes = it.streamProvider().readBytes()
                        File("src/main/resources/images/${fileName.get(fileName.size -1)}").writeBytes(fileBytes)
                    }
                    is PartData.FormItem-> {
                         val json = it.value
                        print(json)
                        dat = Gson().fromJson(json,Productrecieved1::class.java)
                        print(dat)
                    }

                    else -> {}
                }
            }
            val r = products{
                 this.productname= dat?.productname
                this.productdescription= dat?.productdescription
                this.productprice = dat?.productprice
                this.ownerid = dat?.ownerid.toString()
                if (fileName.size == 5) {
                    this.imageurl = "$base_url/images/${fileName.get(0)}"
                    this.imageurl2 = "$base_url/images/${fileName.get(1)}"
                    this.imageurl3 = "$base_url/images/${fileName.get(2)}"
                    this.imageurl4 = "$base_url/images/${fileName.get(3)}"
                    this.imageurl5 = "$base_url/images/${fileName.get(4)}"
                }else if (fileName.size ==4){
                    this.imageurl = "$base_url/images/${fileName.get(0)}"
                    this.imageurl2 = "$base_url/images/${fileName.get(1)}"
                    this.imageurl3 = "$base_url/images/${fileName.get(2)}"
                    this.imageurl4 = "$base_url/images/${fileName.get(3)}"
                }else if (fileName.size == 3){
                    this.imageurl = "$base_url/images/${fileName.get(0)}"
                    this.imageurl2 = "$base_url/images/${fileName.get(1)}"
                    this.imageurl3 = "$base_url/images/${fileName.get(2)}"
                }else if (fileName.size == 2){
                    this.imageurl = "$base_url/images/${fileName.get(0)}"
                    this.imageurl2 = "$base_url/images/${fileName.get(1)}"
                }else{
                    this.imageurl = "$base_url/images/${fileName.get(0)}"
                }

            }
            val b = about{
                this.details= dat?.about?.details
                this.productname = dat?.about?.productname
            }
            val z= data.productss.add(r)
            val y = data.aboutt.add(b)

            if (z == 1&& y == 1){


                call.respond(HttpStatusCode.OK,replytoclient(string = "Entered the data"))
            }else{
                call.respond(HttpStatusCode.BadRequest,replytoclient(string = "data was not added"))
            }
        }

        post("/register") {
            val recievd = call.receive<user>()
            val username = recievd.email.toLowerCase()
            val pass = recievd.encryptpass()
            val userid = username.hashCode()

            val existinguser = data.from(Userentity).select().where(Userentity.email eq username).map {
                it[Userentity.email]
            }.firstOrNull()

            if (existinguser == null){
                val z = data.insert(Userentity){
                    set(it.email,username)
                    set(it.password,pass)
                    set(it.userid,userid.toString())
                }
                if (z==1){
                    call.respond(HttpStatusCode.OK,replytoclient("user registerd"))
                }else{
                    call.respond(HttpStatusCode.BadRequest,replytoclient("not registered"))
                }
            }else{
                call.respond(HttpStatusCode.BadRequest,replytoclient("Client already registered"))
            }

        }

        post ("/login"){
            val rerc =call.receive<user>()
            val username = rerc.email
            val pass = rerc.password
             val check = data.from(Userentity).select().where(Userentity.email eq username).map {
                 val a =it[Userentity.email]!!
                val b=  it[Userentity.password]!!
                 val userid = it[Userentity.userid]!!
                 user2(a,b,userid)
             }.firstOrNull()
            if (check!=null){
                val checkpass = BCrypt.checkpw(pass,check.password)
                if (checkpass){
                    val token = tokengnerateService.generate(
                        config = tokenConfig,
                        tokenclaims(
                            name = "UserID",
                            value = check.userid
                        )
                    )

                    call.respond(HttpStatusCode.OK,replytoclient(token))
                }else{
                    call.respond(HttpStatusCode.Conflict)
                }

            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post ("/neworder"){
            val a = call.receive<List<ordersrecieved>>()
            var r = 0
            if (a.isEmpty()){
                call.respond(replytoclient("please place an order"))
            }else {
                for (z in a) {
                    val e = data.insert(ordersentity) {
                        set(it.product_name, z.productname)
                        set(it.product_type, z.productdescription)
                        set(it.product_owner, z.ownerid)
                        set(it.numberofproducts, z.numberofproduct)
                        set(it.image, z.image)
                        set(it.full_name,z.fullname)
                        set(it.email,z.email)
                        set(it.address,z.address)
                        set(it.mobile,z.mobile)
                        set(it.state,z.state)
                        set(it.city,z.city)
                        set(it.postal,z.postal)
                    }
                    r += e
                }
                if (r == a.size) {
                    call.respond(HttpStatusCode.OK, replytoclient("success"))
                } else {
                    call.respond(replytoclient("failed"))
                }
            }
        }
        auth()
        getsecret()
        neworders()
        staticResources("/images","images")

    }

}

