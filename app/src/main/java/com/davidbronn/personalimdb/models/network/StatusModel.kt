package com.davidbronn.personalimdb.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusModel(
    @SerializedName("status_code")
    var statusCode: Int = 0,
    @SerializedName("status_message")
    var statusMessage: String = "",
    @SerializedName("success")
    var success: Boolean = false
) : Parcelable