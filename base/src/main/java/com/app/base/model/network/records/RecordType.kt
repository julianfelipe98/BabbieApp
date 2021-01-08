package com.app.base.model.network.records

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordType(
    var id: String = "",
    var name: String = "",
    var tag: String = ""
) : Parcelable
