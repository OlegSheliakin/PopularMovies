package home.oleg.popularmovies.presentation.mappers;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.presentation.model.MovieViewModel;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class ReviewModelMapper implements Mapper<List<ReviewViewModel>, List<ReviewResponse.Result>> {
    @Override
    public List<ReviewViewModel> map(List<ReviewResponse.Result> results) {
        List<ReviewViewModel> mappedArray = new ArrayList<>();
        for (ReviewResponse.Result result : results) {
            ReviewViewModel model = new ReviewViewModel();
            model.setAuthor(result.getAuthor());
            model.setOverview(result.getContent());
            mappedArray.add(model);
        }
        return mappedArray;
    }
}
