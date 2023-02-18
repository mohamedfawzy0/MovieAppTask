package com.movieapptask.di

import com.movieapptask.model.MoviesResponse
import retrofit2.http.*


interface ApiInterface {

    @GET("popular")
    suspend fun getMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String,
    ): MoviesResponse.Movie

}
