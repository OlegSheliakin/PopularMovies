package home.oleg.popularmovies.domain

import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by olegshelyakin on 07.08.17.
 */

interface MovieRepository {
    fun getMovies(filter: Filter): Observable<List<Movie>>
    fun saveAll(models: List<Movie>)
    fun delete(id: Long)

    enum class Filter private constructor(val value: String) {
        TOP_RATED("top_rated"),
        POPULAR("popular"),
        FAVOURITE("favourite");


        companion object {
            fun resolve(filter: String): Filter {
                return when (filter) {
                    "top_rated" -> TOP_RATED
                    "popular" -> POPULAR
                    "favourite" -> FAVOURITE
                    else -> POPULAR
                }
            }
        }
    }
}
