package home.oleg.popularmovies.presentation.detail;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.presentation.BasicActivity;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

public class DetailActivity extends BasicActivity {

    public static final String INTENT_KEY_MOVIE = "movie";

    @BindView(R.id.iv_poster) ImageView ivPoster;
    @BindView(R.id.tv_original_title) TextView tvOriginalTitle;
    @BindView(R.id.tv_release_at) TextView tvReleaseAt;
    @BindView(R.id.rb_user_rating) RatingBar rbUserRating;
    @BindView(R.id.tv_post_synopsis) TextView tvPostSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setTitle(R.string.detail_activity);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MovieViewModel model = getIntent().getParcelableExtra(INTENT_KEY_MOVIE);
        if (model != null) {
            fillContent(model);
        }
    }

    @Override
    protected
    @LayoutRes
    Integer getLayoutId() {
        return R.layout.activity_detail;
    }

    private void fillContent(MovieViewModel model) {
        Picasso.with(this).load(NetworkMovies.IMAGE_URL + model.getImagePath()).into(ivPoster);
        tvOriginalTitle.setText(model.getOriginalTitle());
        String releaseDate = getString(R.string.release_date) + ": " + model.getReleaseAt();
        tvReleaseAt.setText(releaseDate);
        tvPostSynopsis.setText(model.getPlotSynopsis());
        rbUserRating.setRating(model.getUserRating());
    }

}
