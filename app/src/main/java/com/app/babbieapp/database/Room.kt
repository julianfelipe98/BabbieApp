package com.app.babbieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.base.database.CartDao
import com.app.base.model.ItemConverter
import com.app.base.model.ItemStoreConverter
import com.app.base.model.StoreConverter
import com.app.base.model.database.Item
import com.app.base.model.database.Order
import com.app.base.model.database.Product
import com.app.base.model.database.Store

@Database(
    entities = [Product::class, Store::class, Item::class, Order::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ItemConverter::class, StoreConverter::class, ItemStoreConverter::class)
abstract class CartDatabase : RoomDatabase() {
    abstract val cartDao: CartDao
}
