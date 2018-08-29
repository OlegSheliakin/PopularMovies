package home.oleg.popularmovies.data.mapper

import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class MovieResponseToMovieDbMapper @Inject constructor() : (MovieResponse.Result) -> MovieDbModel {

    var type: MovieRepository.Filter = MovieRepository.Filter.POPULAR

    override fun invoke(it: MovieResponse.Result): MovieDbModel {
        return MovieDbModel(
                id = it.id,
                adult = it.adult,
                type = type.value,
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

class MovieDbModelToMovieMapper @Inject constructor() : (MovieDbModel) -> Movie {
    override fun invoke(it: MovieDbModel): Movie {
        return Movie(
                id = it.id,
                adult = it.adult,
                isFavorite = it.isFavorite,
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
