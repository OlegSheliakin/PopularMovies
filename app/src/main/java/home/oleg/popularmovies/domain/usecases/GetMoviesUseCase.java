package home.oleg.popularmovies.domain.usecases;

import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.data.network.MovieApi;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter;
import retrofit2.Call;

/**
 * Created by Oleg on 15.04.2017.
 */

public class GetMoviesUseCase {

    private final MovieApi movieApi;
    private Call<MovieResponse> call;

    public GetMoviesUseCase(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public void execute(UseCaseObserver<List<MovieResponse.Result>> observer, ListPresenter.Filter filter) {
        if(call != null && call.isExecuted()) {
            call.cancel();
        }

        call = movieApi.getMovies(filter.getValue());
        call.enqueue(new MovieCall(observer));
        observer.onStartExecute();
    }

    private static class MovieCall extends BaseCallbackAdapter<MovieResponse, MovieResponse.Result> {

        private MovieCall(UseCaseObserver<List<MovieResponse.Result>> observer) {
            super(observer);
        }

        @Override
        protected List<MovieResponse.Result> getResults(MovieResponse movieResponse) {
            return movieResponse.getResults();
        }
    }
}


