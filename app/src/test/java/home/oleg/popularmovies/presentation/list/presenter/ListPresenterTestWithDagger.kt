package home.oleg.popularmovies.presentation.list.presenter

import com.nhaarman.mockitokotlin2.*
import home.oleg.RxSchedulesRule
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.network.MovieApi
import home.oleg.popularmovies.di.DaggerTestMovieAppComponent
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.list.ListView
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    lateinit var presenter: ListPresenter

    private val view: ListView = mock()

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        DaggerTestMovieAppComponent.create().inject(this)
        presenter.attachView(view)
    }

    @Test
    fun fetchMoviesOnSuccess() {
        val testScheduler = rxSchedulesRule.initWithTestScheduler()

        whenever(movieApi.getMovies(any())).thenReturn(
                Observable.just(MovieResponse())
                        .delay(500, TimeUnit.MILLISECONDS))
        whenever(movieDao.getAll(any())).thenReturn(Flowable.just(listOf()))

        presenter.fetchMovies(MovieRepository.Filter.TOP_RATED)
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        verify(view, times(1)).fillList(listOf())
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun fetchMoviesOnError() {
        whenever(movieApi.getMovies(any())).thenReturn(Observable.error(Exception()))
        whenever(movieDao.getAll(any())).thenReturn(Flowable.error(Exception()))

        presenter.fetchMovies(MovieRepository.Filter.TOP_RATED)

        verify(view, times(1)).showError()
        verify(view, times(1)).showLoading()
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun detachView() {
    }

}