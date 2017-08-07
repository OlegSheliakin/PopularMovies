package home.oleg.popularmovies.domain.usecases;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.database.MovieDbContract;
import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.data.network.MovieApi;
import home.oleg.popularmovies.domain.MovieRepository;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter;
import home.oleg.popularmovies.presentation.mappers.Mapper;
import home.oleg.popularmovies.presentation.mappers.MovieModelMapper;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import retrofit2.Call;

/**
 * Created by Oleg on 15.04.2017.
 */

public class GetMoviesUseCase {

    private final MovieApi movieApi;
    private Call<MovieResponse> call;
    private final MovieRepository movieRepository;

    public GetMoviesUseCase(MovieApi movieApi, MovieRepository movieRepository) {
        this.movieApi = movieApi;
        this.movieRepository = movieRepository;
    }

    public void execute(UseCaseObserver<List<MovieViewModel>> observer, ListPresenter.Filter filter) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }

        if (filter == ListPresenter.Filter.FAVOURITE) {
            List<MovieViewModel> results = movieRepository.getMovies();
            observer.onSuccess(results);
            return;
        }

        call = movieApi.getMovies(filter.getValue());
        call.enqueue(new MovieCall(observer));
        observer.onStartExecute();
    }

    private static class MovieCall extends BaseCallbackAdapter<MovieResponse, MovieViewModel> {

        private MovieCall(UseCaseObserver<List<MovieViewModel>> observer) {
            super(observer);
        }

        @Override
        protected List<MovieViewModel> getResults(MovieResponse movieResponse) {
            return new MovieModelMapper().map(movieResponse.getResults());
        }
    }
}


