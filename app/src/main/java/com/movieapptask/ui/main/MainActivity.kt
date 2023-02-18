package com.movieapptask.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.movieapptask.R
import com.movieapptask.adapter.adapter.MovieAdapter
import com.movieapptask.databinding.ActivityMainBinding
import com.movieapptask.model.MoviesResponse
import com.movieapptask.ui.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private var uri: Uri? = null
    private var movieList = mutableListOf<MoviesResponse.Movie>()
    private val adapter: MovieAdapter by lazy { MovieAdapter(movieList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getNotification()
        viewModel.getMovies()
        initView()
        setUpObservers()
        setUpListeners()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar.toolbar)
        binding.toolbar.title = getString(R.string.app_name)
        binding.recView.layoutManager = GridLayoutManager(this, 2)
        binding.recView.adapter = adapter

        uri = intent?.data
        if (uri != null) {
            var data = uri!!.host
            Log.e("info", uri.toString())
        }
    }

    private fun setUpObservers() {
        viewModel.isLoading.observe(this) {
            binding.refresh.isRefreshing = it
        }
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).show()
        }
        viewModel.movieLiveData.observe(this) {
            it?.let {
                binding.cardNoData.isVisible = it.isEmpty()
                binding.recView.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) {
                    movieList.clear()
                    movieList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.refresh.setOnRefreshListener {
            if (binding.refresh.isRefreshing) {
                viewModel.getMovies()
            }
        }
    }

    fun getNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("MovieAppTask")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }
    }
}