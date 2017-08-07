package home.oleg.popularmovies.presentation.mappers;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.entities.ReviewResponse;
import home.oleg.popularmovies.data.entities.VideosResponse;
import home.oleg.popularmovies.presentation.model.ReviewViewModel;
import home.oleg.popularmovies.presentation.model.TrailerViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class TrailerModelMapper implements Mapper<List<TrailerViewModel>, List<VideosResponse.Result>> {
    @Override
    public List<TrailerViewModel> map(List<VideosResponse.Result> results) {
        List<TrailerViewModel> mappedArray = new ArrayList<>();
        for (VideosResponse.Result result : results) {
            TrailerViewModel model = new TrailerViewModel();
            model.setName(result.getName());
            model.setKey(result.getKey());
            mappedArray.add(model);
        }
        return mappedArray;
    }
}
