package home.oleg.popularmovies.data.database

import android.arch.core.executor.testing.InstantTaskExecutorRule
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.di.DaggerInstrumentalTestMovieAppComponent
import home.oleg.popularmovies.domain.MovieRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


/**
 * Created by Oleg Sheliakin on 10.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var movieDatabase: MovieDatabase

    @Before
    fun setUp() {
        DaggerInstrumentalTestMovieAppComponent.create().inject(this)
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        movieDatabase.close()
    }

    @Test
    fun get_shoudEmitMovie() {
        val movie = MovieDbModel(0)
        movieDao.insert(movie)

        movieDao.get(0)
                .test()
                .assertResult(movie)
    }

    @Test
    fun get_shoudEmitError() {
        val movie = MovieDbModel(0)
        movieDao.insert(movie)

        movieDao.get(1)
                .test()
                .assertFailure(Exception::class.java)
    }

    @Test
    fun update_shouldReplaceOldOne() {
        val oldMovie = MovieDbModel(0)
        val newMovie = MovieDbModel(0, posterPath = "url")

        movieDao.insert(oldMovie)
        movieDao.update(newMovie)

        movieDao.get(0)
                .test()
                .assertResult(newMovie)
    }

    @Test
    fun insert_shouldIgnoreWhenExist() {
        val oldMovie = MovieDbModel(0)
        val newMovie = MovieDbModel(0, posterPath = "url")

        movieDao.insert(oldMovie)
        movieDao.insert(newMovie)

        movieDao.get(0)
                .test()
                .assertResult(oldMovie)
    }

    @Test
    fun deleteById() {
        val movie = MovieDbModel(0)

        movieDao.insert(movie)
        movieDao.deleteById(movie.id)

        movieDao.get(movie.id)
                .test()
                .assertNoValues()
    }

    @Test
    fun getAll_shouldEmitPopularMovies() {
        val movies = listOf(
                MovieDbModel(0, type = MovieRepository.Filter.POPULAR.value),
                MovieDbModel(1, type = MovieRepository.Filter.TOP_RATED.value))

        val expectedMovies = listOf(MovieDbModel(0, type = MovieRepository.Filter.POPULAR.value))

        movieDao.insert(movies)

        movieDao.getAllByType(MovieRepository.Filter.POPULAR.value)
                .take(1)
                .test()
                .assertResult(expectedMovies)
    }

    @Test
    fun getAll_shouldEmitEmptyList() {
        val movies = listOf(
                MovieDbModel(0, type = MovieRepository.Filter.POPULAR.value),
                MovieDbModel(1, type = MovieRepository.Filter.FAVOURITE.value))

        movieDao.insert(movies)

        movieDao.getAllByType(MovieRepository.Filter.TOP_RATED.value)
                .take(1)
                .test()
                .assertResult(emptyList())
    }
}