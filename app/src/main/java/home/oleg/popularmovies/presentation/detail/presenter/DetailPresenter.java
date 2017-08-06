package home.oleg.popularmovies.presentation.detail.presenter;

import android.content.ContentResolver;

import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public interface DetailPresenter {
    void onStart(MovieViewModel model);

    void onFavouriteClick(ContentResolver contentResolver);
}
