package home.oleg.popularmovies.presentation.list.presenter

import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.domain.entities.Movie
import home.oleg.popularmovies.domain.usecases.GetMoviesUseCase
import home.oleg.popularmovies.presentation.list.ListView
import home.oleg.popularmovies.presentation.model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Oleg on 15.04.2017.
 */

class ListPresenter @Inject constructor(
        private val disposableBag: CompositeDisposable,
        private val useCase: GetMoviesUseCase) {

    private var view: ListView? = null

    fun attachView(view: ListView) {
        this.view = view
    }

    fun fetchMovies(filter: MovieRepository.Filter) {
        useCase.execute(filter)
                .map { it.map(MovieViewModel.Mapper) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading() }
                .doFinally { view?.hideLoading() }
                .subscribe({ view?.fillList(it) }, {
                    it.printStackTrace()
                    view?.showError() })
                .let(disposableBag::add)
    }

    fun detachView() {
        view = null
        disposableBag.clear()
    }
}