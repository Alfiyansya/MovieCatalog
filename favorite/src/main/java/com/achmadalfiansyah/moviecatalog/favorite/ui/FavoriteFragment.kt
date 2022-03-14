package com.achmadalfiansyah.moviecatalog.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.favorite.adapter.SectionPagerAdapter
import com.achmadalfiansyah.moviecatalog.favorite.databinding.FragmentFavoriteBinding
import com.achmadalfiansyah.moviecatalog.favorite.di.favoriteModule
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabLayout()
        loadKoinModules(favoriteModule)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setTabLayout(){
        val sectionPagerAdapter = SectionPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.viewPager.let {
            binding.favTabLayout.let { it1 ->
                TabLayoutMediator(it1, it) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}