package home.oleg.popularmovies.presentation.list

import home.oleg.popularmovies.presentation.base.WithError
import home.oleg.popularmovies.presentation.base.WithLoading
import home.oleg.popularmovies.presentation.model.MovieViewModel

/**
 * Created by Oleg on 15.04.2017.
 */

interface ListView : WithError, WithLoading {
    fun fillList(models: List<MovieViewModel>)
}
