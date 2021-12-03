package com.achmadalfiansyah.moviecatalog.ui.movie

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.databinding.FragmentMovieBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.MovieAdapter
import com.achmadalfiansyah.moviecatalog.ui.adapter.OnItemMovieClickCallback
import com.achmadalfiansyah.moviecatalog.ui.detail.DetailActivity
import com.achmadalfiansyah.moviecatalog.util.DataMapper.MOVIE
import com.achmadalfiansyah.moviecatalog.util.ShowGridItemDecoration
import com.achmadalfiansyah.moviecatalog.util.SortUtils.BEST_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.RANDOM_VOTE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.WORST_VOTE
import com.achmadalfiansyah.moviecatalog.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter
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
        setUpSearchView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpSearchView() {

        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showProgressBar(true)
                    movieViewModel.getSearchMovie(query)
                        .observe(requireActivity()) { searchUserResponse ->
                            when (searchUserResponse) {
                                is Resource.Loading -> showProgressBar(true)
                                is Resource.Success -> {
                                    showProgressBar(false)
                                    movieAdapter.setMovieList(searchUserResponse.data)
                                    searchView.clearFocus()
                                    setUpRecyclerViewMovie()
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
        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        binding.movieToolbar.setOnMenuItemClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val movieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> showProgressBar(true)
                is Resource.Success -> {
                    showProgressBar(false)
                    movieAdapter.setMovieList(movies.data)
                    setUpRecyclerViewMovie()
                    movieAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show()
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
            this.adapter = movieAdapter
            this.setHasFixedSize(true)
            val largePadding = resources.getDimensionPixelSize(R.dimen.show_item_grid_spacing)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.show_item_grid_spacing_small)
            this.addItemDecoration(ShowGridItemDecoration(largePadding,smallPadding))
        }
        movieAdapter.setOnItemClickCallback(object : OnItemMovieClickCallback {
            override fun onItemClicked(movie: Movie?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, movie?.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, MOVIE)
                startActivity(intent)
            }
        })
    }

}