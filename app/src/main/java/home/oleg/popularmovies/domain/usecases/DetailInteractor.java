package home.oleg.popularmovies.domain.usecases;

import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.domain.MovieRepository;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public class DetailInteractor {

    private final GetReviewsUseCase getReviewsUseCase;
    private final GetTrailersUseCase getTrailersUseCase;
    private final MovieRepository movieRepository;

    public DetailInteractor(GetReviewsUseCase getReviewsUseCase,
                            GetTrailersUseCase getTrailersUseCase,
                            MovieRepository movieRepository) {
        this.getReviewsUseCase = getReviewsUseCase;
        this.getTrailersUseCase = getTrailersUseCase;
        this.movieRepository = movieRepository;
    }

    public void executeFetchReviews(UseCaseObserver<List<ReviewResponse.Result>> observer, Integer id) {
        getReviewsUseCase.execute(observer, id);
    }

    public void executeFetchTrailers(UseCaseObserver<List<VideosResponse.Result>> observer, Integer id) {
        getTrailersUseCase.execute(observer, id);
    }

    public boolean isFavourite(Integer id) {
        return movieRepository.isFavourite(id);
    }

    public void addToFavourite(MovieViewModel movieViewModel) {
        movieRepository.save(movieViewModel);
    }

    public void deleteFromFavourite(Integer id) {
        movieRepository.delete(id);
    }

}
