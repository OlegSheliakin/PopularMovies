package home.oleg.popularmovies.data.network

import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.entities.VideosResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface MovieApi {

    @GET(value = "movie/{path}")
    fun getMovies(@Path(value = "path") path: String): Observable<MovieResponse>

    @GET(value = "movie/{id}/reviews")
    fun getReviews(@Path(value = "id") id: Long): Observable<ReviewResponse>

    @GET(value = "movie/{id}/videos")
    fun getVideos(@Path(value = "id") id: Long): Observable<VideosResponse>
}
