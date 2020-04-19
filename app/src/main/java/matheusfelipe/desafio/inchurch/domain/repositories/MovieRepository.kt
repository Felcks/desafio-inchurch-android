package matheusfelipe.desafio.inchurch.domain.repositories

import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie

interface MovieRepository {
   suspend fun getAllMovies(page: Int = 1): List<Movie>
   suspend fun getAllMoviesGenres(): List<Genre>

   suspend fun cacheDetailMovie(movie: Movie)
   suspend fun getCachedDetailMovie(): Movie

   suspend fun favoriteOrDisfavorMovie(movie: Movie): Movie
   suspend fun getFavoriteMovies(filter: String = ""): List<Movie>
}