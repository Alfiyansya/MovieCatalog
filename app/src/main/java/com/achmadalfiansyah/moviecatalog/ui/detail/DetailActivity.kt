package com.achmadalfiansyah.moviecatalog.ui.detail

import android.annotation.SuppressLint
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

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val id = intent.getStringExtra(EXTRA_ID)
        val key = intent.getStringExtra(EXTRA_KEY)
        checkKeyAndGetId(id,key)
    }
    private fun checkKeyAndGetId(id: String?,key: String?){
        if (key == MOVIE){
            showDetailData(false)
            id?.toInt()?.let{
                detailViewModel.getMovieDetail(it).observe(this@DetailActivity, movieObserver)
            }
        }else{
            showDetailData(false)
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
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showDetailData(false)
                    Toast.makeText(this@DetailActivity, R.string.terjadi_kesalahan, Toast.LENGTH_SHORT).show()
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
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showDetailData(false)
                    Toast.makeText(this@DetailActivity, R.string.terjadi_kesalahan, Toast.LENGTH_SHORT).show()
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

    private fun showProgressBar(isLoading: Boolean) {
        binding?.detailProgressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDetailData(isAppears: Boolean) {
        binding?.detailData?.visibility = if (isAppears) View.VISIBLE else View.GONE
    }
    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_KEY = "key"
    }
}