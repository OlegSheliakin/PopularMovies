package home.oleg.popularmovies

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import home.oleg.popularmovies.di.DaggerMovieAppComponent

class MovieApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerMovieAppComponent.builder().create(this)

}