package home.oleg.popularmovies.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.beender.android.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.MovieApplication
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.MovieDatabase

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class DbModule {

    @PerApplication
    @Provides
    fun provideMovieDatabase(application: MovieApplication): MovieDatabase =
            Room.databaseBuilder(application, MovieDatabase::class.java, MovieDatabase.name)
                    .build()

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.getMovieDao()

}