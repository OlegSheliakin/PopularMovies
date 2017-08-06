package home.oleg.popularmovies.presentation.list.rvadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import home.oleg.popularmovies.R;
import home.oleg.popularmovies.data.network.NetworkMovies;
import home.oleg.popularmovies.presentation.model.MovieViewModel;

/**
 * Created by Oleg on 15.04.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<MovieViewModel> items;
    private final MovieAdapterOnClickHandler clickHandler;

    public ArrayList<MovieViewModel> getItems() {
        return items;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieViewModel movieModel);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.items = new ArrayList<>();
    }

    public MovieAdapter(ArrayList<MovieViewModel> items, MovieAdapterOnClickHandler clickHandler) {
        this(clickHandler);
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item, parent, false);
        return new ViewHolder(v, clickHandler);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieViewModel> models) {
        if (!items.containsAll(models)) {
            items.clear();
            items.addAll(models);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ImageView poster;
        private MovieAdapterOnClickHandler clickHandler;

        ViewHolder(View itemView, @NonNull MovieAdapterOnClickHandler clickHandler) {
            super(itemView);
            this.clickHandler = clickHandler;
            context = itemView.getContext();
            poster = (ImageView) itemView.findViewById(R.id.iv_poster);
        }

        void bind(@NonNull MovieViewModel model) {
            Picasso.with(context).load(NetworkMovies.IMAGE_URL + model.getImagePath()).into(poster);
            poster.setOnClickListener(view -> clickHandler.onClick(model));
        }
    }

}
