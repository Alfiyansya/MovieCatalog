package com.achmadalfiansyah.moviecatalog.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.databinding.FragmentFavoriteBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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