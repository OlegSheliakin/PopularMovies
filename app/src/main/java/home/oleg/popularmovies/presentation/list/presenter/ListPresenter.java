package home.oleg.popularmovies.presentation.list.presenter;

/**
 * Created by Oleg on 15.04.2017.
 */

public interface ListPresenter {
    void fetchMovies(Filter filter);

    enum Filter {
        TOP_RATED("top_rated"),
        POPULAR("popular"),
        FAVOURITE("favourite");

        private String value;

        Filter(String value) {
            this.value = value;
        }

        public static Filter resolve(String filter){
            switch (filter){
                case "top_rated":
                    return TOP_RATED;
                case "popular":
                    return POPULAR;
                case "favourite":
                    return FAVOURITE;
                default:
                    return POPULAR;
            }
        }

        public String getValue() {
           return this.value;
        }
    }
}
