package home.oleg.popularmovies.domain;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface UseCaseObserver<T> {
    void onStartExecute();
    void onSuccess(T t);
    void onError();
}
