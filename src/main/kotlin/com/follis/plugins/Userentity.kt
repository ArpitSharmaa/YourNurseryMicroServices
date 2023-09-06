package com.follis.plugins

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object Userentity:Table<Nothing>("registereduser") {
    val email = text("email")
    val id = int("id").primaryKey()
    val password =  text("passwaord")
    val userid = text("userid")
}