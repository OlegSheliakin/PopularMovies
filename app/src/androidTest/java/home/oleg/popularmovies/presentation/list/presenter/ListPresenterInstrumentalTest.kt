package home.oleg.popularmovies.presentation.list.presenter

import home.oleg.RxSchedulesRule
import home.oleg.popularmovies.di.DaggerInstrumentalTestMovieAppComponent
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.list.ListView
import home.oleg.popularmovies.presentation.model.MovieViewModel
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class ListPresenterInstrumentalTest {

    @get:Rule
    val rxSchedulesRule = RxSchedulesRule()

    @Inject
    lateinit var mockWebserver: MockWebServer

    @Inject
    lateinit var presenter: ListPresenter

    private val view: ListView = mock(ListView::class.java)

    @Inject
    lateinit var movieList: List<MovieViewModel>

    @Before
    fun setUp() {
        DaggerInstrumentalTestMovieAppComponent.create().inject(this)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        mockWebserver.shutdown()
    }

    @Test
    fun fetchMoviesOnSuccess() {
        val testScheduler = rxSchedulesRule.initWithTestScheduler()

        presenter.fetchMovies(MovieRepository.Filter.TOP_RATED)
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        verify(view, times(1)).fillList(movieList)
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun fetchMoviesOnError() {
        presenter.fetchMovies(MovieRepository.Filter.POPULAR)

        verify(view, times(1)).fillList(movieList)
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun detachView() {
    }

}