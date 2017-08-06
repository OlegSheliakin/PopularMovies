package home.oleg.popularmovies.domain.usecases;

import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.domain.UseCaseObserver;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class DetailInteractor {

    private final GetReviewsUseCase getReviewsUseCase;
    private final GetTrailersUseCase getTrailersUseCase;

    public DetailInteractor(GetReviewsUseCase getReviewsUseCase,
                            GetTrailersUseCase getTrailersUseCase) {
        this.getReviewsUseCase = getReviewsUseCase;
        this.getTrailersUseCase = getTrailersUseCase;
    }

    public void executeFetchReviews(UseCaseObserver<List<ReviewResponse.Result>> observer, Integer id) {
        getReviewsUseCase.execute(observer, id);
    }

    public void executeFetchTrailers(UseCaseObserver<List<VideosResponse.Result>> observer, Integer id) {
        getTrailersUseCase.execute(observer, id);
    }

}
