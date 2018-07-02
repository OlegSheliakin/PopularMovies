package home.oleg.popularmovies.data

import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.domain.FavoriteMovieRepository
import io.reactivex.Completable
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
        private val movieDao: MovieDao)
    : FavoriteMovieRepository {

    override fun addFavorite(id: Long): Completable {
        return mark(id, true)
    }

    override fun removeFavorite(id: Long): Completable {
        return mark(id, false)
    }

    private fun mark(id: Long, isFavorite: Boolean): Completable {
        return movieDao.get(id)
                .map { it.copy(isFavorite = isFavorite) }
                .doOnSuccess { movieDao.insert(it) }
                .toCompletable()
    }

}