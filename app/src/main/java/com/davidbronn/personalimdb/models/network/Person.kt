package com.davidbronn.personalimdb.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(

    @field:SerializedName("birthday")
    var birthday: String? = null,

    @field:SerializedName("also_known_as")
    var alsoKnownAs: List<String?>? = null,

    @field:SerializedName("gender")
    var gender: Int? = null,

    @field:SerializedName("imdb_id")
    var imdbId: String? = null,

    @field:SerializedName("known_for_department")
    var knownForDepartment: String? = null,

    @field:SerializedName("profile_path")
    var profilePath: String? = null,

    @field:SerializedName("biography")
    var biography: String? = null,

    @field:SerializedName("deathday")
    var deathday: String? = null,

    @field:SerializedName("place_of_birth")
    var placeOfBirth: String? = null,

    @field:SerializedName("popularity")
    var popularity: Double? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("adult")
    var adult: Boolean? = null,

    @field:SerializedName("homepage")
    var homepage: String? = null
) : Parcelable {

    val aka: String
        get() = alsoKnownAs?.joinToString(", ") ?: ""

    val isAkaAvailable: Boolean
        get() = !alsoKnownAs.isNullOrEmpty()
}
