package home.oleg.popularmovies.presentation.list;

import java.util.List;

import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface ListView {
    void showLoading();
    void hideLoading();
    void showError();
    void fillList(List<MovieViewModel> models);
}
