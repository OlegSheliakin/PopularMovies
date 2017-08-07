package home.oleg.popularmovies.presentation.detail;

import android.support.annotation.DrawableRes;

import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;
import home.oleg.popularmovies.presentation.model.TrailerViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public interface DetailView {
    void showLoading();
    void hideLoading();
    void showError();
    void fillMovieContent(MovieViewModel model);
    void showReviews(List<ReviewViewModel> results);
    void showTrailers(List<TrailerViewModel> results);
    void setFavouriteIcon(@DrawableRes Integer res);
}
