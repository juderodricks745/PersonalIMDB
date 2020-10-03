package com.davidbronn.personalimdb.utils

import com.davidbronn.personalimdb.models.network.*

object TestUtil {

    const val MOVIE_ID = 423204
    const val PERSON_ID = 17276

    @JvmStatic
    fun createPerson(): Person {
        return Person().apply {
            adult = true
            alsoKnownAs = listOf(
                "傑拉德·巴特勒", "เจอราร์ด บัตเลอร์", "ジェラルド・バトラー", "제라드 버틀러",
                "Джерард Батлер", "جيرارد بتلر", "Gerard James Butler", "Τζεράρντ Τζέιμς Μπάτλερ",
                "Τζεράρντ Μπάτλερ"
            )
            birthday = "1969-11-13"
            knownForDepartment = "Acting"
            id = 17276
            name = "Gerard Butler"
            gender = 2
            biography =
                "Gerard James Butler (born 13 November 1969) is a Scottish actor who has appeared on film, stage, and television.\n" +
                        "\n" +
                        "A trained lawyer, Butler turned to acting in the mid-1990s with small roles in productions such as the James Bond film Tomorrow Never Dies (1997), which he followed with steady work on television, most notably in the American miniseries Attila (2001).\n" +
                        "\n" +
                        "He garnered critical acclaim for his breakthrough work as the lead in Joel Schumacher's 2004 film adaptation of the musical The Phantom of the Opera. In 2007, Butler gained worldwide recognition through his portrayal of King Leonidas in the film 300. Since then, he has appeared in projects including P.S. I Love You (2007), Nim's Island (2008), RocknRolla (2008), The Ugly Truth (2009), Gamer (2009), Law Abiding Citizen (2009), The Bounty Hunter (2010), and How to Train Your Dragon (2010). Description above from the Wikipedia article Gerard Butler, licensed under CC-BY-SA, full list of contributors on Wikipedia."
            popularity = 10.513
            placeOfBirth = "Paisley, Scotland, UK"
            profilePath = "/cAlwtnkOTtLAPjCQzMYbM6VA41p.jpg"
            imdbId = "nm0124930"
        }
    }

    @JvmStatic
    fun createMoviesDetail(): MovieDetails {
        return MovieDetails().apply {
            adult = true
            backdropPath = "/k2WyDw2NTUIWnuEs5gT7wgrCQg6.jpg"
            belongsToCollection = BelongsToCollection().apply {
                backdropPath = "/o9jZvL6pi7ur9k5cumSAQUAbmzw.jpg"
                name = "... Has Fallen Collection"
                id = 386534
                posterPath = "/25gcpe1vwzl2VwaAq5rgzHePoxo.jpg"
            }
            budget = 40000000
            genres =
                listOf(GenresItem(name = "Action", id = 28), GenresItem(name = "Thriller", id = 53))
            homepage = "https://angelhasfallen.movie"
            id = 423204
            imdbId = "tt6189022"
            originalLanguage = "en"
            originalTitle = "Angel Has Fallen"
            overview =
                "After a treacherous attack, Secret Service agent Mike Banning is charged with attempting to assassinate President Trumbull. Chased by his own colleagues and the FBI, Banning begins a race against the clock to clear his name."
            popularity = 106.383
            posterPath = "/fapXd3v9qTcNBTm39ZC4KUVQDNf.jpg"
            runtime = 122
            tagline = "Loyalty is under fire"
            title = "Angel Has Fallen"
        }
    }

    @JvmStatic
    fun createSimilarMovies(): List<MovieCastItem> {
        return listOf(
            MovieCastItem().apply {
                url = "/3NvOFpmyECI3DNExYMtFIRcGMsu.jpg"
                movieId = "9386"
                rating = "6.9"
                title = "In the Line of Fire"
            },
            MovieCastItem().apply {
                url = "/n7U0FEirHej1yQIy2mAzDtF9wBz.jpg"
                movieId = "66"
                rating = "6.6"
                title = "Absolute Power"
            }
        )
    }

    @JvmStatic
    fun createMoviesCast(): List<MovieCastItem> {
        return listOf(
            MovieCastItem().apply {
                url = "/cAlwtnkOTtLAPjCQzMYbM6VA41p.jpg"
                movieId = "581361039251414f9b02431f"
                rating = ""
                title = "Gerard Butler"
            },
            MovieCastItem().apply {
                url = "/oIciQWr8VwKoR8TmAw1owaiZFyb.jpg"
                movieId = "58cc01b39251415e44014dec"
                rating = ""
                title = "Morgan Freeman"
            }
        )
    }
}