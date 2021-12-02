package com.achmadalfiansyah.moviecatalog.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.databinding.FragmentFavoriteMovieAndTvShowBinding
import com.achmadalfiansyah.moviecatalog.ui.adapter.MovieAdapter
import com.achmadalfiansyah.moviecatalog.ui.adapter.OnItemMovieClickCallback
import com.achmadalfiansyah.moviecatalog.ui.adapter.OnItemTvShowClickCallback
import com.achmadalfiansyah.moviecatalog.ui.adapter.TvShowAdapter
import com.achmadalfiansyah.moviecatalog.ui.detail.DetailActivity
import com.achmadalfiansyah.moviecatalog.util.DataMapper.MOVIE
import com.achmadalfiansyah.moviecatalog.util.DataMapper.TVSHOW
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class FavoriteMovieAndTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieAndTvShowBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var favMovieAdapter: MovieAdapter

    @Inject
    lateinit var favTvShowAdapter: TvShowAdapter

    private var index = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieAndTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = arguments?.getInt(ARG_SECTION_NUMBER, 0)!!
        indexCheck(index)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun indexCheck(index: Int) {
        if (index == 1) {
            favoriteViewModel.getFavoriteMovie().observe(viewLifecycleOwner, favMovieObserver)
        } else {
            favoriteViewModel.getFavoriteTvShow().observe(viewLifecycleOwner, favTvShowObserver)
        }
    }


    private val favMovieObserver = Observer<List<Movie>> { favMovie ->
        showNoData(true)
        setUpRecyclerViewFavMovie()
        favMovieAdapter.setMovieList(favMovie)
        showNoData(true)
        binding.detailAnimNoData.visibility = if (favMovie.isNotEmpty()) View.GONE else View.VISIBLE
    }

    private val favTvShowObserver = Observer<List<TvShow>> { favTvShow ->
        showNoData(false)
        favTvShowAdapter.setTvShowList(favTvShow)
        setUpRecyclerViewFavTv()
        binding.detailAnimNoData.visibility = if (favTvShow.isNotEmpty()) View.GONE else View.VISIBLE
    }

    private fun setUpRecyclerViewFavMovie() {
        with(binding.rvFavorite) {
            this.adapter = favMovieAdapter
            this.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.setHasFixedSize(true)
        }
        favMovieAdapter.setOnItemClickCallback(object : OnItemMovieClickCallback {
            override fun onItemClicked(movie: Movie?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, movie?.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, MOVIE)
                startActivity(intent)

            }

        })
    }

    private fun setUpRecyclerViewFavTv() {
        with(binding.rvFavorite) {
            this.adapter = favTvShowAdapter
            this.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.setHasFixedSize(true)
        }
        favTvShowAdapter.setOnItemClickCallback(object : OnItemTvShowClickCallback {
            override fun onItemClicked(tvShow: TvShow?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, tvShow?.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, TVSHOW)
                startActivity(intent)
            }

        })
    }

    private fun showNoData(state: Boolean) {
        binding.detailAnimNoData.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section number"

        @JvmStatic
        fun newInstance(index: Int) =
            FavoriteMovieAndTvShowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }

    }

}