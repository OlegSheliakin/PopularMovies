package home.oleg.popularmovies.data.network;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public interface MovieApi {

    @GET(value = "movie/{path}")
    Call<MovieResponse> getMovies(@Path(value = "path") String path);

    @GET(value = "movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path(value = "id") Integer id);

    @GET(value = "movie/{id}/videos")
    Call<VideosResponse> getVideos(@Path(value = "id") Integer id);
}
