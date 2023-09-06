package com.follis.plugins

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.text

object ordersentity:Table<Nothing>("orders") {
    val id = int("id").primaryKey()
    val product_name = text("product_name")
    val product_type = text("product_type")
    val image = text("image")
    val product_owner = text("product_owner")
    val numberofproducts = long("numberofproducts")
    val full_name = text("full_name")
    val email = text("email")
    val postal = text("postal")
    val mobile = text("mobile")
    val city =  text("city")
    val state = text("state")
    val address  = text("address")
}