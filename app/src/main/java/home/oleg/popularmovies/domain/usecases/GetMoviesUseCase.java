package home.oleg.popularmovies.domain.usecases;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import home.oleg.popularmovies.data.entities.Result;
import home.oleg.popularmovies.data.mapper.JsonMapperResults;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.domain.Action;
import home.oleg.popularmovies.domain.UseCaseObserver;
import home.oleg.popularmovies.domain.mappers.Mapper;
import home.oleg.popularmovies.presentation.list.presenter.ListPresenter;


/**
 * Created by Oleg on 15.04.2017.
 */

public class GetMoviesUseCase {

    private FetchMovieTask task;

    public void execute(UseCaseObserver<List<Result>> observer, ListPresenter.Filter filter) {
        if(task != null) task.cancel(true);
        task = new FetchMovieTask(observer);
        task.execute(filter.getValue());
    }

    private static class FetchMovieTask extends AsyncTask<String, Void, List<Result>> {

        private final WeakReference<UseCaseObserver<List<Result>>> observer;
        private final Mapper<List<Result>, JSONObject> mapper;

        private FetchMovieTask(@NonNull UseCaseObserver<List<Result>> observer) {
            this.observer = new WeakReference<>(observer);
            this.mapper = new JsonMapperResults();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            performOnObserver(UseCaseObserver::onStartExecute);
        }

        @Override
        protected List<Result> doInBackground(String... params) {
            if (params[0] == null) return null;

            try {
                URL url = NetworkMovies.buildMovieUrl(params[0]);
                String response = NetworkMovies.getResponseFromHttpUrl(url);
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    return mapper.map(object);
                } else {
                    return null;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            if (results != null) {
                performOnObserver(o -> o.onSuccess(results));
            } else {
                performOnObserver(UseCaseObserver::onError);
            }
        }

        private void performOnObserver(Action<UseCaseObserver<List<Result>>> action) {
            UseCaseObserver<List<Result>> observer = this.observer.get();
            if (observer != null) {
                action.perform(observer);
            }
        }
    }
}


