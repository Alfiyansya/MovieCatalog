package com.achmadalfiansyah.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.databinding.ItemShowBinding
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowAdapter: PagedListAdapter<TvShowEntity,TvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.bind(tvShow)
    }

    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(IMAGE_ENDPOINT + tvShow?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPoster)
                itemTitle.text = tvShow?.name
                itemRating.text = tvShow?.rating.toString()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}