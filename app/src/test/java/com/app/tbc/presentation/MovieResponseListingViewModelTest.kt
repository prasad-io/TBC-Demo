package com.app.tbc.presentation

import com.app.tbc.BuildConfig
import com.app.tbc.data.model.movies.MovieResponse
import com.app.tbc.domain.usecase.MoviesListingUseCase
import com.app.tbc.presentation.movielist.MovieListingViewModel
import com.app.tbc.utills.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieResponseListingViewModelTest {

    @Mock
    private lateinit var moviesListingUseCase: MoviesListingUseCase

    private lateinit var movieListingViewModel: MovieListingViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp(){

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        //Mocking initial fetch behaviour in init block
        `when`(moviesListingUseCase.invoke(BuildConfig.API_KEY)).thenReturn(flowOf(Resource.loading()))

        movieListingViewModel = MovieListingViewModel(moviesListingUseCase)
    }


    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieListing emits loading then success`() =
        runTest {
            // Given
            val mockMovieResponseListing = MovieResponse(page = 1, results = emptyList(), total_pages = 1, total_results = 1)
            val expectedResource = Resource.success(mockMovieResponseListing)

            // Mocking method behavior
            `when`(moviesListingUseCase.invoke(BuildConfig.API_KEY)).thenReturn(flowOf(expectedResource))

            // When
            movieListingViewModel.getMovieListing(BuildConfig.API_KEY)

            // Then
            advanceUntilIdle() // Ensure all coroutines have completed
            val actualResource = movieListingViewModel.movieResponseStateFlow.value
            assertEquals(expectedResource, actualResource)
        }

    @Test
    fun `getMovieListing emits loading then failure`() =
        runTest {
            // Given
            val errorMessage = "Failed to load data"
            val expectedResource = Resource.failed<MovieResponse>(errorMessage)

            // Mocking method behavior
            `when`(moviesListingUseCase.invoke(BuildConfig.API_KEY)).thenReturn(flowOf(expectedResource))

            // When
            movieListingViewModel.getMovieListing(BuildConfig.API_KEY)

            // Then
            advanceUntilIdle() // Ensure all coroutines have completed
            val actualResource = movieListingViewModel.movieResponseStateFlow.value
            assertEquals(expectedResource, actualResource)
        }
}