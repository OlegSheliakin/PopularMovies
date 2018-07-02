package home.oleg.popularmovies.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import home.oleg.popularmovies.BuildConfig
import home.oleg.popularmovies.R
import home.oleg.popularmovies.presentation.BasicActivity
import home.oleg.popularmovies.presentation.detail.presenter.DetailPresenter
import home.oleg.popularmovies.presentation.model.MovieViewModel
import home.oleg.popularmovies.presentation.model.ReviewViewModel
import home.oleg.popularmovies.presentation.model.TrailerViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.item_review.*
import kotlinx.android.synthetic.main.layout_videos.*
import javax.inject.Inject

class DetailActivity : BasicActivity(), DetailView {

    @Inject lateinit var presenter: DetailPresenter

    @LayoutRes
    override val layoutId: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)

        val bar = supportActionBar
        if (bar != null) {
            bar.setTitle(R.string.detail_activity)
            bar.setDisplayHomeAsUpEnabled(true)
        }

        val model = intent.getParcelableExtra<MovieViewModel>(INTENT_KEY_MOVIE)
        if (model != null) {
            if (savedInstanceState == null) {
                presenter.onStart(model)
            } else {
                restoreState(savedInstanceState)
            }
        }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState)
            val position = savedInstanceState.getIntArray(SCROLL_POSITION)
            if (position != null) {
                scrollView.post({ scrollView.scrollTo(position[0], position[1]) })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(SCROLL_POSITION,
                intArrayOf(scrollView.scrollX, scrollView.scrollY))
        presenter.saveState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError() {

    }

    override fun fillMovieContent(model: MovieViewModel) {
        Picasso.with(this).load(BuildConfig.BASE_IMAGE_URL + model.w185ImagePath).into(ivPoster)

        val actionBar = supportActionBar
        actionBar?.title = model.originalTitle

        val releaseDate = getString(R.string.release_date) + ": " + model.releaseAt
        tvReleaseAt.text = releaseDate
        tvPostSynopsis.text = model.plotSynopsis
        rbUserRating.rating = model.userRating?.toFloat() ?: 0f
    }

    override fun showReviews(results: List<ReviewViewModel>) {
        val inflater = layoutInflater
        var hasReviews = false

        if (!results.isEmpty()) {
            for (review in results) {
                val reviewView = inflater.inflate(R.layout.item_review, layoutReviews as ViewGroup, false)
                with(reviewView) {
                    tvAuthor.setText(review.author)
                    tvContent.setText(review.overview)
                }

                (layoutReviews as ViewGroup).addView(reviewView)
            }
            hasReviews = true
        }

        layoutReviews.visibility = if (hasReviews) View.VISIBLE else View.GONE
    }

    override fun showTrailers(results: List<TrailerViewModel>) {
        val inflater = layoutInflater
        var hasReviews = false

        if (!results.isEmpty()) {
            for (review in results) {
                val trailerView = inflater.inflate(R.layout.item_trailer, layoutVideos as ViewGroup, false)
                with(layoutVideos) {
                    tvName.text = review.name
                    tvName.setOnClickListener({
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("http://www.youtube.com/watch?v=" + review.key)
                        startActivity(intent)
                    })
                }
                (layoutVideos as ViewGroup).addView(trailerView)
            }
            hasReviews = true
        }

        layoutVideos.visibility = if (hasReviews) View.VISIBLE else View.GONE
    }

    override fun setFavouriteIcon(@DrawableRes res: Int) {
        fabFavourite.setImageResource(res)
    }

    fun onFavouriteClick() {
        presenter.onFavouriteClick()
    }

    companion object {

        val INTENT_KEY_MOVIE = "movie"
        private val SCROLL_POSITION = "scroll_position"
    }

}
