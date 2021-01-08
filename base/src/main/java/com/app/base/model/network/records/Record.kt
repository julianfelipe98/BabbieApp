package com.app.base.model.network.records

import com.app.base.model.network.petMgt.Pet
import com.app.base.utils.Utils

data class Record(

    var id: String = "",
    var title: String = "",
    var description: String = "",
    var start_date: String = "",
    var next_date: String = "",
    var vetstore_id: String = "",
    var pet: Pet = Pet(),
    var pet_id: String = "",
    var record_type_id: String = "",
    var recordType: RecordType = RecordType()

) {

    val _start_date: String
        get() = Utils.dateToStringFormat(start_date)

    val _next_date: String
        get() = Utils.dateToStringFormat(next_date)

}