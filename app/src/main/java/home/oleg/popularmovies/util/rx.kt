package home.oleg.popularmovies.util

import io.reactivex.Observable
import java.util.concurrent.*

fun <T> Observable<T>.delayError(longDelay: Long, unit: TimeUnit = TimeUnit.MILLISECONDS): Observable<T> {
    return onErrorResumeNext { throwable: Throwable ->
        Observable.error<T>(throwable)
                .delay(longDelay, unit, true)
    }
}

