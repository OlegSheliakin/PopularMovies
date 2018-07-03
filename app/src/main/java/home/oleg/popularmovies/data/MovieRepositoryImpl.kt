package home.oleg.popularmovies.data

import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.mapper.MovieDbModelToMovieMapper
import home.oleg.popularmovies.data.mapper.MovieResponseToMovieDbMapper
import home.oleg.popularmovies.data.network.MovieApi
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by olegshelyakin on 07.08.17.
 */

class MovieRepositoryImpl @Inject constructor(
        private val movieApi: MovieApi,
        private val movieDao: MovieDao,
        private val toMovieMapper: MovieDbModelToMovieMapper,
        private val toMovieDbMapper: MovieResponseToMovieDbMapper) : MovieRepository {

    override fun getMovies(filter: MovieRepository.Filter): Observable<List<Movie>> {
        val localSource = movieDao.getAll(filter.value)
                .toObservable()
                .take(1)

        val remoteSource = movieApi.getMovies(filter.value)
                .map { it.results }
                .map { it.map(toMovieDbMapper) }
                .doOnNext { movieDao.insert(it) }

        return if (filter == MovieRepository.Filter.FAVOURITE) {
            localSource
        } else {
            Observable.mergeDelayError(localSource, remoteSource)
                    .debounce(300, TimeUnit.MILLISECONDS)

        }.map { it.map(toMovieMapper) }
    }

}
