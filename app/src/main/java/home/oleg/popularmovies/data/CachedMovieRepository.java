package home.oleg.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.database.MovieDbContract;
import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.domain.MovieRepository;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by olegshelyakin on 07.08.17.
 */

public class CachedMovieRepository implements MovieRepository {

    private final Context context;

    public CachedMovieRepository(Context context) {
        this.context = context;
    }

    public List<MovieViewModel> getMovies() {
        List<MovieViewModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MovieDbContract.MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID));
                String title = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_TITLE));
                String posterPath = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
                String overview = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
                Double voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
                MovieViewModel movieViewModel = new MovieViewModel();
                movieViewModel.setId(id);
                movieViewModel.setImagePath(posterPath);
                movieViewModel.setPlotSynopsis(overview);
                movieViewModel.setOriginalTitle(title);
                movieViewModel.setUserRating(voteAverage);
                movieViewModel.setReleaseAt(releaseDate);
                list.add(movieViewModel);
            }
            cursor.close();
        }

        return list;
    }

    @Override
    public void save(MovieViewModel model) {
        ContentValues values = new ContentValues();
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID, model.getId());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, model.getImagePath());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_TITLE, model.getOriginalTitle());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, model.getPlotSynopsis());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, model.getUserRating());
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, model.getReleaseAt());

        context.getContentResolver().insert(MovieDbContract.MovieEntry.buildUri(model.getId()), values);
    }

    @Override
    public boolean isFavourite(Integer id) {
        Cursor cursor = context.getContentResolver().query(MovieDbContract.MovieEntry.buildUri(id),
                null, null, null, null);
        if (cursor != null) {

            int count = cursor.getCount();
            cursor.close();

            if (count > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void delete(Integer id) {
        context.getContentResolver().delete(MovieDbContract.MovieEntry.buildUri(id), null, null);
    }

}
