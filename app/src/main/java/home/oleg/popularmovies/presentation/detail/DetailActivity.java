package home.oleg.popularmovies.presentation.detail;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.CachedMovieRepository;
import home.oleg.popularmovies.data.database.MovieDbContract;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.domain.usecases.DetailInteractor;
import home.oleg.popularmovies.domain.usecases.GetReviewsUseCase;
import home.oleg.popularmovies.domain.usecases.GetTrailersUseCase;
import home.oleg.popularmovies.presentation.BasicActivity;
import home.oleg.popularmovies.presentation.detail.presenter.DetailPresenter;
import home.oleg.popularmovies.presentation.detail.presenter.DetailPresenterImpl;
import home.oleg.popularmovies.presentation.mappers.ReviewModelMapper;
import home.oleg.popularmovies.presentation.mappers.TrailerModelMapper;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;
import home.oleg.popularmovies.presentation.model.TrailerViewModel;

import static butterknife.ButterKnife.findById;

public class DetailActivity extends BasicActivity implements DetailView {

    public static final String INTENT_KEY_MOVIE = "movie";
    private static final String SCROLL_POSITION = "scroll_position";

    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_release_at)
    TextView tvReleaseAt;
    @BindView(R.id.rb_user_rating)
    RatingBar rbUserRating;
    @BindView(R.id.tv_post_synopsis)
    TextView tvPostSynopsis;
    @BindView(R.id.layout_reviews)
    ViewGroup reviewsContainer;
    @BindView(R.id.layout_videos)
    ViewGroup trailersContainer;
    @BindView(R.id.fab_favourite)
    FloatingActionButton fabFavourite;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailInteractor interactor = new DetailInteractor(new GetReviewsUseCase(NetworkMovies.getInstance()),
                new GetTrailersUseCase(NetworkMovies.getInstance()), new CachedMovieRepository(this));
        detailPresenter = new DetailPresenterImpl(this, interactor, new ReviewModelMapper(), new TrailerModelMapper());

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setTitle(R.string.detail_activity);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MovieViewModel model = getIntent().getParcelableExtra(INTENT_KEY_MOVIE);
        if (model != null) {
            if (savedInstanceState == null) {
                detailPresenter.onStart(model);
            } else {
                restoreState(savedInstanceState);
            }
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            detailPresenter.restoreState(savedInstanceState);
            int[] position = savedInstanceState.getIntArray(SCROLL_POSITION);
            if (position != null) {
                scrollView.post(() -> scrollView.scrollTo(position[0], position[1]));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(SCROLL_POSITION,
                new int[]{scrollView.getScrollX(), scrollView.getScrollY()});
        detailPresenter.saveState(outState);
    }

    @LayoutRes
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void fillMovieContent(MovieViewModel model) {
        Picasso.with(this).load(NetworkMovies.IMAGE_URL + model.getW185ImagePath()).into(ivPoster);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(model.getOriginalTitle());
        }

        String releaseDate = getString(R.string.release_date) + ": " + model.getReleaseAt();
        tvReleaseAt.setText(releaseDate);
        tvPostSynopsis.setText(model.getPlotSynopsis());
        rbUserRating.setRating(model.getUserRating());
    }

    @Override
    public void showReviews(List<ReviewViewModel> results) {
        LayoutInflater inflater = getLayoutInflater();
        boolean hasReviews = false;

        if (!results.isEmpty()) {
            for (ReviewViewModel review : results) {
                View reviewView = inflater.inflate(R.layout.item_review, reviewsContainer, false);
                TextView tvAuthor = ButterKnife.findById(reviewView, R.id.tv_author);
                TextView tvContent = ButterKnife.findById(reviewView, R.id.tv_content);

                tvAuthor.setText(review.getAuthor());
                tvContent.setText(review.getOverview());

                reviewsContainer.addView(reviewView);
            }
            hasReviews = true;
        }

        reviewsContainer.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTrailers(List<TrailerViewModel> results) {
        LayoutInflater inflater = getLayoutInflater();
        boolean hasReviews = false;

        if (!results.isEmpty()) {
            for (TrailerViewModel review : results) {
                View trailerView = inflater.inflate(R.layout.item_trailer, trailersContainer, false);
                TextView tvName = ButterKnife.findById(trailerView, R.id.tv_trailer);

                tvName.setText(review.getName());
                tvName.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + review.getKey()));
                    startActivity(intent);
                });

                trailersContainer.addView(trailerView);
            }
            hasReviews = true;
        }

        trailersContainer.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setFavouriteIcon(@DrawableRes Integer res) {
        fabFavourite.setImageResource(res);
    }

    @OnClick(R.id.fab_favourite)
    public void onFavouriteClick() {
        detailPresenter.onFavouriteClick();
    }

}
