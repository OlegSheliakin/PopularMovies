package home.oleg.popularmovies.presentation.list.presenter;

import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.domain.mappers.Mapper;
import home.oleg.popularmovies.domain.usecases.GetMoviesUseCase;
import home.oleg.popularmovies.presentation.list.ListView;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class ListPresenterImpl implements ListPresenter, UseCaseObserver<List<MovieResponse.Result>> {

    private final GetMoviesUseCase useCase;
    private final Mapper<List<MovieViewModel>, List<MovieResponse.Result>> mapper;
    private ListView view;

    public ListPresenterImpl(ListView view, GetMoviesUseCase useCase,
                             Mapper<List<MovieViewModel>, List<MovieResponse.Result>> mapper) {
        this.view = view;
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public void fetchMovies(Filter filter) {
        useCase.execute(this, filter);
    }

    @Override
    public void onStartExecute() {
        view.showLoading();
    }

    @Override
    public void onSuccess(List<MovieResponse.Result> results) {
        view.hideLoading();
        view.fillList(mapper.map(results));
    }

    @Override
    public void onError() {
        view.hideLoading();
        view.showError();
    }
}