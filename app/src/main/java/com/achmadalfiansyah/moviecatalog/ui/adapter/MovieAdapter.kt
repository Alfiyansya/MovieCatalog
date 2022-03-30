package com.achmadalfiansyah.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.databinding.ItemShowBinding
import com.achmadalfiansyah.moviecatalog.util.MovieDiffUtil
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var oldListMovie = emptyList<Movie>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun getItemCount(): Int = oldListMovie.count()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = oldListMovie[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
    }

    private lateinit var onItemClickCallback: OnItemMovieClickCallback

    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(IMAGE_ENDPOINT + movie?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPoster)
                itemTitle.text = movie?.title
                itemRating.text = movie?.rating.toString()
            }

        }
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemMovieClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovieList(newOldListMovies: List<Movie>?) {
        val diffUtil = newOldListMovies?.let { MovieDiffUtil(oldListMovie, it) }
        val diffResults = diffUtil?.let { DiffUtil.calculateDiff(it) }
        if (newOldListMovies != null) {
            oldListMovie = newOldListMovies
        }
        diffResults?.dispatchUpdatesTo(this)
    }
}