package com.app.tbc.domain.repository

import com.app.tbc.data.model.movies.MovieResponse


interface MovieRepository {
    suspend fun getMovies(apikey: String): MovieResponse
}
