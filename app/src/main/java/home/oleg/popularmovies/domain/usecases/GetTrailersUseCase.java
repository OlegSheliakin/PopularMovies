package home.oleg.popularmovies.domain.usecases;

import java.util.List;

import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.data.network.MovieApi;
import home.oleg.popularmovies.domain.UseCaseObserver;
import retrofit2.Call;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class GetTrailersUseCase {

    private final MovieApi movieApi;
    private Call<VideosResponse> call;

    public GetTrailersUseCase(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public void execute(UseCaseObserver<List<VideosResponse.Result>> observer, Integer id) {
        if(call != null && call.isExecuted()) {
            call.cancel();
        }

        call = movieApi.getVideos(id);
        call.enqueue(new GetTrailersUseCase.ReviewCall(observer));
        observer.onStartExecute();
    }

    private static class ReviewCall extends BaseCallbackAdapter<VideosResponse, VideosResponse.Result> {

        private ReviewCall(UseCaseObserver<List<VideosResponse.Result>> observer) {
            super(observer);
        }

        @Override
        protected List<VideosResponse.Result> getResults(VideosResponse VideosResponse) {
            return VideosResponse.getResults();
        }
    }
}
