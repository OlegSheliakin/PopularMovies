package home.oleg.popularmovies.presentation.list.presenter;

import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.presentation.mappers.Mapper;
import home.oleg.popularmovies.domain.usecases.GetMoviesUseCase;
import home.oleg.popularmovies.presentation.list.ListView;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class ListPresenterImpl implements ListPresenter, UseCaseObserver<List<MovieViewModel>> {

    private final GetMoviesUseCase useCase;
    private ListView view;

    public ListPresenterImpl(ListView view, GetMoviesUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
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
    public void onSuccess(List<MovieViewModel> results) {
        view.hideLoading();
        view.fillList(results);
    }

    @Override
    public void onError() {
        view.hideLoading();
        view.showError();
    }
}