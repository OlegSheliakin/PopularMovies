package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.mapper.MovieDbModelToMovieMapper
import home.oleg.popularmovies.data.mapper.MovieResponseToMovieDbMapper
import home.oleg.popularmovies.presentation.mappers.MovieToMovieViewModelMapper
import home.oleg.popularmovies.presentation.model.MovieViewModel
import utils.readString

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class InstrumentalTestDataModule {

    @PerApplication
    @Provides
    fun provideMovies(mapperDb: MovieResponseToMovieDbMapper,
                      movieMapper: MovieDbModelToMovieMapper,
                      movieViemodelMapper: MovieToMovieViewModelMapper): List<MovieViewModel> {
        val json = readString(this, "json/movies.json")
        if (json != null) {
            val list: List<MovieResponse.Result> = Gson().fromJson(json, MovieResponse::class.java).results
            return list.map(mapperDb).map(movieMapper).map(movieViemodelMapper)
        }

        return emptyList()
    }

}