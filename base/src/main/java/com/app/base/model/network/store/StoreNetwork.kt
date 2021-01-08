package com.app.base.model.network.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreNetwork(

    var id: String = "",
    var name: String = "",
    var address: String = "",
    var phone_number: String = "",
    var email: String = "",
    var picture_url: String = ""

) : Parcelable