package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.data.entities.MovieResponse
import home.oleg.popularmovies.data.mapper.MovieDbModelToMovieMapper
import home.oleg.popularmovies.data.mapper.MovieResponseToMovieDbMapper
import home.oleg.popularmovies.domain.entities.Movie
import home.oleg.popularmovies.presentation.mappers.MovieToMovieViewModelMapper
import home.oleg.popularmovies.presentation.model.MovieViewModel
import utils.readString

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class TestDataModule {

    @PerApplication
    @Provides
    fun provideDtos(gson: Gson): List<MovieResponse.Result> {
        val json = readString(this, "json/movies.json")
        if (json != null) {
            return gson.fromJson(json, MovieResponse::class.java).results
        }

        return emptyList()
    }

    @PerApplication
    @Provides
    fun provideMovieDbModel(
            listDto: List<MovieResponse.Result>,
            mapper: MovieResponseToMovieDbMapper): List<MovieDbModel> = listDto.map(mapper)

    @PerApplication
    @Provides
    fun provideMovie(
            listDb: List<MovieDbModel>,
            mapper: MovieDbModelToMovieMapper): List<Movie> = listDb.map(mapper)


    @PerApplication
    @Provides
    fun provideMovies(
            listDto: List<Movie>,
            movieViemodelMapper: MovieToMovieViewModelMapper): List<MovieViewModel> {
        return listDto.map(movieViemodelMapper)
    }

}