package com.app.tbc.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tbc.BuildConfig
import com.app.tbc.data.model.movies.MovieResponse
import com.app.tbc.domain.usecase.MoviesListingUseCase
import com.app.tbc.utills.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListingViewModel
@Inject constructor(private val moviesListingUseCase: MoviesListingUseCase) : ViewModel() {
    private var movieResponseMutableStateFlow = MutableStateFlow<Resource<MovieResponse>>(Resource.loading())
    val movieResponseStateFlow: StateFlow<Resource<MovieResponse>> = movieResponseMutableStateFlow

    init {
        getMovieListing(BuildConfig.API_KEY)

    }

    fun getMovieListing(apiKey: String) {
        viewModelScope.launch {
            moviesListingUseCase.invoke(apiKey).collect {
                movieResponseMutableStateFlow.value = it
            }
        }
    }
}
