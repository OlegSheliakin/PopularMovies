package home.oleg.popularmovies.presentation.detail.presenter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.domain.usecases.DetailInteractor;
import home.oleg.popularmovies.presentation.detail.DetailView;
import home.oleg.popularmovies.presentation.mappers.Mapper;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;
import home.oleg.popularmovies.presentation.model.TrailerViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class DetailPresenterImpl implements DetailPresenter {

    private static final String MODEL = "model";
    private static final String REVIEWS = "reviews";
    private static final String TRAILERS = "trailers";

    private final DetailView view;
    private final DetailInteractor interactor;
    private final Mapper<List<ReviewViewModel>, List<ReviewResponse.Result>> mapperReview;
    private final Mapper<List<TrailerViewModel>, List<VideosResponse.Result>> mapperTrailer;
    private MovieViewModel model;
    private ArrayList<ReviewViewModel> reviewViewModels = new ArrayList<>();
    private ArrayList<TrailerViewModel> trailerViewModels = new ArrayList<>();

    public DetailPresenterImpl(DetailView view, DetailInteractor interactor,
                               Mapper<List<ReviewViewModel>, List<ReviewResponse.Result>> mapperReview,
                               Mapper<List<TrailerViewModel>, List<VideosResponse.Result>> mapperTrailer) {
        this.view = view;
        this.interactor = interactor;
        this.mapperReview = mapperReview;
        this.mapperTrailer = mapperTrailer;
    }

    @Override
    public void onStart(MovieViewModel model) {
        if (interactor.isFavourite(model.getId())) {
            model.setFavourite(true);
            view.setFavouriteIcon(R.drawable.ic_star_black_24dp);
        } else {
            view.setFavouriteIcon(R.drawable.ic_star_border_black_24dp);
        }

        this.model = model;
        view.fillMovieContent(model);

        interactor.executeFetchReviews(new UseCaseObserver<List<ReviewResponse.Result>>() {
            @Override
            public void onStartExecute() {

            }

            @Override
            public void onSuccess(List<ReviewResponse.Result> results) {
                reviewViewModels.clear();
                reviewViewModels.addAll(mapperReview.map(results));
                view.showReviews(reviewViewModels);
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
                trailerViewModels.clear();
                trailerViewModels.addAll(mapperTrailer.map(results));
                view.showTrailers(trailerViewModels);
            }

            @Override
            public void onError() {
                view.showError();
            }
        }, model.getId());
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        this.model = savedInstanceState.getParcelable(MODEL);
        this.trailerViewModels = savedInstanceState.getParcelableArrayList(TRAILERS);
        this.reviewViewModels = savedInstanceState.getParcelableArrayList(REVIEWS);
        view.showReviews(reviewViewModels);
        view.showTrailers(trailerViewModels);
        view.fillMovieContent(model);
    }

    @Override
    public void saveState(Bundle outState) {
        outState.putParcelable(MODEL, model);
        outState.putParcelableArrayList(REVIEWS, reviewViewModels);
        outState.putParcelableArrayList(TRAILERS, trailerViewModels);
    }

    @Override
    public MovieViewModel getMovieModel() {
        return model;
    }

    @Override
    public void onFavouriteClick() {
        if (model.isFavourite()) {
            interactor.deleteFromFavourite(model.getId());
            view.setFavouriteIcon(R.drawable.ic_star_border_black_24dp);
            model.setFavourite(false);
        } else {
            interactor.addToFavourite(model);
            model.setFavourite(true);
            view.setFavouriteIcon(R.drawable.ic_star_black_24dp);
        }
    }

}
