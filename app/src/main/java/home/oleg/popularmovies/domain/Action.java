package home.oleg.popularmovies.domain;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface Action<T> {
    void perform(T t);
}

