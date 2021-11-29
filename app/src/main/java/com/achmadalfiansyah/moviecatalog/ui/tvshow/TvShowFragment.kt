package com.achmadalfiansyah.moviecatalog.ui.tvshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.databinding.FragmentTvShowBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.TvShowAdapter
import com.achmadalfiansyah.moviecatalog.util.SortUtils.BEST_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.RANDOM_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.WORST_VOTE
import com.achmadalfiansyah.moviecatalog.vo.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private val tvShowViewModel: TvShowViewModel by viewModel()
    private val tvShowAdapter: TvShowAdapter by inject()
    private var sort = BEST_VOTE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowViewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    @SuppressLint("NotifyDataSetChanged")
    private val tvShowObserver = Observer<Resource<List<TvShow>>> { tvShows ->
        if (tvShows != null) {
            when (tvShows) {
                is Resource.Loading -> showProgressBar(true)
                is Resource.Success -> {
                    showProgressBar(false)
                    tvShowAdapter.setTvShowList(tvShows.data)
                    setUpRecyclerViewTv()
                    tvShowAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Toast.makeText(context, R.string.terjadi_kesalahan, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setUpRecyclerViewTv() {
        with(binding.rvTvShow) {
            this.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.setHasFixedSize(true)
            this.adapter = tvShowAdapter
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.tvShowProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUpToolbar() {
        binding.tvShowToolbar.setOnMenuItemClickListener(this)
    }
}