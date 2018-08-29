package home.oleg.popularmovies.data.network

import DaggerTestMovieAppComponent
import home.oleg.RxSchedulesRule
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.domain.MovieRepository
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

class MovieApiTest {

    @get:Rule
    val rxSchedulesRule = RxSchedulesRule()

    @Inject
    lateinit var mockWebserver: MockWebServer

    @Inject
    lateinit var movieApi: MovieApi

    @Inject
    lateinit var results: List<MovieResponse.Result>

    @Before
    fun setUp() {
        DaggerTestMovieAppComponent.create().inject(this)
       // mockWebserver.start()
    }

    @Test
    fun shouldReturnPopular() {
        movieApi.getMovies(MovieRepository.Filter.POPULAR.value)
                .map { it.results }
                .test()
                .assertResult(results)
    }

    @After
    fun tearDown() {
        mockWebserver.shutdown()
    }

}
