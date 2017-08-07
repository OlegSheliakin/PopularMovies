package home.oleg.popularmovies.presentation.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.CachedMovieRepository;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.presentation.mappers.MovieModelMapper;
import home.oleg.popularmovies.domain.usecases.GetMoviesUseCase;
import home.oleg.popularmovies.presentation.BasicActivity;
import home.oleg.popularmovies.presentation.detail.DetailActivity;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenterImpl;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter.Filter;
import home.oleg.popularmovies.presentation.list.rvadapter.MovieAdapter;

import static home.oleg.popularmovies.presentation.list.presenter.ListPresenter.Filter.FAVOURITE;
import static home.oleg.popularmovies.presentation.list.presenter.ListPresenter.Filter.POPULAR;
import static home.oleg.popularmovies.presentation.list.presenter.ListPresenter.Filter.TOP_RATED;

public class ListActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener, ListView, MovieAdapter.MovieAdapterOnClickHandler {

    public static final String KEY_ITEMS = "items";
    public static final String KEY_FILTER = "filter";

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    private MovieAdapter adapter;

    private ListPresenter presenter;

    private Filter filter = POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContent();
        setupPresenter();
        checkInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (filter == FAVOURITE) {
            presenter.fetchMovies(filter);
        }
    }

    @Override
    protected
    @LayoutRes
    Integer getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ITEMS, adapter.getItems());
        outState.putString(KEY_FILTER, filter.getValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Filter filter = null;

        if (item.getItemId() == R.id.menu_item_popular) {
            filter = POPULAR;
        } else if (item.getItemId() == R.id.menu_item_top_rated) {
            filter = TOP_RATED;
        } else if (item.getItemId() == R.id.menu_item_favourite) {
            filter = FAVOURITE;
        }

        if (this.filter != filter) {
            this.filter = filter;
            presenter.fetchMovies(filter);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        presenter.fetchMovies(filter);
    }

    @Override
    public void showLoading() {
        if (!swipeRefresh.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillList(List<MovieViewModel> models) {
        swipeRefresh.setRefreshing(false);
        adapter.setItems(models);
        Log.d("response", models.toString());
    }

    @Override
    public void onClick(MovieViewModel movieModel) {
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_KEY_MOVIE, movieModel);
        startActivity(intent);
        Log.d("tapped", movieModel.toString());
    }

    private void setupContent() {
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new MovieAdapter(this);

        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(adapter);
        rvMovies.setHasFixedSize(true);
    }

    private void setupPresenter() {
        presenter = new ListPresenterImpl(
                this,
                new GetMoviesUseCase(NetworkMovies.getInstance(), new CachedMovieRepository(this)));
    }

    private void checkInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<MovieViewModel> list = savedInstanceState.getParcelableArrayList(KEY_ITEMS);
            if (list != null) {
                adapter.setItems(list);
            }

            Filter filter = Filter.resolve(savedInstanceState.getString(KEY_FILTER));
            if (filter != null) {
                this.filter = filter;
            }
        } else {
            presenter.fetchMovies(filter);
        }
    }

}
