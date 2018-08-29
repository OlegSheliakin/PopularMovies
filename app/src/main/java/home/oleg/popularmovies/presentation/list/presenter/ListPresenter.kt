package home.oleg.popularmovies.presentation.list.presenter

import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.list.ListView
import home.oleg.popularmovies.presentation.mappers.MovieToMovieViewModelMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.*
import javax.inject.Inject

/**
 * Created by Oleg on 15.04.2017.
 */

class ListPresenter @Inject constructor(
        private val disposableBag: CompositeDisposable,
        private val mapper: MovieToMovieViewModelMapper,
        private val movieRepository: MovieRepository) {

    private var view: ListView? = null

    fun attachView(view: ListView) {
        this.view = view

        /*Observable.interval(5000, TimeUnit.MILLISECONDS).flatMap {
            movieRepository.getMovies(MovieRepository.Filter.TOP_RATED)

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({
                }, {
                    it.printStackTrace()
                })
                .let(disposableBag::add)*/
    }

    fun fetchMovies(filter: MovieRepository.Filter) {
        movieRepository.getMovies(filter)
                .map { it.map(mapper) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe { view?.showLoading() }
                .doFinally { view?.hideLoading() }
                .subscribe({
                    view?.fillList(it)
                }, {
                    it.printStackTrace()
                    view?.showError()
                })
                .let(disposableBag::add)
    }

    fun detachView() {
        view = null
        disposableBag.clear()
    }
}