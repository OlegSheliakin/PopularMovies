package home.oleg.popularmovies.data

import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.network.MovieApi
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.*
import javax.inject.Inject

/**
 * Created by olegshelyakin on 07.08.17.
 */

class MovieRepositoryImpl @Inject constructor(
        private val movieApi: MovieApi,
        private val movieDao: MovieDao) : MovieRepository {

    override fun saveAll(models: List<Movie>) {
        val list = models.map(MovieDbModel.MovieDbModelMapper)
        return movieDao.insert(list)
    }

    override fun getMovies(filter: MovieRepository.Filter): Observable<List<Movie>> {
        val localSource = movieDao.getAll(filter.value)
                .toObservable()
                .take(1)

        val remoteSource = movieApi.getMovies(filter.value)
                .map { it.results }
                .map { it.map(MovieResponse.Mapper) }


        return if (filter == MovieRepository.Filter.FAVOURITE) {
            localSource
        } else {
            Observable.mergeDelayError(localSource, remoteSource)
                    .debounce(300, TimeUnit.MILLISECONDS)

        }.map { it.map(MovieDbModel.MovieMapper) }
    }

    override fun delete(id: Long) {
        return movieDao.deleteById(id)
    }

}
