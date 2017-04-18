package home.oleg.popularmovies.data.network;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import home.oleg.popularmovies.BuildConfig;

/**
 * Created by Oleg on 15.04.2017.
 */

public class NetworkMovies {

    public static final String BASE_URL =  BuildConfig.BASE_URL;
    public static final String IMAGE_URL = BuildConfig.BASE_IMAGE_URL;

    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    private static final String QUERY_API_KEY = "api_key";
    private static final String MOVIE_PATH = "movie";
    private static final String TAG = NetworkMovies.class.getSimpleName();

    public static URL buildMovieUrl(String path) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(path)
                .appendQueryParameter(QUERY_API_KEY, API_KEY)
                .build();

        URL url =  new URL(builtUri.toString());

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static @Nullable String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
