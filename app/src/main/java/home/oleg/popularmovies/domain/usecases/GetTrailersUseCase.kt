package home.oleg.popularmovies.domain.usecases

import home.oleg.popularmovies.data.entities.VideosResponse
import home.oleg.popularmovies.data.network.MovieApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

class GetTrailersUseCase @Inject constructor(private val movieApi: MovieApi) {

    fun execute(id: Long): Observable<List<VideosResponse.Result>> {
        return movieApi.getVideos(id).map { it.results }
    }

}
