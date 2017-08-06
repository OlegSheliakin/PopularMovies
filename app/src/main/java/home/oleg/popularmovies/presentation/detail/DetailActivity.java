package home.oleg.popularmovies.presentation.detail;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.database.MovieDbContract;
import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.domain.usecases.DetailInteractor;
import home.oleg.popularmovies.domain.usecases.GetReviewsUseCase;
import home.oleg.popularmovies.domain.usecases.GetTrailersUseCase;
import home.oleg.popularmovies.presentation.BasicActivity;
import home.oleg.popularmovies.presentation.detail.presenter.DetailPresenter;
import home.oleg.popularmovies.presentation.detail.presenter.DetailPresenterImpl;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import retrofit2.http.PUT;

import static butterknife.ButterKnife.findById;

public class DetailActivity extends BasicActivity implements DetailView {

    public static final String INTENT_KEY_MOVIE = "movie";

    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_original_title)
    TextView tvOriginalTitle;
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


    private DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailInteractor interactor = new DetailInteractor(new GetReviewsUseCase(NetworkMovies.getInstance()), new GetTrailersUseCase(NetworkMovies.getInstance()));
        detailPresenter = new DetailPresenterImpl(this, interactor);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setTitle(R.string.detail_activity);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MovieViewModel model = getIntent().getParcelableExtra(INTENT_KEY_MOVIE);
        if (model != null) {
            detailPresenter.onStart(model);
        }

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
        Picasso.with(this).load(NetworkMovies.IMAGE_URL + model.getImagePath()).into(ivPoster);
        tvOriginalTitle.setText(model.getOriginalTitle());
        String releaseDate = getString(R.string.release_date) + ": " + model.getReleaseAt();
        tvReleaseAt.setText(releaseDate);
        tvPostSynopsis.setText(model.getPlotSynopsis());
        rbUserRating.setRating(model.getUserRating());
    }

    @Override
    public void showReviews(List<ReviewResponse.Result> results) {
        LayoutInflater inflater = getLayoutInflater();
        boolean hasReviews = false;

        if (!results.isEmpty()) {
            for (ReviewResponse.Result review : results) {
                View reviewView = inflater.inflate(R.layout.item_review, reviewsContainer, false);
                TextView tvAuthor = ButterKnife.findById(reviewView, R.id.tv_author);
                TextView tvContent = ButterKnife.findById(reviewView, R.id.tv_content);

                tvAuthor.setText(review.getAuthor());
                tvContent.setText(review.getContent());

                reviewsContainer.addView(reviewView);
                hasReviews = true;
            }
        }

        reviewsContainer.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTrailers(List<VideosResponse.Result> results) {
        LayoutInflater inflater = getLayoutInflater();
        boolean hasReviews = false;

        if (!results.isEmpty()) {
            for (VideosResponse.Result review : results) {
                View trailerView = inflater.inflate(R.layout.item_trailer, trailersContainer, false);
                TextView tvName = ButterKnife.findById(trailerView, R.id.tv_trailer);

                tvName.setText(review.getName());

                trailersContainer.addView(trailerView);
                hasReviews = true;
            }
        }

        trailersContainer.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.fab_favourite)
    public void onFavouriteClick() {
        detailPresenter.onFavouriteClick(getContentResolver());
    }
}
