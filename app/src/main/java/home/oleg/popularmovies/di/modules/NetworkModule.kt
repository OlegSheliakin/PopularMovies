package home.oleg.popularmovies.di.modules

import com.beender.android.di.scope.PerApplication
import com.google.gson.Gson
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
class NetworkModule {

    @PerApplication
    @Provides
    fun providesHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @PerApplication
    @Provides
    fun provideMovieOkHttpClient(logging: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            addNetworkInterceptor(logging)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    fun provideGson(): Gson = Gson()

    @PerApplication
    @Provides
    fun provideMovieApi(httpClient: OkHttpClient, gson: Gson): MovieApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .build().create(MovieApi::class.java)
    }
}