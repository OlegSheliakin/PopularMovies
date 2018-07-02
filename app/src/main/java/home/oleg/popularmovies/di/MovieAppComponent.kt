package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import home.oleg.popularmovies.MovieApplication
import home.oleg.popularmovies.di.modules.BuildersModule
import home.oleg.popularmovies.di.modules.DbModule
import home.oleg.popularmovies.di.modules.NetworkModule
import home.oleg.popularmovies.di.modules.RepositoryModule
import home.oleg.popularmovies.di.modules.RxModule

@PerApplication
@Component(modules = [
    NetworkModule::class,
    RepositoryModule::class,
    RxModule::class,
    DbModule::class,
    BuildersModule::class,
    AndroidSupportInjectionModule::class])
interface MovieAppComponent : AndroidInjector<MovieApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MovieApplication>()

}