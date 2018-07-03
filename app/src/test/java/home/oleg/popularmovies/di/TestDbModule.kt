package home.oleg.popularmovies.di.modules

import android.arch.persistence.room.Room
import com.beender.android.di.scope.PerApplication
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.MovieApplication
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.MovieDatabase

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class TestDbModule {

    @PerApplication
    @Provides
    fun provideMovieDao(): MovieDao = mock()

}