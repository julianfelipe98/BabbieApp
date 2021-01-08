package com.app.base.model.network.store

import com.app.base.model.database.Item
import com.app.base.utils.Utils

import java.util.*

data class OrderNetwork(
    var id: Int,
    var item: Item,
    var quantity: Int = 1,
    var price: Double,
    var date: String
) {
    val _date: Date
        get() = Utils.stringToDate(date)
}