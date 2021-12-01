package com.achmadalfiansyah.moviecatalog.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.achmadalfiansyah.moviecatalog.ui.favorite.FavoriteMovieAndTvShowFragment

class SectionPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        FavoriteMovieAndTvShowFragment.newInstance(position + 1)

}