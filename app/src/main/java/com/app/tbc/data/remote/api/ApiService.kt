package com.app.tbc.data.remote.api

import com.app.tbc.data.model.movies.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?with_people=71580")
    suspend fun getMovies(
        @Query("api_key") key: String,
    ): MovieResponse
}
