package home.oleg.popularmovies.domain.entities

data class Movie(
        val id: Long,
        val isFavorite: Boolean = false,
        val posterPath: String? = null,
        val adult: Boolean? = null,
        val overview: String? = null,
        val releaseDate: String? = null,
        val originalTitle: String? = null,
        val originalLanguage: String? = null,
        val title: String? = null,
        val backdropPath: String? = null,
        val popularity: Double? = null,
        val voteCount: Int? = null,
        val video: Boolean? = null,
        val voteAverage: Double? = null
)