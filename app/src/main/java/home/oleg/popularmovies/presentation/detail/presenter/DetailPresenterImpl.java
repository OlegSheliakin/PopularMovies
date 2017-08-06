package home.oleg.popularmovies.presentation.detail.presenter;

import android.content.ContentResolver;
import android.content.ContentValues;

import java.util.List;

import home.oleg.popularmovies.data.database.MovieDbContract;
import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.domain.usecases.DetailInteractor;
import home.oleg.popularmovies.presentation.detail.DetailView;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class DetailPresenterImpl implements DetailPresenter {

    private final DetailView view;
    private final DetailInteractor interactor;
    private MovieViewModel model;

    public DetailPresenterImpl(DetailView view, DetailInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onStart(MovieViewModel model) {
        this.model = model;
        view.fillMovieContent(model);

        interactor.executeFetchReviews(new UseCaseObserver<List<ReviewResponse.Result>>() {
            @Override
            public void onStartExecute() {

            }

            @Override
            public void onSuccess(List<ReviewResponse.Result> results) {
                view.showReviews(results);
            }

            @Override
            public void onError() {
                view.showError();
            }
        }, model.getId());


        interactor.executeFetchTrailers(new UseCaseObserver<List<VideosResponse.Result>>() {
            @Override
            public void onStartExecute() {

            }

            @Override
            public void onSuccess(List<VideosResponse.Result> results) {
                view.showTrailers(results);
            }

            @Override
            public void onError() {
                view.showError();
            }
        }, model.getId());
    }

    @Override
    public void onFavouriteClick(ContentResolver contentResolver) {
        ContentValues values = new ContentValues();
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID, model.getId());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, model.getImagePath());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_TITLE, model.getOriginalTitle());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, model.getPlotSynopsis());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, model.getUserRating());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, model.getReleaseAt());
        contentResolver.insert(MovieDbContract.MovieEntry.buildUri(model.getId()), values);
    }

}
