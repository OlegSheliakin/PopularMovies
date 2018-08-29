package home.oleg.popularmovies.di.modules

import com.beender.android.di.scope.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import home.oleg.popularmovies.TestService
import home.oleg.popularmovies.presentation.detail.DetailActivity
import home.oleg.popularmovies.presentation.list.ListActivity

/**
 * Created by olegshelyakin on 26.10.17.
 */

@Module
interface BuildersModule {

    @PerActivity
    @ContributesAndroidInjector()
    fun provideListActivityFactory(): ListActivity

    @PerActivity
    @ContributesAndroidInjector()
    fun provideDetailnActivityFactory(): DetailActivity

    @PerActivity
    @ContributesAndroidInjector()
    fun provideTestServiceFactory(): TestService

}