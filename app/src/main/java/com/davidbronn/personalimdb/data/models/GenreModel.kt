package com.davidbronn.personalimdb.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreModel(
    @SerializedName("genres")
    var genres: List<Genre> = listOf()
) : Parcelable {
    @Parcelize
    data class Genre(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        var name: String = ""
    ) : Parcelable
}