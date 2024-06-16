package com.app.tbc.domain.usecase

import com.app.tbc.data.model.movies.MovieResponse
import com.app.tbc.domain.repository.MovieRepository
import com.app.tbc.utills.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import java.io.IOException
import javax.inject.Inject


class MoviesListingUseCase
@Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(apiKey: String): Flow<Resource<MovieResponse>> = flow {
        emit(Resource.loading())
        emit(Resource.success(repository.getMovies(apiKey)))
    }.retry(1) { e ->
        e is IOException
    }.catch {
        emit(Resource.failed(it.message.toString()))
    }
}
