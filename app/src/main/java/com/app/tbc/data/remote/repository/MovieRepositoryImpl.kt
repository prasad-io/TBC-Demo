package com.app.tbc.data.remote.repository



import com.app.tbc.data.model.movies.MovieResponse
import com.app.tbc.data.remote.api.ApiService
import com.app.tbc.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl
@Inject
constructor(private val apiService: ApiService) : MovieRepository {


    override suspend fun getMovies(apikey: String): MovieResponse {
        return apiService.getMovies(apikey)
    }
}
