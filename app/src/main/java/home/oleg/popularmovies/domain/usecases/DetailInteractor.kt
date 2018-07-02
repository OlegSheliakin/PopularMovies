package home.oleg.popularmovies.domain.usecases

import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.entities.VideosResponse
import home.oleg.popularmovies.domain.FavoriteMovieRepository
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

class DetailInteractor @Inject constructor(
        private val getReviewsUseCase: GetReviewsUseCase,
        private val getTrailersUseCase: GetTrailersUseCase,
        private val favoriteMovieRepository: FavoriteMovieRepository) {

    fun fetchReviews(id: Long): Observable<List<ReviewResponse.Result>> {
        return getReviewsUseCase.execute(id)
    }

    fun fetchTrailers(id: Long): Observable<List<VideosResponse.Result>> {
        return getTrailersUseCase.execute(id)
    }

    fun addToFavourite(id: Long): Completable {
        return favoriteMovieRepository.addFavorite(id)
    }

    fun deleteFromFavourite(id: Long): Completable {
        return favoriteMovieRepository.removeFavorite(id)
    }

}
