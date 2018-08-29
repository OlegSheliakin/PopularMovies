package home.oleg.popularmovies.data

import android.util.Log
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.mapper.MovieDbModelToMovieMapper
import home.oleg.popularmovies.data.mapper.MovieResponseToMovieDbMapper
import home.oleg.popularmovies.data.network.MovieApi
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import home.oleg.popularmovies.util.delayError
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import java.sql.Time
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
        return if (filter == MovieRepository.Filter.FAVOURITE) {
            movieDao.getFavourites().toObservable()
        } else {
            loadMovie(filter)
        }.map { it.map(toMovieMapper) }
    }

    private fun loadMovie(filter: MovieRepository.Filter): Observable<List<MovieDbModel>> {
        val localSource = localSource(filter)
        val remoteSource = remoteSource(filter)
                .delayError(400)

        return Observable.mergeDelayError(localSource, remoteSource)
                .debounce(300, TimeUnit.MILLISECONDS)
    }

    private fun localSource(filter: MovieRepository.Filter): Observable<List<MovieDbModel>> {
        return movieDao.getAllByType(filter.value)
                .take(1)
                .toObservable()
    }

    private fun remoteSource(filter: MovieRepository.Filter): Observable<List<MovieDbModel>> {
        return movieApi.getMovies(filter.value)
                .map(MovieResponse::results)
                .map { it.sortedBy { it.id } }
                .map {
                    toMovieDbMapper.type = filter
                    it.map(toMovieDbMapper)
                }
                .doOnNext(movieDao::insert)
    }

}
