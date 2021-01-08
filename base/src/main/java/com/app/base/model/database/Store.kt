package com.app.base.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class Store(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var phone_number: String = "",
    var email: String = "",
    var picture_url: String = ""
)