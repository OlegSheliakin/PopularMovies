package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.database.MovieDao
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.domain.MovieRepository
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by olegshelyakin on 24.08.17.
 */

@Module
class TestDbModule {

    @PerApplication
    @Provides
    fun provideMovieDao(listDb: List<MovieDbModel>): MovieDao = object : MovieDao() {

        override fun getAll(): Flowable<List<MovieDbModel>> {
            return Flowable.just(emptyList())
        }

        override fun get(id: Long): Single<MovieDbModel> {
            return Single.error(Exception())
        }

        override fun insert(movie: MovieDbModel) {
        }

        override fun insert(movies: List<MovieDbModel>) {
        }

        override fun deleteById(id: Long) {
        }

        override fun getAllByType(type: String): Flowable<List<MovieDbModel>> {
            if (type == MovieRepository.Filter.FAVOURITE.value) {
                return Flowable.just(listDb)
            }
            return Flowable.just(emptyList())
        }

    }
}