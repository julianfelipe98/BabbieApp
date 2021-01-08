package com.app.base.model.network.appointment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(

    var id: String,
    var name: String,
    var color: String

) : Parcelable
