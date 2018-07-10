import com.beender.android.di.scope.PerApplication
import dagger.Component
import home.oleg.popularmovies.data.network.MovieApiTest
import home.oleg.popularmovies.di.TestDataModule
import home.oleg.popularmovies.di.TestDbModule
import home.oleg.popularmovies.di.TestNetworkModule
import home.oleg.popularmovies.di.modules.RepositoryModule
import home.oleg.popularmovies.di.modules.RxModule
import home.oleg.popularmovies.presentation.list.presenter.ListPresenterTestWithDagger

@PerApplication
@Component(modules = [
    TestNetworkModule::class,
    TestDataModule::class,
    RepositoryModule::class,
    RxModule::class,
    TestDbModule::class])
interface TestMovieAppComponent {

    fun inject(target: ListPresenterTestWithDagger)
    fun inject(target: MovieApiTest)

}