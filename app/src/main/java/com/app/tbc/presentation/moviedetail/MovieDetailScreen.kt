package com.app.tbc.presentation.moviedetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.app.tbc.presentation.movielist.MovieListingViewModel
import com.app.tbc.utills.Resource

@Composable
fun MovieDetailScreen(
    movieId: Int?,
    navigationController: NavController,
    context: Context,
) {
    getMovieDetails(navigationController = navigationController, movieId, context = context)

}

private const val TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p/w500"

@Composable
fun getMovieDetails(navigationController: NavController, movieId: Int?, viewModel: MovieListingViewModel = hiltViewModel(), context: Context) {


    val moviesState by viewModel.movieResponseStateFlow.collectAsState()
    when(val state = moviesState){
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxWidth()){
                CircularProgressIndicator()
            }
        }

        is Resource.Failed -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        is Resource.Success -> {
            val movie = state.data.results.find { it.id.toInt() == movieId }

            movie?.let {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter("$TMDB_IMAGE_BASE${it.poster_path}"),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }



}