package home.oleg.popularmovies.domain.usecases

import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Oleg on 15.04.2017.
 */

class GetMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun execute(filter: MovieRepository.Filter): Observable<List<Movie>> {
        return movieRepository.getMovies(filter)
    }

}


