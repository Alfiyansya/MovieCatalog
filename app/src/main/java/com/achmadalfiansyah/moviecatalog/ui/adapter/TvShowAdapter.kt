package com.achmadalfiansyah.moviecatalog.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.databinding.ItemShowBinding
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowAdapter @Inject constructor(): RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {
    private var listTvShow = ArrayList<TvShow>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = listTvShow[position]
        holder.bind(tvShow)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(tvShow) }
    }
    private lateinit var onItemClickCallback: OnItemTvShowClickCallback

    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow?) {
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
    @SuppressLint("NotifyDataSetChanged")
    fun setTvShowList(tvShow: List<TvShow>?) {
        if (tvShow == null ) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShow)
        notifyDataSetChanged()
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemTvShowClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listTvShow.size
}