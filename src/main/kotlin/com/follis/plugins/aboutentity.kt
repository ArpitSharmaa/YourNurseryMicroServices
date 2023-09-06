package com.follis.plugins

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object aboutentity:Table<about>("about") {
    val id = int("id").primaryKey()
    val details = text("details").bindTo { it.details }
    val productname = text("productname").bindTo { it.productname }
}