package com.app.base.model.network.petMgt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Species(
    var id: String="",
    var name: String=""
) : Parcelable {
    override fun toString(): String {
       return this.name
    }
}