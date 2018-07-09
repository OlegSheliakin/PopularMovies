package home.oleg.popularmovies.di.modules

import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.entities.VideosResponse
import home.oleg.popularmovies.presentation.mappers.Mapper
import home.oleg.popularmovies.presentation.mappers.ReviewModelMapper
import home.oleg.popularmovies.presentation.mappers.TrailerModelMapper
import home.oleg.popularmovies.presentation.model.ReviewViewModel
import home.oleg.popularmovies.presentation.model.TrailerViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class RxModule {

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideMapper() : Mapper<List<ReviewViewModel>, List<ReviewResponse.Result>> = ReviewModelMapper()

    @Provides
    fun provideMapper1() : Mapper<List<TrailerViewModel>, List<VideosResponse.Result>> = TrailerModelMapper()
}