package home.oleg.popularmovies.domain.mappers;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.entities.Result;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class MovieModelMapper implements Mapper<List<MovieViewModel>, List<Result>> {
    @Override
    public List<MovieViewModel> map(List<Result> results) {
        List<MovieViewModel> mappedArray = new ArrayList<>();
        for (Result result : results) {
            MovieViewModel model = new MovieViewModel();
            model.setOriginalTitle(result.getOriginalTitle());
            model.setImagePath(result.getPosterPath());
            model.setPlotSynopsis(result.getOverview());
            model.setReleaseAt(result.getReleaseDate());
            model.setUserRating(result.getVoteAverage());
            mappedArray.add(model);
        }
        return mappedArray;
    }
}
