package com.achmadalfiansyah.moviecatalog.ui.movie

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
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.databinding.FragmentMovieBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.MovieAdapter
import com.achmadalfiansyah.moviecatalog.util.SortUtils.BEST_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.RANDOM_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.WORST_VOTE
import com.achmadalfiansyah.moviecatalog.vo.Resource
import com.achmadalfiansyah.moviecatalog.vo.Status
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModel()
    private val movieAdapter: MovieAdapter by inject()
    private var sort = BEST_VOTE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpToolbar()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
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
        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        binding.movieToolbar.setOnMenuItemClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    movieAdapter.submitList(movies.data)
                    setUpRecyclerViewMovie()
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, R.string.terjadi_kesalahan, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.movieProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setUpRecyclerViewMovie() {
        with(binding.rvMovie) {
            this.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.setHasFixedSize(true)
            this.adapter = movieAdapter
        }
    }
}