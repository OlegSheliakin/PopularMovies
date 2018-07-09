package home.oleg.popularmovies.di

import com.beender.android.di.scope.PerApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import home.oleg.popularmovies.data.network.MovieApi
import utils.readString
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by olegshelyakin on 13.09.17.
 */

@Module
class TestNetworkModule {

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
            addNetworkInterceptor(logging)
        }.build()
    }

    @Provides
    fun provideGson(): Gson = Gson()

    @PerApplication
    @Provides
    fun provideMovieApi(httpClient: OkHttpClient, gson: Gson, httpUrl: HttpUrl): MovieApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(httpUrl.toString())
                .build().create(MovieApi::class.java)
    }

    @PerApplication
    @Provides
    fun provideDispatcher(): Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {

            if (request.path.contains("top_rated") || request.path.contains("popular")) {
                return MockResponse().setResponseCode(200).setBody(readString(this, "json/movies.json"))
            }
            return MockResponse().setResponseCode(404)
        }
    }

    @PerApplication
    @Provides
    fun provideServer(dispatcher: Dispatcher): MockWebServer = MockWebServer().apply {
        setDispatcher(dispatcher)
    }

    @PerApplication
    @Provides
    fun provideUrl(server: MockWebServer): HttpUrl = server.url("/")

}