package com.follis.plugins

import kotlinx.serialization.Serializable
import org.ktorm.entity.Entity
import org.mindrot.jbcrypt.BCrypt


interface products:Entity<products>{
    companion object : Entity.Factory<products>()
    val id :Int
    var productname :String?
    var productdescription :String?
    var productprice :Long?

    var imageurl : String?
    var imageurl2 : String?
    var imageurl3 : String?
    var imageurl4 : String?
    var imageurl5 : String?
    var ownerid :String




}
interface about:Entity<about>{
    companion object : Entity.Factory<about>()
    var details : String?
    var productname: String?

}

@Serializable
data class Productrecieved1(
    val productname :String?,
    val productdescription :String?,
    val productprice :Long?,
    val about: aboutsec1,
    val ownerid :String



)
@Serializable
data class Productresponse(
    val productname:String?,
    val productdescription:String?,
    val productprice:Long?,
    val imageurl:String?,
    val imageurl2:String?,
    val imageurl3:String?,
    val imageurl4:String?,
    val imageurl5:String?,
    val about: List<aboutreturn>,
    val ownerid: String



    )
@Serializable
data class aboutreturn(
    val details: String
)

@Serializable
data class aboutsec1(
    val details: String,
    val productname: String?
)
@Serializable
data class replytoclient(
    val string: String
)
@Serializable
data class user(
    val email:String,
    val password :String

){
    fun encryptpass():String{
        return BCrypt.hashpw(password,BCrypt.gensalt())
    }
}
@Serializable
data class user2(
    val email:String,
    val password :String,
    val userid:String

)
@Serializable
data class ordersrecieved(
    val productname: String,
    var numberofproduct : Long,
    val productdescription : String,
    val image:String,
    val ownerid:String,
    val email:String,
    val fullname :String,
    val mobile :String,
    val address : String,
    val state : String,
    val city : String,
    val postal : String
)
@Serializable
data class ordersresponse(
    val productname: String,
    var numberofproduct : Long,
    val productdescription : String,
    val image:String,
    val fullname :String,
    val mobile :String,
    val address : String,
    val state : String,
    val city : String,
    val postal : String

)