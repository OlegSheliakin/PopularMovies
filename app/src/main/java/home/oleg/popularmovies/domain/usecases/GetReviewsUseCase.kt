package home.oleg.popularmovies.domain.usecases

import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.network.MovieApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

class GetReviewsUseCase @Inject constructor(private val movieApi: MovieApi) {
    fun execute(id: Long): Observable<List<ReviewResponse.Result>> {
        return movieApi.getReviews(id).map(ReviewResponse::results)
    }

}
