package home.oleg.popularmovies.presentation.list.presenter

import DaggerTestMovieAppComponent
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import home.oleg.RxSchedulesRule
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.list.ListView
import home.oleg.popularmovies.presentation.model.MovieViewModel
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class ListPresenterTestWithDagger {

    @get:Rule
    val rxSchedulesRule = RxSchedulesRule()

    @Inject
    lateinit var mockWebserver: MockWebServer

    @Inject
    lateinit var presenter: ListPresenter

    private val view: ListView = mock()

    @Inject
    lateinit var movieList: List<MovieViewModel>

    @Before
    fun setUp() {
        DaggerTestMovieAppComponent.create().inject(this)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        mockWebserver.shutdown()
    }

    @Test
    fun fetchMoviesTopRated() {
        val testScheduler = rxSchedulesRule.initWithTestScheduler()

        presenter.fetchMovies(MovieRepository.Filter.TOP_RATED)
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        argumentCaptor<List<MovieViewModel>>().run {
            verify(view, times(1)).fillList(capture())
            Assert.assertThat(firstValue.size, CoreMatchers.`is`(CoreMatchers.equalTo(movieList.size)))
        }

        verify(view, times(1)).fillList(movieList)
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun fetchMoviesPopular() {
        presenter.fetchMovies(MovieRepository.Filter.POPULAR)

        verify(view, times(1)).fillList(movieList)
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun fetchMoviesFavourite() {
        presenter.fetchMovies(MovieRepository.Filter.FAVOURITE)

        verify(view, times(1)).fillList(emptyList())
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

}