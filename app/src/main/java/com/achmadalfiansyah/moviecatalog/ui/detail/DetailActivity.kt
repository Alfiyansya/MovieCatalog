package com.achmadalfiansyah.moviecatalog.ui.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.databinding.ActivityDetailBinding
import com.achmadalfiansyah.moviecatalog.util.DataMapper.MOVIE
import com.achmadalfiansyah.moviecatalog.util.SortUtils.IMAGE_ENDPOINT
import com.achmadalfiansyah.moviecatalog.vo.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val id = intent.getStringExtra(EXTRA_ID)
        val key = intent.getStringExtra(EXTRA_KEY)
        checkKeyAndGetId(id,key)
    }
    private fun checkKeyAndGetId(id: String?,key: String?){
        if (key == MOVIE){
            showDetailData(false)
            showFailedLoadData(false)
            id?.toInt()?.let{
                detailViewModel.getMovieDetail(it).observe(this@DetailActivity, movieObserver)
            }
        }else{
            showDetailData(false)
            showFailedLoadData(false)
            id?.toInt()?.let {
                detailViewModel.getTvShowDetail(it).observe(this@DetailActivity, tvShowObserver)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private val movieObserver = Observer<Resource<Movie>> { movie ->
        if (movie != null) {
            when (movie) {
                is Resource.Loading -> {
                    showProgressBar(true)
                    showDetailData(false)
                }
                is Resource.Success -> {
                    showProgressBar(false)
                    showDetailData(true)
                    setUpDataMovie(movie.data)
                    val isFavorite = !movie.data?.isFavorite!!
                    setUpFavMovieButton(movie.data, isFavorite)

                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showDetailData(false)
                    Toast.makeText(this@DetailActivity, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private val tvShowObserver = Observer<Resource<TvShow>> { tvShow ->
        if (tvShow != null) {
            when (tvShow) {
                is Resource.Loading -> {
                    showProgressBar(true)
                    showDetailData(false)
                }

                is Resource.Success -> {
                    showProgressBar(false)
                    showDetailData(true)
                    setUpDataTvShow(tvShow.data)
                    val isFavorite = !tvShow.data?.isFavorite!!
                    setUpFavTvShowButton(tvShow.data, isFavorite)

                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showDetailData(false)
                    showFailedLoadData(true)
                    Toast.makeText(this@DetailActivity, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpDataMovie(movie: Movie?) {
        with(binding) {
            this?.detailImage?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + movie?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }
            this?.detailPoster?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + movie?.backdropPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }
            this?.detailTitle?.text = movie?.title
            this?.detailGenre?.text = movie?.genres
            this?.detailRating?.text = movie?.rating.toString()
            this?.detailOverviewValue?.text = movie?.overview
            this?.detailRatingBar?.rating = (movie?.rating?.toFloat()?.div(2)!!)
        }
    }

    private fun setUpDataTvShow(tvShow: TvShow?) {
        with(binding) {
            this?.detailImage?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + tvShow?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }
            this?.detailPoster?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + tvShow?.backdropPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }

            this?.detailTitle?.text = tvShow?.name
            this?.detailGenre?.text = tvShow?.genres
            this?.detailRating?.text = tvShow?.rating.toString()
            this?.detailOverviewValue?.text = tvShow?.overview.toString()
            this?.detailRatingBar?.rating = (tvShow?.rating?.toFloat()?.div(2)!!)
        }
    }
    private fun setUpFavMovieButton(movie: Movie?, state: Boolean){
        binding?.detailFabFavorite?.imageTintList =
            if (state) {
                ColorStateList.valueOf(Color.rgb(255, 255, 255))
            } else {
                ColorStateList.valueOf(Color.rgb(247, 106, 123))
            }
        binding?.detailFabFavorite?.apply {
            setOnClickListener {
                if (state) {
                    if (movie != null) {
                        detailViewModel.setFavoriteMovie(movie, state)
                    }
                    Toast.makeText(
                        this@DetailActivity,
                        " ${movie?.title} has added to your favorite",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (movie != null) {
                        detailViewModel.setFavoriteMovie(movie, false)
                    }
                    Toast.makeText(
                        this@DetailActivity,
                        " ${movie?.title} has removed from your favorite",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun setUpFavTvShowButton(tvShow: TvShow?, state: Boolean) {
        binding?.detailFabFavorite?.imageTintList =
            if (state) {
                ColorStateList.valueOf(Color.rgb(255, 255, 255))
            } else {
                ColorStateList.valueOf(Color.rgb(247, 106, 123))
            }
        binding?.detailFabFavorite?.apply {
            setOnClickListener {
                if (state) {
                    if (tvShow != null) {
                        detailViewModel.setFavoriteTvShow(tvShow, state)
                    }
                    Toast.makeText(
                        this@DetailActivity,
                        " ${tvShow?.name} has added to your favorite",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (tvShow != null) {
                        detailViewModel.setFavoriteTvShow(tvShow, false)
                    }
                    Toast.makeText(
                        this@DetailActivity,
                        " ${tvShow?.name} has removed from your favorite",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding?.detailAnimLoader?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDetailData(isAppears: Boolean) {
        binding?.detailData?.visibility = if (isAppears) View.VISIBLE else View.GONE
        binding?.toolbarLayout?.visibility = if (isAppears) View.VISIBLE else View.GONE
        binding?.detailFabFavorite?.visibility = if (isAppears) View.VISIBLE else View.GONE
    }
    private fun showFailedLoadData(isLoading: Boolean) {
        binding?.detailAnimLoader?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_KEY = "key"
    }
}