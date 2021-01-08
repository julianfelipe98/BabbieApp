package com.app.base.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var picture_url: String = ""
)