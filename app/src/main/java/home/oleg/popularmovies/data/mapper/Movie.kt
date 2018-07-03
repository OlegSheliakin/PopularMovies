package home.oleg.popularmovies.data.mapper

import com.beender.android.di.scope.PerApplication
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.domain.entities.Movie
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@PerApplication
class MovieResponseToMovieDbMapper @Inject constructor() : (MovieResponse.Result) -> MovieDbModel {
    override fun invoke(it: MovieResponse.Result): MovieDbModel {
        return MovieDbModel(
                id = it.id,
                adult = it.adult,
                backdropPath = it.backdropPath,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount)
    }
}

@PerApplication
class MovieDbModelToMovieMapper @Inject constructor() : (MovieDbModel) -> Movie {
    override fun invoke(it: MovieDbModel): Movie {
        return Movie(
                id = it.id,
                adult = it.adult,
                backdropPath = it.backdropPath,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount)
    }
}
