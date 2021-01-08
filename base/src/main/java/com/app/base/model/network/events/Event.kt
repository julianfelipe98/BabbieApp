package com.app.base.model.network.events

import com.app.base.utils.Utils
import java.util.*

data class Event(

    var id: String = "",
    var title: String = "",
    var body: String = "",
    var imageUrl: String = "",
    var date: String = ""
) {
    val _date: Date
        get() = Utils.stringToDate(date)
}