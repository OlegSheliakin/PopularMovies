package home.oleg.popularmovies.data.entities

import com.google.gson.annotations.SerializedName
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.domain.entities.Movie

data class MovieResponse(
        val page: Int? = null,
        val results: List<Result> = emptyList(),
        val totalResults: Int? = null,
        val totalPages: Int? = null) {

    data class Result(

            @SerializedName(value = "poster_path")
            val posterPath: String? = null,

            @SerializedName(value = "adult")
            val adult: Boolean? = null,

            @SerializedName(value = "overview")
            val overview: String? = null,

            @SerializedName(value = "release_date")
            val releaseDate: String? = null,

            @SerializedName(value = "id")
            val id: Long,

            @SerializedName(value = "original_title")
            val originalTitle: String? = null,

            @SerializedName(value = "original_language")
            val originalLanguage: String? = null,

            @SerializedName(value = "title")
            val title: String? = null,

            @SerializedName(value = "backdrop_path")
            val backdropPath: String? = null,

            @SerializedName(value = "popularity")
            val popularity: Double? = null,

            @SerializedName(value = "vote_count")
            val voteCount: Int? = null,

            @SerializedName(value = "video")
            val video: Boolean? = null,

            @SerializedName(value = "vote_average")
            val voteAverage: Double? = null
    )

}
