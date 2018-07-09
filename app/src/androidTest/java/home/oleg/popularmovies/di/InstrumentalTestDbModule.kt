package home.oleg.popularmovies.di

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.beender.android.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.MovieDatabase

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class InstrumentalTestDbModule {

    @PerApplication
    @Provides
    fun provideMovieDatabase(): MovieDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @PerApplication
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.getMovieDao()
}