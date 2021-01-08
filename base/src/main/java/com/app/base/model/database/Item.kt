package com.app.base.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.base.model.ItemConverter
import com.app.base.model.StoreConverter

@Entity(tableName = "item")
data class Item(
    @PrimaryKey
    var id: String,
    var price: Double,

    @TypeConverters(ItemConverter::class)
    var product: Product,

    @TypeConverters(StoreConverter::class)
    var vetstore: Store
)