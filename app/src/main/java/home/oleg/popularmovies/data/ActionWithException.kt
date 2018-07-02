package home.oleg.popularmovies.data

import org.json.JSONException

/**
 * Created by Oleg on 15.04.2017.
 */

interface ActionWithException<T> {
    @Throws(JSONException::class)
    fun perform(t: T)
}