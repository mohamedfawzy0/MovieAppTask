package com.movieapptask.model

data class MoviesResponse(
    var page: Int = 0,
    var results: ArrayList<Movie> = ArrayList(),
    var total_pages: Int = 0,
    var total_results: Int = 0
) {
    data class Movie(
        var adult: Boolean = false,
        var backdrop_path: String = "",
        var budget: Int = 0,
        var genres: ArrayList<Genre> = ArrayList(),
        var homepage: String = "",
        var id: Int = 0,
        var imdb_id: String = "",
        var original_language: String = "",
        var original_title: String = "",
        var overview: String = "",
        var popularity: Double = 0.0,
        var poster_path: String = "",
        var release_date: String = "",
        var revenue: Int = 0,
        var runtime: Int = 0,
        var status: String = "",
        var tagline: String = "",
        var title: String = "",
        var video: Boolean = false,
        var vote_average: Double = 0.0,
        var vote_count: Int = 0
    ) {
        data class Genre(
            var id: Int = 0,
            var name: String = ""
        )
    }
}

