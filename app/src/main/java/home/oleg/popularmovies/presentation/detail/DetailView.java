package home.oleg.popularmovies.presentation.detail;

import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public interface DetailView {
    void showLoading();
    void hideLoading();
    void showError();
    void fillMovieContent(MovieViewModel model);
    void showReviews(List<ReviewResponse.Result> results);
    void showTrailers(List<VideosResponse.Result> results);
}
