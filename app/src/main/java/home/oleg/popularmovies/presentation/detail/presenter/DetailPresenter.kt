package home.oleg.popularmovies.presentation.detail.presenter

import android.os.Bundle

import java.util.ArrayList

import home.oleg.popularmovies.R
import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.entities.VideosResponse
import home.oleg.popularmovies.domain.usecases.DetailInteractor
import home.oleg.popularmovies.presentation.detail.DetailView
import home.oleg.popularmovies.presentation.mappers.Mapper
import home.oleg.popularmovies.presentation.model.MovieViewModel
import home.oleg.popularmovies.presentation.model.ReviewViewModel
import home.oleg.popularmovies.presentation.model.TrailerViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

class DetailPresenter @Inject constructor(
        private val disposableBag: CompositeDisposable,
        private val interactor: DetailInteractor,
        private val mapperReview: Mapper<List<ReviewViewModel>, List<ReviewResponse.Result>>,
        private val mapperTrailer: Mapper<List<TrailerViewModel>, List<VideosResponse.Result>>) {


    private var view: DetailView? = null

    private var model: MovieViewModel? = null
    private var reviewViewModels: ArrayList<ReviewViewModel> = ArrayList()
    private var trailerViewModels: ArrayList<TrailerViewModel> = ArrayList()

    fun attachView(view: DetailView) {
        this.view = view
    }

    fun onStart(model: MovieViewModel) {
        if (model.isFavourite) {
            view?.setFavouriteIcon(R.drawable.ic_star_black_24dp)
        } else {
            view?.setFavouriteIcon(R.drawable.ic_star_border_black_24dp)
        }

        this.model = model
        view?.fillMovieContent(model)

        interactor.fetchReviews(model.id)
                .map { mapperReview.map(it) }
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { reviewViewModels ->
                    this.reviewViewModels.apply {
                        clear()
                        addAll(reviewViewModels)
                    }
                }
                .subscribe({ view?.showReviews(it) }, { view?.showError() })
                .let(disposableBag::add)

        interactor.fetchTrailers(model.id)
                .map { mapperTrailer.map(it) }
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { trailerViewModels ->
                    this.trailerViewModels.apply {
                        clear()
                        addAll(trailerViewModels)
                    }
                }
                .subscribe({ view?.showTrailers(it) }, { view?.showError() })
                .let(disposableBag::add)
    }

    fun restoreState(savedInstanceState: Bundle) {
        this.model = savedInstanceState.getParcelable(MODEL)
        this.trailerViewModels = savedInstanceState.getParcelableArrayList(TRAILERS) ?: arrayListOf()
        this.reviewViewModels = savedInstanceState.getParcelableArrayList(REVIEWS) ?: arrayListOf()

        view?.showReviews(reviewViewModels)
        view?.showTrailers(trailerViewModels)

        model?.let {
            view?.fillMovieContent(it)
        }
    }

    fun saveState(outState: Bundle) {
        outState.putParcelable(MODEL, model)
        outState.putParcelableArrayList(REVIEWS, reviewViewModels)
        outState.putParcelableArrayList(TRAILERS, trailerViewModels)
    }

    fun getMovieModel(): MovieViewModel? {
        return model
    }

    fun onFavouriteClick() {
        model?.let {
            if (it.isFavourite) {
                view?.setFavouriteIcon(R.drawable.ic_star_border_black_24dp)
                it.isFavourite = false
                interactor.deleteFromFavourite(it.id)
            } else {
                it.isFavourite = true
                view?.setFavouriteIcon(R.drawable.ic_star_black_24dp)
                interactor.addToFavourite(it.id)
            }
        }?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({}, { view?.showError() })?.let(disposableBag::add)
    }

    fun detachView() {
        view = null
        disposableBag.clear()
    }

    companion object {
        private val MODEL = "model"
        private val REVIEWS = "reviews"
        private val TRAILERS = "trailers"
    }

}
