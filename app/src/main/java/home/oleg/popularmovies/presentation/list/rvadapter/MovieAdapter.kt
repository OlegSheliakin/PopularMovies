package home.oleg.popularmovies.presentation.list.rvadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso
import home.oleg.popularmovies.BuildConfig

import home.oleg.popularmovies.R
import home.oleg.popularmovies.presentation.model.MovieViewModel
import kotlinx.android.synthetic.main.rv_movie_item.view.*
import javax.inject.Inject

/**
 * Created by Oleg on 15.04.2017.
 */

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var items: List<MovieViewModel> = emptyList()
    var clickListener: ((MovieViewModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_item, parent, false)
        return MovieAdapter.ViewHolder(v, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    fun setMovies(models: List<MovieViewModel>) {
        this.items = models
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val clickListener: ((MovieViewModel) -> Unit)?) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: MovieViewModel) = with(itemView) {
            Picasso.with(context).load(BuildConfig.BASE_IMAGE_URL + model.w185ImagePath).into(ivPoster)
            ivPoster.setOnClickListener { clickListener?.invoke(model) }
        }
    }

}
