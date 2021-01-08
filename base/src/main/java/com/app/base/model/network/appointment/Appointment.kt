package com.app.base.model.network.appointment

import android.os.Parcelable
import com.app.base.model.network.petMgt.Pet
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.AppointmentStatus
import com.app.base.utils.Utils
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Appointment(

    var id: String,
    var initial_date: String,
    var final_date: String,
    var status: String,
    var service: VetService,
    var vetstore: StoreNetwork,
    var pet: Pet

) : Parcelable {

    val _initial_date: Date
        get() = Utils.stringToDate(initial_date)

    val _status: AppointmentStatus
        get() = if (status == AppointmentStatus.CANCEL.value) AppointmentStatus.CANCEL else AppointmentStatus.ACTIVE
}