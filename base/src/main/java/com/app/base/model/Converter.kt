package com.app.base.model

import android.text.TextUtils
import androidx.room.TypeConverter
import com.app.base.model.database.Item
import com.app.base.model.database.Product
import com.app.base.model.database.Store
import com.google.gson.Gson

class ItemConverter {

    @TypeConverter
    fun stringToItem(string: String): Product? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Product::class.java)
    }

    @TypeConverter
    fun itemToString(product: Product): String? {
        return Gson().toJson(product)
    }
}


class StoreConverter {

    @TypeConverter
    fun stringToStore(string: String): Store? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Store::class.java)

    }

    @TypeConverter
    fun storeToString(store: Store): String? {
        return Gson().toJson(store)
    }
}

class ItemStoreConverter {

    @TypeConverter
    fun stringToItemStore(string: String): Item? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Item::class.java)
    }

    @TypeConverter
    fun storeToItemString(store: Item): String? {
        return Gson().toJson(store)
    }
}