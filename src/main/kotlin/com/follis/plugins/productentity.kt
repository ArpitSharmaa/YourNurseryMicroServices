package com.follis.plugins


import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.text

object productentity : org.ktorm.schema.Table<products>("products") {
    val id = int("id").primaryKey()

    val productname = text("productname").bindTo { it.productname }
    val productdescription = text("productdescription").bindTo { it.productdescription }
    val productprice = long("productprice").bindTo { it.productprice }
    val ownerid = text("ownerid").bindTo { it.ownerid }
    val image = text("imageurl").bindTo { it.imageurl }
    val image2 = text("imageurl2").bindTo { it.imageurl2 }
    val image3 = text("imageurl3").bindTo { it.imageurl3 }
    val image4 = text("imageurl4").bindTo { it.imageurl4 }
    val image5 = text("imageurl5").bindTo { it.imageurl5 }


}