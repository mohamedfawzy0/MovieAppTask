package com.movieapptask.ui.movies

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.movieapptask.R
import com.movieapptask.adapter.adapter.GenreAdapter
import com.movieapptask.databinding.ActivityMovieDetailsBinding
import com.movieapptask.model.MoviesResponse
import com.movieapptask.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private var movieId: Int? = null
    private var uri: Uri? = null
    private var genreList = mutableListOf<MoviesResponse.Movie.Genre>()
    private val adapter: GenreAdapter by lazy { GenreAdapter(genreList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        movieId = intent.extras?.getInt(Constants.KEY_ID)
        uri = intent?.data
        if (uri != null) {
            var data = uri!!.host
            if (data=="www.moviescreen.com"){
                movieId=315162
            }else if (data=="www.moviescreen.open"){
                movieId=536554
            } else if (data=="www.details.screen.com"){
                movieId=842544
            } else if (data=="www.screen.open"){
                movieId=843794
            }
        }

        viewModel.getMovieDetails(movieId!!)
        initView()
        setUpObservers()
        setUpListeners()
    }

    private fun initView() {
        binding.toolbar.lang = Locale.getDefault().language
        setSupportActionBar(binding.toolbar.toolbar)
        binding.toolbar.llBack.visibility = View.VISIBLE
        binding.toolbar.title = getString(R.string.movie_details)
        binding.toolbar.llBack.setOnClickListener { onBackPressed() }
        binding.recViewGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.recViewGenre.adapter = adapter

    }

    private fun setUpObservers() {
        viewModel.isLoading.observe(this) {
            binding.refresh.isRefreshing = it
        }
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).show()
        }
        viewModel.movieDetailsLiveData.observe(this) {
            binding.model = it
            Glide.with(this).load(Constants.PATH + binding.model!!.poster_path)
                .into(binding.movieImg)

            if (it.genres.isNotEmpty()) {
                genreList.clear()
                genreList.addAll(it.genres)
                adapter.notifyDataSetChanged()

            }
        }
    }

    private fun setUpListeners() {
        binding.refresh.setOnRefreshListener {
            if (binding.refresh.isRefreshing) {
                viewModel.getMovieDetails(movieId!!)
            }
        }
    }
}