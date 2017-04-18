package home.oleg.popularmovies.data;

import org.json.JSONException;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface ActionWithException<T> {
    void perform(T t) throws JSONException;
}