package home.oleg.popularmovies.presentation.list.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import home.oleg.RxSchedulesRule
import home.oleg.popularmovies.data.MovieRepositoryImpl
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.mapper.MovieDbModelToMovieMapper
import home.oleg.popularmovies.data.mapper.MovieResponseToMovieDbMapper
import home.oleg.popularmovies.data.network.MovieApi
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.list.ListView
import home.oleg.popularmovies.presentation.mappers.MovieToMovieViewModelMapper
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.*

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class ListPresenterTest {

    @get:Rule
    val rxSchedulesRule = RxSchedulesRule()

    private lateinit var presenter: ListPresenter

    private val view: ListView = mock()

    private val movieDao: MovieDao = mock()
    private val movieApi: MovieApi = mock()

    private val movieRepository = MovieRepositoryImpl(
            movieApi,
            movieDao,
            MovieDbModelToMovieMapper(),
            MovieResponseToMovieDbMapper())

    @Before
    fun setUp() {
        presenter = ListPresenter(CompositeDisposable(), MovieToMovieViewModelMapper(), movieRepository)
        presenter.attachView(view)
    }

    @Test
    fun fetchMovies() {
        val testScheduler = rxSchedulesRule.initWithTestScheduler()

        whenever(movieApi.getMovies(any())).thenReturn(
                Observable.just(MovieResponse()).delay(500, TimeUnit.MILLISECONDS))
        whenever(movieDao.getAll(any())).thenReturn(Flowable.just(listOf()))

        presenter.fetchMovies(MovieRepository.Filter.TOP_RATED)
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        verify(view, times(1)).fillList(listOf())
    }

    @Test
    fun detachView() {
    }

}