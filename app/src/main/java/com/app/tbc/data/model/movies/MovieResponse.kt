package com.app.tbc.data.model.movies

data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
    val total_pages: Long,
    val total_results: Long,
)
