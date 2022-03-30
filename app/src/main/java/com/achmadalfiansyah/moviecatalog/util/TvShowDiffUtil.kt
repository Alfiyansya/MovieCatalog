package com.achmadalfiansyah.moviecatalog.util

import androidx.recyclerview.widget.DiffUtil
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow

class TvShowDiffUtil(
    private val oldList: List<TvShow>,
    private val newList: List<TvShow>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}