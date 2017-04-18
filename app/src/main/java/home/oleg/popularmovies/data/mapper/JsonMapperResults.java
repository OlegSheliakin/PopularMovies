package home.oleg.popularmovies.data.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.data.ActionWithException;
import home.oleg.popularmovies.data.entities.Result;
import home.oleg.popularmovies.domain.Action;
import home.oleg.popularmovies.domain.mappers.Mapper;

/**
 * Created by Oleg on 15.04.2017.
 */

public class JsonMapperResults implements Mapper<List<Result>, JSONObject> {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_SYNOPSIS = "overview";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_ADULT = "adult";
    private static final String KEY_BACKDROP_PAPTH = "backdrop_path";
    private static final String KEY_ID = "id";
    private static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VIDEO = "video";
    private static final String KEY_VOTE_COUNT = "vote_count";

    public List<Result> map(JSONObject jsonObject) {

        if (!jsonObject.has(KEY_RESULTS)) return new ArrayList<>();

        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray(KEY_RESULTS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (array == null) return new ArrayList<>();

        List<Result> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            Result result = new Result();
            try {
                JSONObject object = array.getJSONObject(i);
                tryToSet(object, KEY_SYNOPSIS, o -> result.setOverview(o.getString(KEY_SYNOPSIS)));
                tryToSet(object, KEY_ORIGINAL_TITLE, o -> result.setOriginalTitle(o.getString(KEY_ORIGINAL_TITLE)));
                tryToSet(object, KEY_POSTER_PATH, o -> result.setPosterPath(o.getString(KEY_POSTER_PATH)));
                tryToSet(object, KEY_RELEASE_DATE, o -> result.setReleaseDate(o.getString(KEY_RELEASE_DATE)));
                tryToSet(object, KEY_ADULT, o -> result.setAdult(o.getBoolean(KEY_ADULT)));
                tryToSet(object, KEY_BACKDROP_PAPTH, o -> result.setBackdropPath(o.getString(KEY_BACKDROP_PAPTH)));
                tryToSet(object, KEY_ID, o -> result.setId(o.getInt(KEY_ID)));
                tryToSet(object, KEY_ORIGINAL_LANGUAGE, o -> result.setOriginalLanguage(o.getString(KEY_ORIGINAL_LANGUAGE)));
                tryToSet(object, KEY_POPULARITY, o -> result.setPopularity(o.getDouble(KEY_POPULARITY)));
                tryToSet(object, KEY_TITLE, o -> result.setTitle(o.getString(KEY_TITLE)));
                tryToSet(object, KEY_VOTE_AVERAGE, o -> result.setVoteAverage(o.getDouble(KEY_VOTE_AVERAGE)));
                tryToSet(object, KEY_VIDEO, o -> result.setVideo(o.getBoolean(KEY_VIDEO)));
                tryToSet(object, KEY_VOTE_COUNT, o -> result.setVoteCount(o.getInt(KEY_VOTE_COUNT)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            results.add(result);
        }
        return results;
    }

    private void tryToSet(JSONObject object, String key, ActionWithException<JSONObject> action) throws JSONException {
        if (object.has(key)) {
            action.perform(object);
        }
    }
}
