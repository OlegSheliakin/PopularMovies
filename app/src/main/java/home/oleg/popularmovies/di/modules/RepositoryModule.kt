package home.oleg.popularmovies.di.modules

import dagger.Binds
import dagger.Module
import home.oleg.popularmovies.data.BookmarkMoviesRepositoryImpl
import home.oleg.popularmovies.data.MovieRepositoryImpl
import home.oleg.popularmovies.domain.BookmarkMovieRepository
import home.oleg.popularmovies.domain.MovieRepository

/**
 * Created by olegshelyakin on 26.10.17.
 */

@Module
interface RepositoryModule {

    @Binds
    fun provideMoviesRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    fun provideFavoriteMoviesRepository(repository: BookmarkMoviesRepositoryImpl): BookmarkMovieRepository
}