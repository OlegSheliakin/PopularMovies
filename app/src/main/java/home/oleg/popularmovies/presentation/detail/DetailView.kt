package home.oleg.popularmovies.presentation.detail

import android.support.annotation.DrawableRes

import home.oleg.popularmovies.data.entities.ReviewResponse
import home.oleg.popularmovies.data.entities.VideosResponse
import home.oleg.popularmovies.presentation.base.WithError
import home.oleg.popularmovies.presentation.base.WithLoading
import home.oleg.popularmovies.presentation.model.MovieViewModel
import home.oleg.popularmovies.presentation.model.ReviewViewModel
import home.oleg.popularmovies.presentation.model.TrailerViewModel

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface DetailView : WithLoading, WithError {
    fun fillMovieContent(model: MovieViewModel)
    fun showReviews(results: List<ReviewViewModel>)
    fun showTrailers(results: List<TrailerViewModel>)
    fun setFavouriteIcon(@DrawableRes res: Int)
}
