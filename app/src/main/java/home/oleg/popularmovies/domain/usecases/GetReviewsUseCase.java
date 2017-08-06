package home.oleg.popularmovies.domain.usecases;

import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.network.MovieApi;
import home.oleg.popularmovies.domain.UseCaseObserver;
import retrofit2.Call;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class GetReviewsUseCase {

    private final MovieApi movieApi;
    private Call<ReviewResponse> call;

    public GetReviewsUseCase(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public void execute(UseCaseObserver<List<ReviewResponse.Result>> observer, Integer id) {
        if(call != null && call.isExecuted()) {
            call.cancel();
        }

        call = movieApi.getReviews(id);
        call.enqueue(new GetReviewsUseCase.ReviewCall(observer));
        observer.onStartExecute();
    }

    private static class ReviewCall extends BaseCallbackAdapter<ReviewResponse, ReviewResponse.Result> {

        private ReviewCall(UseCaseObserver<List<ReviewResponse.Result>> observer) {
            super(observer);
        }

        @Override
        protected List<ReviewResponse.Result> getResults(ReviewResponse reviewResponse) {
            return reviewResponse.getResults();
        }
    }
}
