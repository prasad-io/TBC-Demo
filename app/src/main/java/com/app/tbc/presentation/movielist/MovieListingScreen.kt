package com.app.tbc.presentation.movielist

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.app.tbc.navigation.Screen
import com.app.tbc.utills.Resource

@Composable
fun MovieListingScreen(
    navigationController: NavController,
    context: Context,
) {
    getMoviesData(navigationController = navigationController, context = context)

}

@Composable
fun getMoviesData(navigationController: NavController, viewModel: MovieListingViewModel = hiltViewModel(), context:Context){
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
            Log.d("MOVIES", state.data.toString())
            MovieList(navigationController = navigationController, movies = state.data.results)
        }
    }
}



@Composable
fun MovieCard(movie: com.app.tbc.data.model.movies.Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original" + movie.poster_path),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.overview, maxLines = 4, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun MovieList(navigationController: NavController,movies: List<com.app.tbc.data.model.movies.Movie>) {
    LazyColumn {
        items(movies) { movie ->
            MovieCard(movie = movie) {

                navigationController.navigate(Screen.MovieDetail.createRoute(movie.id.toInt()))
            }
        }
    }
}


