package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import dagger.Component
import home.oleg.popularmovies.di.modules.RepositoryModule
import home.oleg.popularmovies.di.modules.RxModule
import home.oleg.popularmovies.presenter.ListPresenterInstrumentalTest

@PerApplication
@Component(modules = [
    InstrumentalTestDataModule::class,
    TestNetworkModule::class,
    RepositoryModule::class,
    RxModule::class,
    InstrumentalTestDbModule::class])
interface InstrumentalTestMovieAppComponent {

    fun inject(target: ListPresenterInstrumentalTest)

}