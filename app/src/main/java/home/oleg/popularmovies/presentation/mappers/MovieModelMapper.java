package home.oleg.popularmovies.presentation.mappers;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class MovieModelMapper implements Mapper<List<MovieViewModel>, List<MovieResponse.Result>> {
    @Override
    public List<MovieViewModel> map(List<MovieResponse.Result> results) {
        List<MovieViewModel> mappedArray = new ArrayList<>();
        for (MovieResponse.Result result : results) {
            MovieViewModel model = new MovieViewModel();
            model.setOriginalTitle(result.getOriginalTitle());
            model.setImagePath(result.getPosterPath());
            model.setPlotSynopsis(result.getOverview());
            model.setReleaseAt(result.getReleaseDate());
            model.setUserRating(result.getVoteAverage());
            model.setId(result.getId());
            mappedArray.add(model);
        }
        return mappedArray;
    }
}
