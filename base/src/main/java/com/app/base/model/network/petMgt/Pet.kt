package com.app.base.model.network.petMgt

import android.os.Parcelable
import com.app.base.utils.Utils

import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pet(

    var id: String="",
    var name: String="",
    var born_date: String="",
    var user_id:String="",
    var picture_url: String="",
    var breed_id:String="",
    var breed: Breed = Breed(),
    var specie: Species=Species(),
    var mime_type:String=""

) : Parcelable {
    val _born_date: String
        get() = Utils.dateToStringFormat(born_date)
}