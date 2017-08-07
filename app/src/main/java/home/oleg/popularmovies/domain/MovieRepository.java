package home.oleg.popularmovies.domain;

import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by olegshelyakin on 07.08.17.
 */

public interface MovieRepository {
    List<MovieViewModel> getMovies();
    void save(MovieViewModel model);
    boolean isFavourite(Integer id);

    void delete(Integer id);
}
