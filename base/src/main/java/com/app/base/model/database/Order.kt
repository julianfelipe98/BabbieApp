package com.app.base.model.database

import androidx.room.*
import com.app.base.model.ItemStoreConverter

@Entity(tableName = "order", indices = arrayOf(Index(value = ["item"], unique = true)))
data class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @TypeConverters(ItemStoreConverter::class)
    var item: Item,
    var quantity: Int = 1,
    var price: Double

) {
    @Ignore
    var _totalValue: Double = 0.0
        get() = if (quantity <= 0.0) 0.0 else price * quantity

}