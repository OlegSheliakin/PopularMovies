package home.oleg.popularmovies.di.modules

import com.beender.android.di.scope.PerApplication
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.BuildConfig
import home.oleg.popularmovies.data.network.MovieApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by olegshelyakin on 13.09.17.
 */

@Module
class TestNetworkModule {

    @PerApplication
    @Provides
    fun provideMovieApi(): MovieApi =  mock()
}