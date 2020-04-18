package matheusfelipe.desafio.inchurch.domain.repositories

import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie

interface MovieRepository {
   suspend fun getAllMovies(): List<Movie>
   suspend fun getAllMoviesGenres(): List<Genre>

   suspend fun cacheDetailMovie(movie: Movie)
   suspend fun getCachedDetailMovie(): Movie

   suspend fun favoriteOrDisfavorMovie(movie: Movie)
   suspend fun getFavoriteMovies(): List<Movie>
}