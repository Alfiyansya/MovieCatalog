package com.achmadalfiansyah.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.databinding.ItemShowBinding
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : PagedListAdapter<MovieEntity,MovieAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }


    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity?) {
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
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}