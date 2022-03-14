package com.achmadalfiansyah.moviecatalog.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.databinding.ItemShowBinding
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listMovie = ArrayList<Movie>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun getItemCount(): Int = listMovie.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
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

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieList(movies: List<Movie>?) {
        if (movies == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
        notifyDataSetChanged()
    }
}