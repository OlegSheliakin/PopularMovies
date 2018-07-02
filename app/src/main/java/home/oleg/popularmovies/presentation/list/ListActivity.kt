package home.oleg.popularmovies.presentation.list

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import home.oleg.popularmovies.R
import home.oleg.popularmovies.domain.MovieRepository
import home.oleg.popularmovies.presentation.BasicActivity
import home.oleg.popularmovies.presentation.detail.DetailActivity
import home.oleg.popularmovies.presentation.model.MovieViewModel
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter
import home.oleg.popularmovies.presentation.list.rvadapter.MovieAdapter
import kotlinx.android.synthetic.main.content_list.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject

class ListActivity : BasicActivity(), SwipeRefreshLayout.OnRefreshListener, ListView {

    @Inject
    lateinit var adapter: MovieAdapter

    @Inject
    lateinit var presenter: ListPresenter

    private var filter: MovieRepository.Filter = MovieRepository.Filter.POPULAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContent()
        presenter.attachView(this)
        checkInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (filter === MovieRepository.Filter.FAVOURITE) {
            presenter.fetchMovies(filter)
        }
    }

    @LayoutRes
    override val layoutId: Int = R.layout.activity_list

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val elements = adapter.items

        outState.putParcelableArrayList(KEY_ITEMS, arrayListOf(*elements.toTypedArray()))
        outState.putString(KEY_FILTER, filter.value)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var filter: MovieRepository.Filter? = null

        when {
            item.itemId == R.id.menu_item_popular -> filter = MovieRepository.Filter.POPULAR
            item.itemId == R.id.menu_item_top_rated -> filter = MovieRepository.Filter.TOP_RATED
            item.itemId == R.id.menu_item_favourite -> filter = MovieRepository.Filter.FAVOURITE
        }

        if (filter != null && this.filter != filter) {
            this.filter = filter
            presenter.fetchMovies(filter)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        presenter.fetchMovies(filter)
    }

    override fun showLoading() {
        if (!swipeRefresh.isRefreshing()) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun fillList(models: List<MovieViewModel>) {
        swipeRefresh.isRefreshing = false
        adapter.setMovies(models)
        Log.d("response", models.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupContent() {
        swipeRefresh.setOnRefreshListener(this)

        val layoutManager = GridLayoutManager(this, 3)

        adapter.clickListener = {
            val intent = Intent(this@ListActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.INTENT_KEY_MOVIE, it)
            startActivity(intent)
            Log.d("tapped", it.toString())
        }

        rvMovies.layoutManager = layoutManager
        rvMovies.adapter = adapter
        rvMovies.setHasFixedSize(true)
    }

    private fun checkInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val list = savedInstanceState.getParcelableArrayList<MovieViewModel>(KEY_ITEMS)
            if (list != null) {
                adapter.setMovies(list)
            }

            val filter = MovieRepository.Filter.resolve(savedInstanceState.getString(KEY_FILTER))
            this.filter = filter

        } else {
            presenter.fetchMovies(filter)
        }
    }

    companion object {
        val KEY_ITEMS = "items"
        val KEY_FILTER = "filter"
    }

}
