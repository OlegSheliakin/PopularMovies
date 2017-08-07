package home.oleg.popularmovies.presentation.mappers;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface Mapper<TO, FROM> {
    TO map(FROM from) ;
}
