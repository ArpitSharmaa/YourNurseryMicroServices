package com.follis.plugins

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf

object Databaseconnection {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:5555/yournursery",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )
    val Database.productss get() = this.sequenceOf(productentity)
    val Database.aboutt get() = this.sequenceOf(aboutentity)

}