package home.oleg.popularmovies.di.modules

import dagger.Binds
import dagger.Module
import home.oleg.popularmovies.data.FavoriteMoviesRepositoryImpl
import home.oleg.popularmovies.data.MovieRepositoryImpl
import home.oleg.popularmovies.domain.FavoriteMovieRepository
import home.oleg.popularmovies.domain.MovieRepository

/**
 * Created by olegshelyakin on 26.10.17.
 */

@Module
interface RepositoryModule {

    @Binds
    fun provideMoviesRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    fun provideFavoriteMoviesRepository(repository: FavoriteMoviesRepositoryImpl): FavoriteMovieRepository
}