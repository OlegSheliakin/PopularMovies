package home.oleg.popularmovies.domain.usecases;

import java.lang.ref.WeakReference;
import java.util.List;

import home.oleg.popularmovies.data.entities.MovieResponse;
import home.oleg.popularmovies.domain.Action;
import home.oleg.popularmovies.domain.UseCaseObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oleg Sheliakin on 06.08.2017.
 * Contact me by email - olegsheliakin@gmail.com
 */

public abstract class BaseCallbackAdapter<RESPONSE, RESULTS> implements Callback<RESPONSE> {
    private final WeakReference<UseCaseObserver<List<RESULTS>>> observer;

    protected BaseCallbackAdapter(UseCaseObserver<List<RESULTS>> observer) {
        this.observer = new WeakReference<>(observer);
    }

    @Override
    public void onResponse(Call<RESPONSE> call, Response<RESPONSE> response) {
        if (response.isSuccessful()) {
            RESPONSE res = response.body();
            if (res != null) {
                performOnObserver(observer -> observer.onSuccess(getResults(res)));
            } else {
                performOnObserver(UseCaseObserver::onError);
            }
        } else {
            performOnObserver(UseCaseObserver::onError);
        }
    }

    @Override
    public void onFailure(Call<RESPONSE> call, Throwable t) {
        performOnObserver(UseCaseObserver::onError);
    }

    private void performOnObserver(Action<UseCaseObserver<List<RESULTS>>> action) {
        UseCaseObserver<List<RESULTS>> observer = this.observer.get();
        if (observer != null) {
            action.perform(observer);
        }
    }

    protected abstract List<RESULTS> getResults(RESPONSE response);
}

