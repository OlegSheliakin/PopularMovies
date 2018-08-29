package home.oleg.popularmovies.presentation.mappers

import home.oleg.popularmovies.domain.entities.Movie
import home.oleg.popularmovies.presentation.model.MovieViewModel
import javax.inject.Inject

class MovieToMovieViewModelMapper @Inject constructor() : (Movie) -> MovieViewModel {
    override fun invoke(it: Movie): MovieViewModel {
        return MovieViewModel(
                id = it.id,
                originalTitle = it.originalTitle,
                imagePath = it.posterPath,
                isFavourite = it.isFavorite,
                plotSynopsis = it.overview,
                releaseAt = it.releaseDate,
                userRating = it.voteAverage)
    }

}

