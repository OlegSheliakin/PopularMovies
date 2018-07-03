package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import dagger.Component
import home.oleg.popularmovies.di.modules.RepositoryModule
import home.oleg.popularmovies.di.modules.RxModule
import home.oleg.popularmovies.di.modules.TestDbModule
import home.oleg.popularmovies.di.modules.TestNetworkModule
import home.oleg.popularmovies.presentation.list.presenter.ListPresenterTestWithDagger

@PerApplication
@Component(modules = [
    TestNetworkModule::class,
    RepositoryModule::class,
    RxModule::class,
    TestDbModule::class])
interface TestMovieAppComponent {

    fun inject(target: ListPresenterTestWithDagger)

}