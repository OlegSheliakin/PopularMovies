package home.oleg.popularmovies.domain

import io.reactivex.Completable

interface BookmarkMovieRepository {

    fun addFavorite(id: Long): Completable

    fun removeFavorite(id: Long): Completable

}