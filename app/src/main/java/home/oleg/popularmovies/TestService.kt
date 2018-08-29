package home.oleg.popularmovies

import android.content.Intent
import android.util.Log
import dagger.android.DaggerIntentService
import home.oleg.popularmovies.domain.MovieRepository
import io.reactivex.Observable
import java.util.concurrent.*
import javax.inject.Inject

class TestService : DaggerIntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        Observable.interval(5000, TimeUnit.MILLISECONDS).flatMap {
            movieRepository.getMovies(MovieRepository.Filter.TOP_RATED)

        }
                .blockingSubscribe({
                }, {
                    it.printStackTrace()
                })
    }

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Service", "destory service")
    }
}