package home.oleg.popularmovies.data.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import home.oleg.popularmovies.domain.entities.Movie

@Entity(tableName = "movies")
data class MovieDbModel(
        @PrimaryKey
        val id: Long,
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
        val voteAverage: Double? = null,
        val type: String? = null,
        val isFavorite: Boolean = false)
