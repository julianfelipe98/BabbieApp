package com.app.base.model.network.petMgt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breed(
    var id: String = "",
    var name: String = "",
    var specie: Species = Species()
) : Parcelable {
    override fun toString(): String {
        return this.name
    }
}