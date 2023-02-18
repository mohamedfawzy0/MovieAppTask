package com.movieapptask.adapter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.movieapptask.R
import com.movieapptask.databinding.ItemMovieRowBinding
import com.movieapptask.model.MoviesResponse
import com.movieapptask.ui.movies.MovieDetailsActivity
import com.movieapptask.utils.Constants


class MovieAdapter(
    var moviesList: MutableList<MoviesResponse.Movie>
) :
    RecyclerView.Adapter<MovieAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieRowBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_movie_row, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = moviesList[position]
        holder.bind(model)

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MyHolder(private val binding: ItemMovieRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesResponse.Movie) {
            binding.model = movie
            var movieId = movie.id
            itemView.rootView.setOnClickListener {
                val intent = Intent(it.context, MovieDetailsActivity::class.java)
                intent.putExtra(Constants.KEY_ID, movieId)
                it.context.startActivity(intent)
            }
        }
    }

    interface OnItemClickListener {
        fun goToDetails(movieId: Int)
    }
}