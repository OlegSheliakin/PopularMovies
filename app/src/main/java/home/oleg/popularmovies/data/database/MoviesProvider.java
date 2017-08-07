package home.oleg.popularmovies.data.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class MoviesProvider extends ContentProvider {

    private static final int CODE_MOVIE = 100;
    private static final int CODE_MOVIE_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private MoviesDbHelper moviesDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieDbContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE, CODE_MOVIE);
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE + "/#", CODE_MOVIE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                cursor = moviesDbHelper.getReadableDatabase().query(MovieDbContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_MOVIE_ID:
                String id = uri.getLastPathSegment();
                cursor = moviesDbHelper.getReadableDatabase().query(MovieDbContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieDbContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri resultUri;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE_ID:
                long id = moviesDbHelper.getWritableDatabase().insert(MovieDbContract.MovieEntry.TABLE_NAME, null, values);
                if (id > -1) {
                    resultUri = MovieDbContract.MovieEntry.buildUri(id);
                } else {
                    throw new UnsupportedOperationException("Error inserting: " + id);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (resultUri != null) {
            getContext().getContentResolver().notifyChange(resultUri, null);
        }

        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE_ID:
                numRowsDeleted = moviesDbHelper.getWritableDatabase()
                        .delete(MovieDbContract.MovieEntry.TABLE_NAME,
                                MovieDbContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                                new String[]{uri.getLastPathSegment()});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
