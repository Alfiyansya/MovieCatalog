package com.achmadalfiansyah.moviecatalog.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.databinding.FragmentTvShowBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.OnItemTvShowClickCallback
import com.achmadalfiansyah.moviecatalog.ui.adapter.TvShowAdapter
import com.achmadalfiansyah.moviecatalog.ui.detail.DetailActivity
import com.achmadalfiansyah.moviecatalog.util.DataMapper.TVSHOW
import com.achmadalfiansyah.moviecatalog.util.ShowGridItemDecoration
import com.achmadalfiansyah.moviecatalog.util.DataMapper.BEST_VOTE
import com.achmadalfiansyah.moviecatalog.util.DataMapper.RANDOM_VOTE
import com.achmadalfiansyah.moviecatalog.util.DataMapper.WORST_VOTE
import com.achmadalfiansyah.moviecatalog.core.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var binding: FragmentTvShowBinding? = null

    private val tvShowViewModel: TvShowViewModel by viewModels()

    @Inject
    lateinit var tvShowAdapter: TvShowAdapter

    private var sort = BEST_VOTE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentTvShowBinding = FragmentTvShowBinding.inflate(inflater, container, false)
        binding = fragmentTvShowBinding
        setHasOptionsMenu(true)
        setUpToolbar()
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowViewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
        setUpSearchView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpSearchView() {

        with(binding) {
            this?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showProgressBar(true)
                    tvShowViewModel.getSearchTvShows(query)
                        .observe(requireActivity()) { searchUserResponse ->
                            when (searchUserResponse) {
                                is Resource.Loading -> showProgressBar(true)
                                is Resource.Success -> {
                                    showProgressBar(false)
                                    tvShowAdapter.setTvShowList(searchUserResponse.data)
                                    searchView.clearFocus()
                                    setUpRecyclerViewTv()
                                }
                                is Resource.Error -> {
                                    showProgressBar(false)
                                    Toast.makeText(
                                        context,
                                        R.string.something_wrong,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })

        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.best_vote -> {
                sort = BEST_VOTE
                item.isChecked = true
            }
            R.id.worst_vote -> {
                sort = WORST_VOTE
                item.isChecked = true
            }
            R.id.random_vote -> {
                sort = RANDOM_VOTE
                item.isChecked = true
            }
        }
        tvShowViewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
        return super.onOptionsItemSelected(item)
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { tvShows ->
        if (tvShows != null) {
            when (tvShows) {
                is Resource.Loading -> showProgressBar(true)
                is Resource.Success -> {
                    showProgressBar(false)
                    tvShowAdapter.setTvShowList(tvShows.data)
                    setUpRecyclerViewTv()
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setUpRecyclerViewTv() {
        with(binding?.rvTvShow) {
            val largePadding = resources.getDimensionPixelSize(R.dimen.show_item_grid_spacing)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.show_item_grid_spacing_small)
            this?.apply {
                layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = tvShowAdapter
                setHasFixedSize(true)
                addItemDecoration(ShowGridItemDecoration(largePadding, smallPadding))
            }
        }
        tvShowAdapter.setOnItemClickCallback(object : OnItemTvShowClickCallback {
            override fun onItemClicked(tvShow: TvShow?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, tvShow?.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, TVSHOW)
                startActivity(intent)
            }
        })
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding?.tvShowProgressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUpToolbar() {
        binding?.tvShowToolbar?.setOnMenuItemClickListener(this)
    }
}