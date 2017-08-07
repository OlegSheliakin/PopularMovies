package home.oleg.popularmovies.presentation.detail.presenter;

import android.content.ContentResolver;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;
import home.oleg.popularmovies.presentation.model.TrailerViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public interface DetailPresenter {
    void onStart(MovieViewModel model);

    void restoreState(Bundle savedInstanceState);

    void saveState(Bundle outState);

    MovieViewModel getMovieModel();

    void onFavouriteClick();

}
