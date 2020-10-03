package com.davidbronn.personalimdb.models.network

import com.google.gson.annotations.SerializedName

data class MovieDetails(

    @field:SerializedName("original_language")
    var originalLanguage: String? = null,

    @field:SerializedName("imdb_id")
    var imdbId: String? = null,

    @field:SerializedName("video")
    var video: Boolean? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @field:SerializedName("revenue")
    var revenue: Int? = null,

    @field:SerializedName("genres")
    var genres: List<GenresItem?>? = null,

    @field:SerializedName("popularity")
    var popularity: Double? = null,

    @field:SerializedName("production_countries")
    var productionCountries: List<ProductionCountriesItem?>? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("vote_count")
    var voteCount: Int? = null,

    @field:SerializedName("budget")
    var budget: Int? = null,

    @field:SerializedName("overview")
    var overview: String? = null,

    @field:SerializedName("original_title")
    var originalTitle: String? = null,

    @field:SerializedName("runtime")
    var runtime: Int? = null,

    @field:SerializedName("poster_path")
    var posterPath: String? = null,

    @field:SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguagesItem?>? = null,

    @field:SerializedName("production_companies")
    var productionCompanies: List<ProductionCompaniesItem?>? = null,

    @field:SerializedName("release_date")
    var releaseDate: String? = null,

    @field:SerializedName("vote_average")
    var voteAverage: Double? = null,

    @field:SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection? = null,

    @field:SerializedName("tagline")
    var tagline: String? = null,

    @field:SerializedName("adult")
    var adult: Boolean? = null,

    @field:SerializedName("homepage")
    var homepage: String? = null,

    @field:SerializedName("status")
    var status: String? = null
) {
    val movieRuntime: String
        get() = runtime.toString() + " mins"

    val showTagLine: Boolean
        get() = !tagline.isNullOrBlank()

    val genresAsString: String
        get() = genres?.map { it?.name }?.joinToString(", ") ?: ""
}

data class BelongsToCollection(

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("poster_path")
    var posterPath: String? = null
)

data class GenresItem(

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null
)

data class ProductionCompaniesItem(

    @field:SerializedName("logo_path")
    var logoPath: Any? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("origin_country")
    var originCountry: String? = null
)

data class ProductionCountriesItem(

    @field:SerializedName("iso_3166_1")
    var iso31661: String? = null,

    @field:SerializedName("name")
    var name: String? = null
)

data class SpokenLanguagesItem(

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("iso_639_1")
    var iso6391: String? = null
)